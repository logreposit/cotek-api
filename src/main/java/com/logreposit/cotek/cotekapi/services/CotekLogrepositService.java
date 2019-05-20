package com.logreposit.cotek.cotekapi.services;

import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.LogrepositApiClient;
import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.LogrepositApiClientException;
import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request.CotekLogData;
import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClient;
import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClientException;
import com.logreposit.cotek.cotekapi.configuration.LogrepositConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CotekLogrepositService
{
    private static final Logger logger = LoggerFactory.getLogger(CotekLogrepositService.class);

    private final CotekSerialClient       cotekSerialClient;
    private final LogrepositConfiguration logrepositConfiguration;
    private final LogrepositApiClient     logrepositApiClient;

    @Autowired
    public CotekLogrepositService(CotekSerialClient cotekSerialClient, LogrepositConfiguration logrepositConfiguration, LogrepositApiClient logrepositApiClient)
    {
        this.cotekSerialClient       = cotekSerialClient;
        this.logrepositConfiguration = logrepositConfiguration;
        this.logrepositApiClient     = logrepositApiClient;
    }

    @Scheduled(initialDelay = 10000L, fixedDelayString = "${logreposit.collect-interval}")
    public void readAndPublishData()
    {
        if (!this.logrepositConfiguration.getEnabled())
        {
            logger.info("Logreposit Collector is disabled.");

            return;
        }

        logger.debug("Started collecting log values.");

        try
        {
            Date         begin            = new Date();
            CotekLogData cotekLogData     = this.retrieveData();
            long         logFetchDuration = ((new Date()).getTime() - begin.getTime()) / 1000;

            logger.info("Finished collecting and converting log values. Operation took {} seconds.", logFetchDuration);

            this.logrepositApiClient.publishData(cotekLogData);
        }
        catch (CotekSerialClientException e)
        {
            logger.error("Unable to retrieve Log Data from Cotek SP Inverter", e);
        }
        catch (LogrepositApiClientException e)
        {
            logger.error("Unable to publish Log Data to Logreposit API", e);
        }
        catch (Exception e)
        {
            logger.error("Caught unexpected Exception while executing scheduled log collector task", e);
        }
    }

    private CotekLogData retrieveData() throws CotekSerialClientException
    {
        int outputFrequency = this.cotekSerialClient.getOutputFrequency();
        int outputVoltage   = this.cotekSerialClient.getOutputVoltage();
        int outputCurrent   = this.cotekSerialClient.getOutputCurrent();
        int outputPower     = this.cotekSerialClient.getOutputPower();
        int inputVoltage    = this.cotekSerialClient.getInputVoltage();

        CotekLogData cotekLogData = new CotekLogData();

        cotekLogData.setDate(new Date());
        cotekLogData.setOutputFrequency(outputFrequency);
        cotekLogData.setOutputVoltage(outputVoltage);
        cotekLogData.setOutputCurrent(outputCurrent);
        cotekLogData.setOutputPower(outputPower);
        cotekLogData.setInputVoltage(inputVoltage);

        return cotekLogData;
    }
}
