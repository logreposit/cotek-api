package com.logreposit.cotek.cotekapi.rest.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
abstract class Response
{
    @JsonProperty(value = "status")
    private ResponseStatus status;

    @JsonProperty(value = "correlationId")
    private String correlationId;

    public Response(ResponseStatus responseStatus, String correlationId)
    {
        this.status        = responseStatus;
        this.correlationId = correlationId;
    }
}
