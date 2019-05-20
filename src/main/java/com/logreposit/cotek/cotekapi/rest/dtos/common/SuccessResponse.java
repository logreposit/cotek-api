package com.logreposit.cotek.cotekapi.rest.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logreposit.cotek.cotekapi.rest.filters.correlationid.RequestCorrelation;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
public class SuccessResponse<T extends ResponseDto> extends Response
{
    private T data;

    public SuccessResponse(T data)
    {
        super(ResponseStatus.SUCCESS, RequestCorrelation.getCorrelationId());

        this.data = data;
    }
}
