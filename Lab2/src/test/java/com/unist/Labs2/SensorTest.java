package com.unist.Labs2;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SensorTest {

    @Test
    public void randInRangeTest() {
        Sensor s = new Sensor("temperature","C", "int16", "10", -3266.8, 3266.8);
        Random rand = new Random();
        s.setMeasuredValue(rand.nextDouble(s.getUpperBoundary() - s.getLowerBoundary()) + s.getLowerBoundary());

        Assert.assertTrue(s.getLowerBoundary() <= s.getMeasuredValue() && s.getMeasuredValue() <= s.getUpperBoundary());
    }
}