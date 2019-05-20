package com.logreposit.cotek.cotekapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cotek")
@Getter
@Setter
public class CotekConfiguration
{
    private String comPort;
    private int readTimeoutInMillis;
    private boolean autoPowerOn;
    private int autoPowerOnIntervalInMillis;
}
