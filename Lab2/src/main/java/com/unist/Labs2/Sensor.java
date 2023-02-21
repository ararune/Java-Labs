package com.unist.Labs2;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Sensor {
    private final String topic;
    private final String unit;
    private final String type;
    private final String factor;
    private final double lowerBoundary;
    private final double upperBoundary;
    private double measuredValue;

    public Sensor(String topic, String unit, String type, String factor, double lowerBoundary, double upperBoundary) {
        this.topic = topic;
        this.unit = unit;
        this.type = type;
        this.factor = factor;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
        measuredValue = randInRange(lowerBoundary, upperBoundary);
    }
    public double randInRange(double lowerBoundary, double upperBoundary){
        Random rand = new Random();
        return rand.nextDouble(upperBoundary- lowerBoundary) + lowerBoundary;

    }
    @Override
    public String toString() {
        return  "topic:"+topic + " " + "unit:"+unit + " " + "type:"+type + " " +
                "factor:"+factor + " " + "low:"+lowerBoundary + " " + "up:"+upperBoundary + " " +
                "measured:"+String.format("%.2f", measuredValue);
    }
}
