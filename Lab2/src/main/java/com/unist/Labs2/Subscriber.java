package com.unist.Labs2;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class Subscriber {

    public static void main(String[] args) {

        final String broker = "tcp://localhost:1883";
        final String[] topics = {"temperature", "pressure", "day", "month", "min", "10min", "hr", "week", "year", "sensors"};
        final int[] qos = {1, 2, 1, 0, 1, 0, 1, 2, 1, 1};
        final String clientId = "subscribe_client";


        try {
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();

            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);

            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });

            client.connect(options);
            client.subscribe(topics, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
