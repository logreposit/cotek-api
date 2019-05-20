package com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class LogIngressRequestDto
{
    @JsonProperty(value = "deviceType")
    private DeviceType deviceType;

    @JsonProperty(value = "data")
    private CotekLogData data;

    public LogIngressRequestDto(DeviceType deviceType, CotekLogData cotekLogData)
    {
        this.deviceType = deviceType;
        this.data       = cotekLogData;
    }
}
