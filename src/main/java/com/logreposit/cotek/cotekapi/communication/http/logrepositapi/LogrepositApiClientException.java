package com.logreposit.cotek.cotekapi.communication.http.logrepositapi;

import com.logreposit.cotek.cotekapi.exceptions.LogrepositException;

public class LogrepositApiClientException extends LogrepositException
{
    LogrepositApiClientException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
