package com.logreposit.cotek.cotekapi.rest.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logreposit.cotek.cotekapi.communication.serial.CotekError;
import com.logreposit.cotek.cotekapi.rest.dtos.common.ResponseDto;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class StatusResponseDto extends ResponseDto
{
    private Double     outputFrequency;
    private Double     outputVoltage;
    private Double     outputCurrent;
    private Double     outputPower;
    private Double     inputVoltage;
    private CotekError error;
}
