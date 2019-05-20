package com.logreposit.cotek.cotekapi.communication.serial;

public class CommandConstants
{
    private CommandConstants()
    {
    }

    public static final String POWER_ON                  = "POWER 1";
    public static final String POWER_OFF                 = "POWER 0";
    public static final String OUTPUT_FREQUENCY          = "FRQ?";
    public static final String OUTPUT_VOLTAGE            = "VINV?";
    public static final String OUTPUT_CURRENT            = "IINV?";
    public static final String OUTPUT_POWER              = "PINV?";
    public static final String INPUT_VOLTAGE             = "VBAT?";
    public static final String ERROR                     = "ERR?";
    public static final String RESET                     = "*RST";
    public static final String FUNCTION_OVP_SETTING      = "FUNC 0";
    public static final String FUNCTION_OVP_RECOVERY     = "FUNC 1";
    public static final String FUNCTION_UVP_SETTING      = "FUNC 2";
    public static final String FUNCTION_UVP_RECOVERY     = "FUNC 3";
    public static final String FUNCTION_RS232_BAUDRATE   = "FUNC 5";
    public static final String FUNCTION_RETRY_TIME       = "FUNC 6";
    public static final String FUNCTION_QUERY_NO         = "FUNC?";
    public static final String FUNCTION_VALUE_SET_PREFIX = "SETT ";
    public static final String FUNCTION_VALUE_GET        = "SETT?";
}
