package com.logreposit.cotek.cotekapi.communication.http.logrepositapi;

import com.logreposit.cotek.cotekapi.communication.http.logrepositapi.dtos.request.CotekLogData;

public interface LogrepositApiClient
{
    void publishData(CotekLogData cotekLogData) throws LogrepositApiClientException;
}
