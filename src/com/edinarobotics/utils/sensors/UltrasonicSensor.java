package com.edinarobotics.utils.sensors;

import java.util.Arrays;
import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicSensor {
    private AnalogInput AnalogInput;
    private double scale;
    private int TO_AVERAGE = 35;
    private int lastValue = 0;
    private double[] distances = new double[TO_AVERAGE];
    
    private final boolean isScaled;
    
    /**
     * Creates an Ultrasonic Sensor object.
     * @param sensorChannel The channel the sensor is wired into.
     */
    public UltrasonicSensor(int sensorChannel) {
        AnalogInput = new AnalogInput(sensorChannel);
        isScaled = false;
    }

    /**
     * Creates an Ultrasonic Sensor object with a scaler value.
     * @param sensorChannel The channel the sensor is wired into.
     * @param scale The scaling factor for ouputs.
     */
    public UltrasonicSensor(int sensorChannel, double scale) {
        AnalogInput = new AnalogInput(sensorChannel);
        this.scale = scale;
        isScaled = true;
    }
    
    /**
     * Returns your curernt distance from the object in front of the sensor.
     * @return Your current distance from the object in front of the sensor.
     */
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
    
    /**
     * Returns the current averageArr value.
     * @return The current averageArr value.
     */
    private double averageArr(){
        double[] workingArray = Arrays.copyOf(distances, distances.length);
        Arrays.sort(workingArray);
        int lowIndex = (int)Math.ceil(workingArray.length * 0.1);
        int highIndex = (int)Math.ceil(workingArray.length * 0.9);
        double sum = 0;
        for(int i=lowIndex; i<highIndex; i++){
            sum += distances[i];
        }
        return sum / ((highIndex - lowIndex) * 1.0);
    }
    
    /**
     * Returns the current voltage that the sensor is operating at.
     * @return The current voltage that the sensor is operating at.
     */
    public double getVoltage() {
        return AnalogInput.getVoltage();
    }
}
