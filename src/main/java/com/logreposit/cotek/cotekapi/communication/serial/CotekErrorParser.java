package com.logreposit.cotek.cotekapi.communication.serial;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.List;

class CotekErrorParser
{
    private static final Logger logger = LoggerFactory.getLogger(CotekErrorParser.class);

    private CotekErrorParser()
    {
    }

    // Only for Cotek SP 700~2000 !
    static CotekError parse(List<Byte> errors)
    {
        logger.info("Got {} bytes with error information: {}", errors.size(), errors);

        byte[]     errorArray  = ArrayUtils.toPrimitive(errors.toArray(new Byte[] {}));
        BitSet     errorBitSet = BitSet.valueOf(errorArray);
        CotekError cotekError  = convertBitSet(errorBitSet);

        logger.info("Converted errorBitSet to CotekError: {}", cotekError);

        return cotekError;
    }

    private static CotekError convertBitSet(BitSet errorBitSet)
    {
        boolean olplProtection    = errorBitSet.get(0);
        boolean sofFailProtection = errorBitSet.get(1);
        boolean pOffProtection    = errorBitSet.get(2);
        boolean uvpProtection     = errorBitSet.get(3);
        boolean ovpProtection     = errorBitSet.get(4);
        boolean olpmProtection    = errorBitSet.get(5);
        boolean olphProtection    = errorBitSet.get(6);
        boolean otpProtection     = errorBitSet.get(7);
        boolean utpProtection     = errorBitSet.get(8);
        boolean oocpProtection    = errorBitSet.get(9);

        CotekError cotekError = new CotekError();

        cotekError.setUnderVoltageProtection(uvpProtection);
        cotekError.setOverVoltageProtection(ovpProtection);
        cotekError.setOverLoadProtectionLow(olplProtection);
        cotekError.setOverLoadProtectionMiddle(olpmProtection);
        cotekError.setOverLoadProtectionHigh(olphProtection);
        cotekError.setOutputOverCurrentProtection(oocpProtection);
        cotekError.setOverTemperatureProtection(otpProtection);
        cotekError.setUnderTemperatureProtection(utpProtection);
        cotekError.setSoftStartFailProtection(sofFailProtection);
        cotekError.setPowerOffProtection(pOffProtection);

        return cotekError;
    }
}
