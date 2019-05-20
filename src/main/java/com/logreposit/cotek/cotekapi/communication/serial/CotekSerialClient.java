package com.logreposit.cotek.cotekapi.communication.serial;

public interface CotekSerialClient
{
    void       powerOn()            throws CotekSerialClientException;
    void       powerOff()           throws CotekSerialClientException;
    int        getOutputFrequency() throws CotekSerialClientException;
    int        getOutputVoltage()   throws CotekSerialClientException;
    int        getOutputCurrent()   throws CotekSerialClientException;
    int        getOutputPower()     throws CotekSerialClientException;
    int        getInputVoltage()    throws CotekSerialClientException;
    CotekError getErrors()          throws CotekSerialClientException;
}
