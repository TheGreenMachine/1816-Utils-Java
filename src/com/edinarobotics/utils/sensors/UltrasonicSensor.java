package com.edinarobotics.utils.sensors;

import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.AnalogChannel;

public class UltrasonicSensor {
    private AnalogChannel analogChannel;
    private double scale;
    private int TO_AVERAGE = 35;
    private int lastValue = 0;
    private double[] distances = new double[TO_AVERAGE];
    
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
        double toReturn;
        if(isScaled) {
            toReturn = scale * getVoltage();
        }
        else{
            toReturn = getVoltage();
        }
        lastValue++;
        if(lastValue >= distances.length){
            lastValue = 0;
        }
        distances[lastValue] = toReturn;
        return averageArr();
    }
    
    private double averageArr(){
        double[] workingArray = Arrays.copy(distances, 0, distances.length, 0, distances.length);
        Arrays.sort(workingArray);
        int lowIndex = (int)Math.ceil(workingArray.length * 0.1);
        int highIndex = (int)Math.ceil(workingArray.length * 0.9);
        double sum = 0;
        for(int i=lowIndex; i<highIndex; i++){
            sum += distances[i];
        }
        return sum / ((highIndex - lowIndex) * 1.0);
    }
    
    public double getVoltage() {
        return analogChannel.getVoltage();
    }
}
