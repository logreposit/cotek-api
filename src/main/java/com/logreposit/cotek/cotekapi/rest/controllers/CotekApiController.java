package com.logreposit.cotek.cotekapi.rest.controllers;

import com.logreposit.cotek.cotekapi.communication.serial.CotekError;
import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClient;
import com.logreposit.cotek.cotekapi.communication.serial.CotekSerialClientException;
import com.logreposit.cotek.cotekapi.rest.dtos.common.ResponseDto;
import com.logreposit.cotek.cotekapi.rest.dtos.common.SuccessResponse;
import com.logreposit.cotek.cotekapi.rest.dtos.response.StatusResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CotekApiController
{
    private final CotekSerialClient cotekSerialClient;

    @Autowired
    public CotekApiController(CotekSerialClient cotekSerialClient)
    {
        this.cotekSerialClient = cotekSerialClient;
    }

    @GetMapping(path = "/v1/api/status")
    public ResponseEntity<SuccessResponse> getStatus() throws CotekSerialClientException
    {
        StatusResponseDto            statusResponseDto = this.retrieveCotekStatus();
        SuccessResponse<ResponseDto> successResponse   = new SuccessResponse<>(statusResponseDto);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/v1/api/power/on")
    public ResponseEntity<SuccessResponse> powerOn() throws CotekSerialClientException
    {
        this.cotekSerialClient.powerOn();

        SuccessResponse<ResponseDto> successResponse = new SuccessResponse<>(null);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/v1/api/power/off")
    public ResponseEntity<SuccessResponse> powerOff() throws CotekSerialClientException
    {
        this.cotekSerialClient.powerOff();

        SuccessResponse<ResponseDto> successResponse = new SuccessResponse<>(null);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    private StatusResponseDto retrieveCotekStatus() throws CotekSerialClientException
    {
        int        outputFrequency = this.cotekSerialClient.getOutputFrequency();
        int        outputVoltage   = this.cotekSerialClient.getOutputVoltage();
        int        outputCurrent   = this.cotekSerialClient.getOutputCurrent();
        int        outputPower     = this.cotekSerialClient.getOutputPower();
        int        inputVoltage    = this.cotekSerialClient.getInputVoltage();
        CotekError error           = this.cotekSerialClient.getErrors();

        StatusResponseDto statusResponseDto = new StatusResponseDto();

        statusResponseDto.setOutputFrequency((double) outputFrequency);
        statusResponseDto.setOutputPower((double) outputPower);
        statusResponseDto.setOutputVoltage(outputVoltage / 1000d);
        statusResponseDto.setOutputCurrent(outputCurrent / 1000d);
        statusResponseDto.setInputVoltage(inputVoltage / 1000d);
        statusResponseDto.setError(error);

        return statusResponseDto;
    }
}
