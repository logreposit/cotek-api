package com.logreposit.cotek.cotekapi.exceptions;

public class LogrepositException extends Exception
{
    protected LogrepositException()
    {
    }

    public LogrepositException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
