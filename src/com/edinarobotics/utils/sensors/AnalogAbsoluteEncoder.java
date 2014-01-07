package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Implements a general analog absolute encoder. This class simplifies the
 * task of working with absolute encoders by performing all required voltage
 * calculations and returning a simple angle.
 */
public class AnalogAbsoluteEncoder {
    private AnalogChannel analogChannel;
    private double minVolts, maxVolts;
    private boolean reversed;
    
    /**
     * Constructs a new AnalogAbsoluteEncoder given the analog channel to which
     * it is connected, the voltage that the encoder reads at zero degrees, and
     * the voltage that the encoder reads at 360 degrees.
     * @param analogChannel The analog channel of the absolute encoder.
     * @param voltage0Deg The voltage of the encoder at 0 degrees.
     * @param voltage360Deg The voltage of the encoder at 360 degrees.
     */
    public AnalogAbsoluteEncoder(AnalogChannel analogChannel, double voltage0Deg, double voltage360Deg){
        this.analogChannel = analogChannel;
        minVolts = voltage0Deg;
        maxVolts = voltage360Deg;
        reversed = false;
        if(maxVolts < minVolts){
            double temp = minVolts;
            minVolts = maxVolts;
            maxVolts = temp;
            reversed = true;
        }
    }
    
    /**
     * Returns the raw voltage read from the analog encoder directly from the
     * analog channel.
     * @return The rav voltage (in volts) as read from the analog channel.
     */
    public double getVoltage(){
        return analogChannel.getVoltage();
    }
    
    /**
     * Returns the angle read from the analog encoder in radians. This angle is
     * computed based on the voltages given to the constructor of
     * AnalogAbsoluteEncoder.
     * @return The angle read from the analog encoder in radians.
     */
    public double getAngleRadians(){
        return Math.toRadians(getAngleDegrees());
    }
    
    /**
     * Returns the angle read from the analog encoder in degrees. This angle is
     * computed based on the voltages given to the constructor of
     * AnalogAbsoluteEncoder.
     * @return The angle read from the analog encoder in degrees.
     */
    public double getAngleDegrees(){
        double fractionAboveMin = (getVoltage() - minVolts) / (maxVolts - minVolts);
        double angle = 360.0 * fractionAboveMin;
        if(reversed){
            angle = 360.0 - angle;
        }
        return angle;
    }
}
