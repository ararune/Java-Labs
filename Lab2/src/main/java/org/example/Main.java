package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.unist.Labs2.Publisher;
import com.unist.Labs2.Sensor;
import com.unist.Labs2.Subscriber;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MqttException, IOException {
        Sensor m1 = new Sensor("temperature","C", "int16", "10", -3266.8, 3266.8);
        Sensor m2 = new Sensor("pressure","B", "uint16", "1000", 0, 65.336);
        Sensor m3 = new Sensor("min","L", "uint16", "0", 0, 65336);
        Sensor m4 = new Sensor("10min","L", "uint16", "0", 0, 65336);
        Sensor m5 = new Sensor("hr","L", "uint16", "0", 0, 65336);
        Sensor m6 = new Sensor("day","L", "uint16", "0", 0, 65336);
        Sensor m7 = new Sensor("week","m3", "uint16", "10", 0, 6533.6);
        Sensor m8 = new Sensor("month","m3", "uint16", "10", 0, 6533.6);
        Sensor m9 = new Sensor("year","m3", "uint16", "10", 0, 6533.6);

        List<Sensor> list = new ArrayList<>(List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9));
        Publisher p = new Publisher("tcp://localhost:1883", "sensors","publish_client");

        p.connectClient();
        for (Sensor sensor : list) {
                p.addToMeasure(sensor.toString());
                p.publishMeasure(sensor.toString());
        }
        p.disconnectClient();


    }
}