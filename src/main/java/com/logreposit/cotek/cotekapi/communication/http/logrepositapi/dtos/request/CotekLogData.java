package com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class CotekLogData
{
    private Date    date;
    private Integer outputFrequency;
    private Integer outputVoltage;
    private Integer outputCurrent;
    private Integer outputPower;
    private Integer inputVoltage;
}
