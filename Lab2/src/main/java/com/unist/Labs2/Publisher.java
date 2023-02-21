package com.unist.Labs2;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Publisher {
    private final String broker;
    private final String topic;
    private final String clientId;
    private MqttClient client;
    private MqttMessage message;

    public Publisher(String broker, String topic, String client) {
        this.broker = broker;
        this.topic = topic;
        this.clientId = client;
    }
    public void connectClient() throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        client.connect(options);
    }

    public void disconnectClient() throws MqttException {
        this.client.disconnect();
        this.client.close();
    }
    public String addToMeasure(String content){
        message = new MqttMessage(content.getBytes());
        int qos = 1;
        message.setQos(qos);
        return content;
    }

    public void publishMeasure(String content) throws MqttException {
        client.publish(topic, message);
        System.out.println("Content published");
        System.out.println("content: " + content);
    }



}
