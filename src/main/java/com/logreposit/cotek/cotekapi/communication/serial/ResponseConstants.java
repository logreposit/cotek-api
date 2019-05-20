package com.logreposit.cotek.cotekapi.communication.serial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ResponseConstants
{
    private ResponseConstants()
    {
    }

    private static final List<Byte> OK         = Arrays.asList((byte) 61, (byte) 62);
    private static final List<Byte> ERROR      = Arrays.asList((byte) 33, (byte) 62);
    private static final List<Byte> EXEC_ERROR = Arrays.asList((byte) 63, (byte) 62);

    static final List<Byte> CRLF           = Arrays.asList((byte) 13, (byte) 10);
    static final List<Byte> CMD_OK         = Stream.concat(OK.stream(), CRLF.stream()).collect(Collectors.toList());
    static final List<Byte> CMD_ERROR      = Stream.concat(ERROR.stream(), CRLF.stream()).collect(Collectors.toList());
    static final List<Byte> CMD_EXEC_ERROR = Stream.concat(EXEC_ERROR.stream(), CRLF.stream()).collect(Collectors.toList());
}
