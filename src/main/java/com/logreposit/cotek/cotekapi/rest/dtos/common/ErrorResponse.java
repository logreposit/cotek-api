package com.logreposit.cotek.cotekapi.rest.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logreposit.cotek.cotekapi.rest.filters.correlationid.RequestCorrelation;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class ErrorResponse extends Response
{
    private Integer code;
    private String  message;

    public ErrorResponse(Integer code, String message)
    {
        super(ResponseStatus.ERROR, RequestCorrelation.getCorrelationId());

        this.code    = code;
        this.message = message;
    }
}
