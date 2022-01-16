package com.logreposit.cotek.cotekapi.communication.serial;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isFalse();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isFalse();
            softly.assertThat(cotekError.isPowerOffProtection()).isFalse();
        });
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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isTrue();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isFalse();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isFalse();
            softly.assertThat(cotekError.isPowerOffProtection()).isFalse();
        });
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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isFalse();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isTrue();
            softly.assertThat(cotekError.isPowerOffProtection()).isFalse();
        });
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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isFalse();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isFalse();
            softly.assertThat(cotekError.isPowerOffProtection()).isTrue();
        });
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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isTrue();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isFalse();
            softly.assertThat(cotekError.isPowerOffProtection()).isFalse();
        });
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

        assertSoftly(softly -> {
            softly.assertThat(cotekError.isUnderVoltageProtection()).isFalse();
            softly.assertThat(cotekError.isOverVoltageProtection()).isTrue();
            softly.assertThat(cotekError.isOverLoadProtectionLow()).isFalse();
            softly.assertThat(cotekError.isOverLoadProtectionMiddle()).isTrue();
            softly.assertThat(cotekError.isOverLoadProtectionHigh()).isFalse();
            softly.assertThat(cotekError.isOutputOverCurrentProtection()).isTrue();
            softly.assertThat(cotekError.isOverTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isUnderTemperatureProtection()).isFalse();
            softly.assertThat(cotekError.isSoftStartFailProtection()).isFalse();
            softly.assertThat(cotekError.isPowerOffProtection()).isFalse();
        });
    }
}
