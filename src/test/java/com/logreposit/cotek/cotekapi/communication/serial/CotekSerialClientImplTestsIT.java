package com.logreposit.cotek.cotekapi.communication.serial;

import com.logreposit.cotek.cotekapi.configuration.CotekConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CotekSerialClientImplTestsIT
{
    private CotekSerialClientImpl cotekSerialClient;

    @MockBean
    private CotekConfiguration cotekConfiguration;

    @Before
    public void setUp()
    {
        this.cotekSerialClient = new CotekSerialClientImpl(this.cotekConfiguration);

        Mockito.when(this.cotekConfiguration.getComPort()).thenReturn("/dev/tty.usbserial-AC00JCNA");
        Mockito.when(this.cotekConfiguration.getReadTimeoutInMillis()).thenReturn(6000);
    }

    @Test
    public void test0PowerOn() throws CotekSerialClientException
    {
        this.cotekSerialClient.powerOn();
    }

    @Test
    public void test1GetOutputFrequency() throws CotekSerialClientException
    {
        int frequency = this.cotekSerialClient.getOutputFrequency();

        Assert.assertEquals(50, frequency);
    }

    @Test
    public void test2GetOutputVoltage() throws CotekSerialClientException, InterruptedException
    {
        Thread.sleep(15000);

        int outputVoltage = this.cotekSerialClient.getOutputVoltage();

        Assert.assertEquals(230000, outputVoltage, 3000);
    }

    @Test
    public void test3GetOutputCurrent() throws CotekSerialClientException
    {
        int outputCurrent = this.cotekSerialClient.getOutputCurrent();

        Assert.assertEquals(0, outputCurrent);
    }

    @Test
    public void test4GetOutputPower() throws CotekSerialClientException
    {
        int outputPower = this.cotekSerialClient.getOutputPower();

        Assert.assertEquals(2, outputPower, 2);
    }

    @Test
    public void test5GetInputVoltage() throws CotekSerialClientException
    {
        int inputVoltage = this.cotekSerialClient.getInputVoltage();

        Assert.assertEquals(12000.0, inputVoltage, 3000.0);
    }

    @Test
    public void test6PowerOff() throws CotekSerialClientException
    {
        this.cotekSerialClient.powerOff();
    }
}
