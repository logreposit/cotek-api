package com.logreposit.cotek.cotekapi.services;

import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClient;
import com.logreposit.cotek.cotekapi.configuration.CotekConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AutoPowerOnService
{
    private static final Logger logger = LoggerFactory.getLogger(AutoPowerOnService.class);

    private final CotekConfiguration cotekConfiguration;
    private final CotekSerialClient  cotekSerialClient;

    @Autowired
    public AutoPowerOnService(CotekConfiguration cotekConfiguration,
                              CotekSerialClient  cotekSerialClient)
    {
        this.cotekConfiguration = cotekConfiguration;
        this.cotekSerialClient  = cotekSerialClient;
    }

    @Scheduled(fixedDelayString = "${cotek.auto-power-on-interval-in-millis}", initialDelayString = "${cotek.auto-power-on-interval-in-millis}")
    public void autoPowerOn()
    {
        if (!this.cotekConfiguration.isAutoPowerOn())
        {
            logger.info("Auto Power On is disabled.");

            return;
        }

        try
        {
            int outputMilliVolts = this.cotekSerialClient.getOutputVoltage();

            if (outputMilliVolts > 100_000)
            {
                logger.info("Output Voltage {} is above threshold (100V). Skipping restart.", outputMilliVolts / 1000);

                return;
            }

            logger.warn("Output Voltage {} is below threshold (100V). Sending Power off/on commands.", outputMilliVolts / 1000);

            this.cotekSerialClient.powerOff();
            this.cotekSerialClient.powerOn();

            logger.warn("Successfully sent Power Off + Power On commands to the inverter.");
        }
        catch (Exception e)
        {
            logger.error("Unable communicate with the Inverter. Communication failed", e);
        }
    }
}
