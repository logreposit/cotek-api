package com.logreposit.cotek.cotekapi.communication.serial;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Only for Cotek SP 700~2000 Inverters
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@ToString
public class CotekError
{
    private boolean underVoltageProtection;
    private boolean overVoltageProtection;
    private boolean overLoadProtectionLow;
    private boolean overLoadProtectionMiddle;
    private boolean overLoadProtectionHigh;
    private boolean outputOverCurrentProtection;
    private boolean overTemperatureProtection;
    private boolean underTemperatureProtection;
    private boolean softStartFailProtection;
    private boolean powerOffProtection;

    // for future use, SP-3000~4000
    private boolean idConnectionError;
    private boolean phaseLockLoopError;
    private boolean shortProtection;
}
