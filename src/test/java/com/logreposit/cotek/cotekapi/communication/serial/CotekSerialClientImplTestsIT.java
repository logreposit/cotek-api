package com.logreposit.cotek.cotekapi.communication.serial;

import com.logreposit.cotek.cotekapi.configuration.CotekConfiguration;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CotekSerialClientImplTestsIT
{
    private CotekSerialClientImpl cotekSerialClient;

    @MockBean
    private CotekConfiguration cotekConfiguration;

    @BeforeEach
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

        assertThat(frequency).isEqualTo(50);
    }

    @Test
    public void test2GetOutputVoltage() throws CotekSerialClientException, InterruptedException
    {
        Thread.sleep(15000);

        int outputVoltage = this.cotekSerialClient.getOutputVoltage();

        assertThat(outputVoltage).isCloseTo(230000, Offset.offset(3000));
    }

    @Test
    public void test3GetOutputCurrent() throws CotekSerialClientException
    {
        int outputCurrent = this.cotekSerialClient.getOutputCurrent();

        assertThat(outputCurrent).isEqualTo(0);
    }

    @Test
    public void test4GetOutputPower() throws CotekSerialClientException
    {
        int outputPower = this.cotekSerialClient.getOutputPower();

        assertThat(outputPower).isCloseTo(2, Offset.offset(2));
    }

    @Test
    public void test5GetInputVoltage() throws CotekSerialClientException
    {
        int inputVoltage = this.cotekSerialClient.getInputVoltage();

        assertThat(inputVoltage).isCloseTo(12000, Offset.offset(3000));
    }

    @Test
    public void test6PowerOff() throws CotekSerialClientException
    {
        this.cotekSerialClient.powerOff();
    }
}
