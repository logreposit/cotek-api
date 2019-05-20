package com.logreposit.cotek.cotekapi.communication.serial;

import com.logreposit.cotek.cotekapi.exceptions.LogrepositException;

public class CotekSerialClientException extends LogrepositException
{
    CotekSerialClientException()
    {
    }

    CotekSerialClientException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
