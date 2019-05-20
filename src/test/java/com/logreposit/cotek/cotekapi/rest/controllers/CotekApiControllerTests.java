package com.logreposit.cotek.cotekapi.rest.controllers;

import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CotekApiController.class)
public class CotekApiControllerTests
{
    @MockBean
    private CotekSerialClient cotekSerialClient;

    @Autowired
    private MockMvc controller;

    @Test
    public void testGetStatus() throws Exception
    {
        Mockito.when(this.cotekSerialClient.getInputVoltage()).thenReturn(12500);
        Mockito.when(this.cotekSerialClient.getOutputFrequency()).thenReturn(50);
        Mockito.when(this.cotekSerialClient.getOutputVoltage()).thenReturn(228200);
        Mockito.when(this.cotekSerialClient.getOutputCurrent()).thenReturn(1668);
        Mockito.when(this.cotekSerialClient.getOutputPower()).thenReturn(381);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/v1/api/status");

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                       .andExpect(jsonPath("$.correlationId").isString())
                       .andExpect(jsonPath("$.status").value("SUCCESS"))
                       .andExpect(jsonPath("$.data").exists())
                       .andExpect(jsonPath("$.data.inputVoltage").value(12.5d))
                       .andExpect(jsonPath("$.data.outputFrequency").value(50.0d))
                       .andExpect(jsonPath("$.data.outputVoltage").value(228.2d))
                       .andExpect(jsonPath("$.data.outputCurrent").value(1.668d))
                       .andExpect(jsonPath("$.data.outputPower").value(381.0d));

        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).getInputVoltage();
        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).getOutputFrequency();
        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).getOutputVoltage();
        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).getOutputCurrent();
        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).getOutputPower();
    }

    @Test
    public void testPowerOn() throws Exception
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/v1/api/power/on");

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                       .andExpect(jsonPath("$.correlationId").isString())
                       .andExpect(jsonPath("$.status").value("SUCCESS"));

        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).powerOn();
    }

    @Test
    public void testPowerOff() throws Exception
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/v1/api/power/off");

        this.controller.perform(request)
                       .andDo(MockMvcResultHandlers.print())
                       .andExpect(status().isOk())
                       .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                       .andExpect(jsonPath("$.correlationId").isString())
                       .andExpect(jsonPath("$.status").value("SUCCESS"));

        Mockito.verify(this.cotekSerialClient, Mockito.times(1)).powerOff();
    }
}
