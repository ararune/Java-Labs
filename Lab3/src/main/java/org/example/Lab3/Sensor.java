package org.example.Lab3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
public class Sensor {
    private Topics topic;
    private Units unit;
    private String type;
    private String factor;
    private double lowerBoundary;
    private double upperBoundary;
    @JsonIgnore
    private double measuredValue;

    public Sensor(Topics topic, Units unit, String type, String factor, double lowerBoundary, double upperBoundary) {
        this.topic = topic;
        this.unit = unit;
        this.type = type;
        this.factor = factor;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
    }
    public double randDouble(){
        Random rand = new Random();
        return rand.nextDouble(this.upperBoundary- this.lowerBoundary) + this.lowerBoundary;

    }
    @Override
    public String toString() {
        return  "topic:"+topic + " " + "unit:"+unit + " " + "type:"+type + " " +
                "factor:"+factor + " " + "low:"+lowerBoundary + " " + "up:"+upperBoundary + " " +
                "measured:"+String.format("%.2f", measuredValue);
    }
}