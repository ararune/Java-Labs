package com.unist.Lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

    @Test
    public void testRGBtoHSL() {
        var HSLValues = new float[3];
        Color.RGBtoHSL(-14683905, HSLValues);

        assertEquals(184.01785,HSLValues[0], 0.1);
        assertEquals(1.0000001,HSLValues[1], 0.1);
        assertEquals(0.56078434,HSLValues[2], 0.1);


    }

    @Test
    public void testDecode() {
        var validHexColor = "0x1FF0FF";

        var expectedRed = 31;
        var expectedGreen = 240;
        var expectedBlue = 255;

        Color actualColor = Color.decode(validHexColor);
        var actualRed = actualColor.getRed();
        var actualGreen = actualColor.getGreen();
        var actualBlue = actualColor.getBlue();

        assertEquals(expectedRed, actualRed);
        assertEquals(expectedGreen, actualGreen);
        assertEquals(expectedBlue, actualBlue);

    }

    @Test
    public void testRGBtoHSB() {
        var HSBValues = new float[3];
        Color.RGBtoHSB(31, 240, 255, HSBValues);

        assertEquals(0.511160671710968,HSBValues[0], 0.0);
        assertEquals(0.8784313797950745,HSBValues[1], 0.0);
        assertEquals(1.0,HSBValues[2], 0.0);

    }

    @Test
    public void testRGBtoCMYK() {
        var CMYKalues = new float[4];
        Color.RGBtoCMYK(31, 240, 255, CMYKalues);

        assertEquals(0.8784314,CMYKalues[0], 0.1);
        assertEquals(0.058823526,CMYKalues[1], 0.1);
        assertEquals(0.0,CMYKalues[2], 0.1);
        assertEquals(0.0,CMYKalues[3], 0.1);

    }
}