package com.logreposit.cotek.cotekapi.communication.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.logreposit.cotek.cotekapi.configuration.CotekConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CotekSerialClientImpl implements CotekSerialClient
{
    private static final Logger logger = LoggerFactory.getLogger(CotekSerialClientImpl.class);

    private final CotekConfiguration cotekConfiguration;

    @Autowired
    public CotekSerialClientImpl(CotekConfiguration cotekConfiguration)
    {
        this.cotekConfiguration = cotekConfiguration;
    }

    @Override
    public void powerOn() throws CotekSerialClientException
    {
        this.communicate(CommandConstants.POWER_ON);
    }

    @Override
    public void powerOff() throws CotekSerialClientException
    {
        this.communicate(CommandConstants.POWER_OFF);
    }

    @Override
    public int getOutputFrequency() throws CotekSerialClientException
    {
        String response  = this.communicate(CommandConstants.OUTPUT_FREQUENCY);
        int    frequency = parseStringToInt(response);

        return frequency;
    }

    @Override
    public int getOutputVoltage() throws CotekSerialClientException
    {
        String response   = this.communicate(CommandConstants.OUTPUT_VOLTAGE);
        int    voltage    = parseStringToInt(response);
        int    milliVolts = voltage * 1000;

        return milliVolts;
    }

    @Override
    public int getOutputCurrent() throws CotekSerialClientException
    {
        String response     = this.communicate(CommandConstants.OUTPUT_CURRENT);
        double current      = parseStringToDouble(response);
        int    milliAmperes = (int) current * 1000;

        return milliAmperes;
    }

    @Override
    public int getOutputPower() throws CotekSerialClientException
    {
        String response = this.communicate(CommandConstants.OUTPUT_POWER);
        int    power    = parseStringToInt(response);

        return power;
    }

    @Override
    public int getInputVoltage() throws CotekSerialClientException
    {
        String response   = this.communicate(CommandConstants.INPUT_VOLTAGE);
        double voltage    = parseStringToDouble(response);
        int    milliVolts = (int) voltage * 1000;

        return milliVolts;
    }

    @Override
    public CotekError getErrors() throws CotekSerialClientException
    {
        List<Byte> bytesRead  = this.executeCommand(CommandConstants.ERROR);
        CotekError cotekError = CotekErrorParser.parse(bytesRead);

        return cotekError;
    }

    private String communicate(String requestAsString) throws CotekSerialClientException
    {
        List<Byte> bytesRead = this.executeCommand(requestAsString);

        return byteListToString(bytesRead);
    }

    private synchronized List<Byte> executeCommand(String requestAsString) throws CotekSerialClientException
    {
        String     terminatedRequest = requestAsString + "\r\n";
        byte[]     request           = stringToASCIIBytes(terminatedRequest);
        SerialPort serialPort        = SerialPort.getCommPort(this.cotekConfiguration.getComPort());

        configureSerialPortForCotekSPSeries(serialPort);

        try
        {
            logger.info("Sending command: {}", requestAsString);

            serialPort.openPort();
            serialPort.writeBytes(request, request.length);

            List<Byte> bytesRead = this.readUntilResponseTerminator(serialPort);

            logger.info("Read {} bytes", bytesRead.size());

            int indexOfCmdOk = Collections.indexOfSubList(bytesRead, ResponseConstants.CMD_OK);

            bytesRead.subList(indexOfCmdOk, indexOfCmdOk + ResponseConstants.CMD_OK.size()).clear();

            int indexOfCRLF = Collections.indexOfSubList(bytesRead, ResponseConstants.CRLF);

            if (indexOfCRLF != -1)
            {
                bytesRead.subList(indexOfCRLF, indexOfCRLF + ResponseConstants.CRLF.size()).clear();
            }

            return bytesRead;
        }
        catch (InterruptedException e)
        {
            logger.error("Caught InterruptedException: {}", e.getMessage());

            throw new CotekSerialClientException("Caught InterruptedException during communication", e);
        }
        finally
        {
            serialPort.closePort();
        }
    }

    private List<Byte> readUntilResponseTerminator(SerialPort serialPort)
            throws InterruptedException, CotekReadTimeoutException, CotekCommandErrorException, CotekCommandExecutionErrorException
    {
        List<Byte> bytesRead = new ArrayList<>();
        Date       started   = new Date();

        while (true)
        {
            Thread.sleep(30);

            Date now = new Date();

            long differenceInMillis = now.getTime() - started.getTime();

            if (differenceInMillis > this.cotekConfiguration.getReadTimeoutInMillis())
            {
                logger.error("Timeout reading data.");

                throw new CotekReadTimeoutException();
            }

            int    bytesAvailable = serialPort.bytesAvailable();
            // TODO: byteAvailable can also return -1 ?!
            byte[] readBuffer     = new byte[serialPort.bytesAvailable()];
            int    numRead        = serialPort.readBytes(readBuffer, readBuffer.length);

            logger.info("Read {} bytes of {} available.", numRead, bytesAvailable);

            for (byte b : readBuffer)
            {
                bytesRead.add(b);
            }

            int indexOfCmdOk        = Collections.indexOfSubList(bytesRead, ResponseConstants.CMD_OK);
            int indexOfCmdError     = Collections.indexOfSubList(bytesRead, ResponseConstants.CMD_ERROR);
            int indexOfCmdExecError = Collections.indexOfSubList(bytesRead, ResponseConstants.CMD_EXEC_ERROR);

            if (indexOfCmdError != -1)
            {
                throw new CotekCommandErrorException();
            }

            if (indexOfCmdExecError != -1)
            {
                throw new CotekCommandExecutionErrorException();
            }

            if (indexOfCmdOk != -1)
            {
                return bytesRead;
            }
        }
    }

    private static void configureSerialPortForCotekSPSeries(SerialPort serialPort)
    {
        serialPort.setBaudRate(4800);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(1);
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
    }

    private static byte[] stringToASCIIBytes(String string)
    {
        byte[] b = new byte[string.length()];

        for (int i = 0; i < b.length; i++)
        {
            b[i] = (byte) string.charAt(i);
        }

        return b;
    }

    private static String byteListToString(List<Byte> bytes)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for (Byte byte_ : bytes)
        {
            byte b = byte_;
            stringBuilder.append((char) b);
        }

        return stringBuilder.toString();
    }

    private static int parseStringToInt(String string) throws CotekSerialClientException
    {
        try
        {
            int value = Integer.parseInt(string);

            return value;
        }
        catch (NumberFormatException e)
        {
            logger.error("Could not parse response String {} to int", string, e);

            throw new CotekSerialClientException("Could not parse response String to int", e);
        }
    }

    private static double parseStringToDouble(String string) throws CotekSerialClientException
    {
        try
        {
            double value = Double.parseDouble(string);

            return value;
        }
        catch (NumberFormatException e)
        {
            logger.error("Could not parse response String {} to double", string, e);

            throw new CotekSerialClientException("Could not parse response String to double", e);
        }
    }
}
