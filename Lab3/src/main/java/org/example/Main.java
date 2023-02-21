package org.example;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.Lab3.Device;
import org.example.Lab3.Sensor;
import org.example.Lab3.Topics;
import org.example.Lab3.Units;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Lab3.Device.deserializeDevice;


public class Main {
    public static void main(String[] args) throws MqttException, IOException {
        // DESERIALIZATION FROM JSON
        String filePath = "/home/ararune/Documents/Java Labovi/Lab3/src/main/java/org/example/sensors.json";
        deserializeDevice(filePath);

        // SERIALIZATION TO JSON
        Sensor temp = new Sensor(Topics.TEMPERATURE, Units.C, "int16", "10", -3266.8, 3266.8);
        Sensor press = new Sensor(Topics.PRESSURE, Units.B, "uint16", "1000", 0, 65.336);
        Sensor c1 = new Sensor(Topics.CONSUMPTION_PER_MIN, Units.L, "uint16", "0", 0, 65336);
        Sensor c2 = new Sensor(Topics.CONSUMPTION_PER_10, Units.L, "uint16", "0", 0, 65336);
        Sensor c3 = new Sensor(Topics.CONSUMPTION_PER_HR, Units.L, "uint16", "0", 0, 65336);
        Sensor c4 = new Sensor(Topics.CONSUMPTION_PER_DAY, Units.L, "uint16", "0", 0, 65336);
        Sensor c5 = new Sensor(Topics.CONSUMPTION_PER_WEEK, Units.m3, "uint16", "10", 0, 6533.6);
        Sensor c6 = new Sensor(Topics.CONSUMPTION_PER_MONTH, Units.m3, "uint16", "10", 0, 6533.6);
        Sensor c7 = new Sensor(Topics.CONSUMPTION_PER_YEAR, Units.m3, "uint16", "10", 0, 6533.6);

        Device device = new Device();
        List<Sensor> sensorsList = new ArrayList<>(List.of(temp, press, c1, c2, c3, c4, c5, c6, c7));
        for (Sensor sensor : sensorsList) {
            device.addSensor(sensor);
        }

        device.initialize(device, "tcp://localhost:1883", "Sensor", "publish_client",filePath);
        device.serialize(device, filePath);
    }

}