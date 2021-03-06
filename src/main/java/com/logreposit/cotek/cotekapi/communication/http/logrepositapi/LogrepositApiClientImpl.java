package com.logreposit.cotek.cotekapi.communication.http.logrepositapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logreposit.cotek.cotekapi.communication.http.common.ResponseErrorHandlerFactory;
import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request.CotekLogData;
import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request.DeviceType;
import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request.LogIngressRequestDto;
import com.logreposit.cotek.cotekapi.configuration.LogrepositConfiguration;
import com.logreposit.cotek.cotekapi.utils.RetryTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@Service
public class LogrepositApiClientImpl implements LogrepositApiClient
{
    private static final Logger logger = LoggerFactory.getLogger(LogrepositApiClientImpl.class);

    private static final String DEVICE_TOKEN_HEADER_NAME = "x-device-token";

    private final RestTemplate            restTemplate;
    private final LogrepositConfiguration logrepositConfiguration;
    private final ObjectMapper            objectMapper;

    public LogrepositApiClientImpl(RestTemplateBuilder restTemplateBuilder, LogrepositConfiguration logrepositConfiguration, ObjectMapper objectMapper)
    {
        ResponseErrorHandler responseErrorHandler = ResponseErrorHandlerFactory.createWithoutHttpStatusErrorHandling();

        this.restTemplate = restTemplateBuilder.errorHandler(responseErrorHandler)
                                               .build();

        this.logrepositConfiguration = logrepositConfiguration;
        this.objectMapper            = objectMapper;
    }

    @Override
    public void publishData(CotekLogData cotekLogData) throws LogrepositApiClientException
    {
        try
        {
            URL                  ingressUrl           = new URL(this.getApiBaseUrl(), "ingress");
            LogIngressRequestDto logIngressRequestDto = new LogIngressRequestDto(DeviceType.COTEK_SP_SERIES, cotekLogData);
            String               payload              = this.objectMapper.writeValueAsString(logIngressRequestDto);
            String               response             = this.requestWithRetries(ingressUrl.toString(), HttpMethod.POST, payload);

            logger.info("Successfully published log data: {}", response);
        }
        catch (Exception e)
        {
            logger.error("Unable to publish log data", e);
            throw new LogrepositApiClientException("Unable to publish log data", e);
        }
    }

    private URL getApiBaseUrl() throws MalformedURLException
    {
        String apiBaseUrl = this.logrepositConfiguration.getApiBaseUrl();

        if (!apiBaseUrl.endsWith("/"))
        {
            apiBaseUrl = apiBaseUrl + "/";
        }

        return new URL(apiBaseUrl);
    }

    private String requestWithRetries(String uriAsString, HttpMethod httpMethod, String requestPayload) throws Exception
    {
        int    maxAttempts            = this.logrepositConfiguration.getApiClientRetryCount();
        long   initialBackOffInterval = this.logrepositConfiguration.getApiClientRetryInitialBackOffInterval();
        double backOffMulitplier      = this.logrepositConfiguration.getApiClientRetryBackOffMultiplier();

        RetryTemplate retryTemplate = RetryTemplateFactory.getRetryTemplateWithExponentialBackOffForAllExceptions(
                maxAttempts,
                initialBackOffInterval,
                backOffMulitplier
        );

        String successResponse = retryTemplate.execute(retryContext -> {
            logger.info("Retry {}/{}: {} '{}' ... ", retryContext.getRetryCount(), maxAttempts, httpMethod, uriAsString);
            return this.request(uriAsString, httpMethod, requestPayload);
        });

        return successResponse;
    }

    private String request(String uriAsString, HttpMethod httpMethod, String requestPayload) throws Exception
    {
        try
        {
            URI uri = new URI(uriAsString);

            logger.info("Sending {} Request to '{}' with payload {} ...", httpMethod, uri, requestPayload);

            ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                    uri,
                    httpMethod,
                    new HttpEntity<>(requestPayload, this.buildHeaders()),
                    String.class
            );

            String responseBody = responseEntity.getBody();

            if (!responseEntity.getStatusCode().is2xxSuccessful())
            {
                logger.error("Request was not successful: Got HTTP status code '{}': Body: {}", responseEntity.getStatusCodeValue(), responseBody);

                String errorMessage = String.format("Request to '%s' was not successful: HTTP %d", uri.toString(), responseEntity.getStatusCodeValue());

                throw new Exception(errorMessage);
            }

            logger.debug("Request was successful: HTTP status code '{}': Body: {}", responseEntity.getStatusCodeValue(), responseBody);

            return responseBody;
        }
        catch (Exception e)
        {
            logger.error("Caught unexpected Exception while {}ing to URL '{}'", httpMethod, uriAsString, e);
            throw e;
        }
    }

    private HttpHeaders buildHeaders()
    {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.set(DEVICE_TOKEN_HEADER_NAME, this.logrepositConfiguration.getDeviceToken());

        return httpHeaders;
    }
}
