package com.unist.Labs2;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PublisherTest {

    @Test
    public void addToMeasureTest() {
        Publisher p = new Publisher("tcp://localhost:1883", "min","publish_client");
        Sensor s = new Sensor("min","L", "uint16", "0", 0, 65336);
        String expectedString= String.format("topic:min unit:L type:uint16 factor:0 low:0.0 up:65336.0 measured:%s", String.format("%.2f", s.getMeasuredValue()));
        String outputString = p.addToMeasure(s.toString());
        Assert.assertEquals(expectedString, outputString);
    }
}