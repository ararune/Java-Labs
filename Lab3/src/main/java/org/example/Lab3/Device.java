package org.example.Lab3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


@Setter
@Getter
@NoArgsConstructor
public class Device {
    private String broker;
    private String topic;
    private String clientId;
    @JsonIgnore
    private MqttClient client;
    @JsonIgnore
    private String filePath;
    @JsonIgnore
    private Sensor readSensor = new Sensor();
    private ArrayList<Sensor> sensorList = new ArrayList<>();

    public void addSensor(Sensor newSensor) {
        this.sensorList.add(newSensor);
    }

    public void connectClient() throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        client.connect(options);
    }

    public void disconnectClient() throws MqttException {
        this.client.disconnect();
        this.client.close();
    }

    public void publishMessage(MqttMessage message, int qos) throws MqttException {
        message.setQos(qos);
        client.publish(topic, message);
    }
    public void initialize(Device d, String broker, String topic, String clientId, String filePath){
        d.setFilePath(filePath);
        d.setBroker(broker);
        d.setTopic(topic);
        d.setClientId(clientId);
    }

    public void serialize(Device d, String filePath) {
        ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            mapper.writeValue(new File(filePath), d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deserializeDevice(String filePath) {
        Device deserialize = new Device();
        try {
            deserialize = new ObjectMapper().readValue(new File(filePath), Device.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deserialize.runClient();
    }
    public void runClient() {
        int qos = 0;

        for (Sensor sensor : sensorList) {
            readSensor = sensor;
            readSensor.setMeasuredValue(readSensor.randDouble());
            String output = readSensor.toString();
            try {
                connectClient();
                System.out.println("Message published!");
                System.out.println(output);
                MqttMessage message = new MqttMessage(output.getBytes(StandardCharsets.UTF_8));
                publishMessage(message, qos);
                disconnectClient();
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                me.printStackTrace();
            }
        }
        System.exit(0);
    }

}