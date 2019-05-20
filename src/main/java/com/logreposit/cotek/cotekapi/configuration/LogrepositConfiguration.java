package com.logreposit.cotek.cotekapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "logreposit")
@Getter
@Setter
public class LogrepositConfiguration
{
    @NotNull
    private Boolean enabled;

    @NotBlank
    private String deviceToken;

    @NotNull
    private Long collectInterval;

    @NotBlank
    private String apiBaseUrl;

    @NotNull
    private Integer apiClientRetryCount;

    @NotNull
    private Long apiClientRetryInitialBackOffInterval;

    @NotNull
    private Double apiClientRetryBackOffMultiplier;
}
