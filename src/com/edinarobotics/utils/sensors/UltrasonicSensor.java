package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

public class UltrasonicSensor {
    private AnalogChannel analogChannel;
    private double scale;
    
    private final boolean isScaled;
    
    public UltrasonicSensor(int sensorChannel) {
        analogChannel = new AnalogChannel(sensorChannel);
        isScaled = false;
    }

    public UltrasonicSensor(int sensorChannel, double scale) {
        analogChannel = new AnalogChannel(sensorChannel);
        this.scale = scale;
        isScaled = true;
    }
    
    public double getDistance() {
        if(isScaled) {
            return scale * getVoltage();
        }
        return getVoltage();
    }
    
    public double getVoltage() {
        return analogChannel.getVoltage();
    }
}
