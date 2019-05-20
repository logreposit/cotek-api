package com.logreposit.cotek.cotekapi.communication.serial;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CotekErrorParserTests
{
    @Test
    public void testParse_everythingFalse()
    {
        byte       byte0      = 0;
        byte       byte1      = 0;
        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertFalse(cotekError.isOverVoltageProtection());
        Assert.assertFalse(cotekError.isOverLoadProtectionLow());
        Assert.assertFalse(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOutputOverCurrentProtection());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertFalse(cotekError.isSoftStartFailProtection());
        Assert.assertFalse(cotekError.isPowerOffProtection());
    }

    @Test
    public void testParse_b0_olpl()
    {
        byte byte0 = 0;

        byte0 = (byte) (byte0 | (1 << 0));

        byte       byte1      = 0;
        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertTrue(cotekError.isOverLoadProtectionLow());
        Assert.assertFalse(cotekError.isSoftStartFailProtection());
        Assert.assertFalse(cotekError.isPowerOffProtection());
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertFalse(cotekError.isOverVoltageProtection());
        Assert.assertFalse(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertFalse(cotekError.isOutputOverCurrentProtection());
    }

    @Test
    public void testParse_b1_sofFail()
    {
        byte byte0 = 0;

        byte0 = (byte) (byte0 | (1 << 1));

        byte       byte1      = 0;
        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertFalse(cotekError.isOverLoadProtectionLow());
        Assert.assertTrue(cotekError.isSoftStartFailProtection());
        Assert.assertFalse(cotekError.isPowerOffProtection());
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertFalse(cotekError.isOverVoltageProtection());
        Assert.assertFalse(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertFalse(cotekError.isOutputOverCurrentProtection());
    }

    @Test
    public void testParse_b2_pOff()
    {
        byte byte0 = 0;

        byte0 = (byte) (byte0 | (1 << 2));

        byte       byte1      = 0;
        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertFalse(cotekError.isOverLoadProtectionLow());
        Assert.assertFalse(cotekError.isSoftStartFailProtection());
        Assert.assertTrue(cotekError.isPowerOffProtection());
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertFalse(cotekError.isOverVoltageProtection());
        Assert.assertFalse(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertFalse(cotekError.isOutputOverCurrentProtection());
    }

    @Test
    public void testParse_b9_pOff()
    {
        byte byte0 = 0;
        byte byte1 = 0;

        byte1 = (byte) (byte1 | (1 << 1));

        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertFalse(cotekError.isOverLoadProtectionLow());
        Assert.assertFalse(cotekError.isSoftStartFailProtection());
        Assert.assertFalse(cotekError.isPowerOffProtection());
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertFalse(cotekError.isOverVoltageProtection());
        Assert.assertFalse(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertTrue(cotekError.isOutputOverCurrentProtection());
    }

    @Test
    public void testParse_b4_b5_b9_pOff()
    {
        byte byte0 = 0;
        byte byte1 = 0;

        byte0 = (byte) (byte0 | (1 << 4));
        byte0 = (byte) (byte0 | (1 << 5));
        byte1 = (byte) (byte1 | (1 << 1));

        byte[]     bytes      = new byte[] {byte0, byte1};
        List<Byte> byteList   = Arrays.asList(ArrayUtils.toObject(bytes));
        CotekError cotekError = CotekErrorParser.parse(byteList);

        Assert.assertNotNull(cotekError);
        Assert.assertFalse(cotekError.isOverLoadProtectionLow());
        Assert.assertFalse(cotekError.isSoftStartFailProtection());
        Assert.assertFalse(cotekError.isPowerOffProtection());
        Assert.assertFalse(cotekError.isUnderVoltageProtection());
        Assert.assertTrue(cotekError.isOverVoltageProtection());
        Assert.assertTrue(cotekError.isOverLoadProtectionMiddle());
        Assert.assertFalse(cotekError.isOverLoadProtectionHigh());
        Assert.assertFalse(cotekError.isOverTemperatureProtection());
        Assert.assertFalse(cotekError.isUnderTemperatureProtection());
        Assert.assertTrue(cotekError.isOutputOverCurrentProtection());
    }
}
