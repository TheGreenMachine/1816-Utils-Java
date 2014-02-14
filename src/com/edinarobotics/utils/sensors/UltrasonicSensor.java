package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

public class UltrasonicSensor {
    private AnalogChannel analogChannel;
    private double startVoltage;
    private double startDistance;
    private double scale;
    
    private final boolean isScaled;
    
    public UltrasonicSensor(int sensorChannel) {
        analogChannel = new AnalogChannel(sensorChannel);
        
        isScaled = false;
    }

    public UltrasonicSensor(int sensorChannel, double startVoltage, double endVoltage, double startDistance, double endDistance) {
        analogChannel = new AnalogChannel(sensorChannel);
        this.startVoltage = startVoltage;
        this.startDistance = startDistance;
        
        scale = (endDistance - startDistance) / (endVoltage - startVoltage);
        isScaled = true;
    }
    
    public double getDistance() {
        if (isScaled) {
            return scale * (getVoltage() -  startVoltage) + startDistance;
        }
        return -1;
    }
    
    public double getVoltage() {
        return analogChannel.getVoltage();
    }
}
