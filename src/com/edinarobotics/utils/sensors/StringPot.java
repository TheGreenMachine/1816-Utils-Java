package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * The StringPot class defines a String Potentiometer and provides methods to get the raw
 * voltage of the potentiometer and the length of the string based on the same voltage.
 */
public class StringPot {
    private AnalogChannel analogChannel;
    private double minVoltage;
    private double maxVoltage;
    private double stringLength;
    private boolean reverseRange;
    
    /**
     * Creates a String Potentiometer, the minimum and maximum voltages, the string's
     * length, and reverses the range of the voltages if specified.
     * @param analogChannel The Analog Channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     * @param reverseRange Whether to reverse the range of the voltages.
     */
    public StringPot(AnalogChannel analogChannel, double minVoltage, double maxVoltage,
            double stringLength, boolean reverseRange) {
        this.analogChannel = analogChannel;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.stringLength = stringLength;
        this.reverseRange = reverseRange;
    }
    
    /**
     * Creates a String Potentiometer on the specified channel, sets the minimum
     * and maximum voltages, the string's length, and reverses the range of the
     * voltages if specified.
     * @param channel The potentiometer's channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     * @param reverseRange Whether to reverse the range of the voltages.
     */
    public StringPot(int channel, double minVoltage, double maxVoltage, double stringLength,
            boolean reverseRange) {
        this(new AnalogChannel(channel), minVoltage, maxVoltage, stringLength, reverseRange);
    }
    
    /**
     * Creates a String Potentiometer on the specified channel and slot, sets the minimum
     * and maximum voltages, the string's length, and reverses the range of the
     * voltages if specified.
     * @param slot Slot on which potentiometer is inserted.
     * @param channel The potentiometer's channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     * @param reverseRange Whether to reverse the range of the voltages.
     */
    public StringPot(int slot, int channel, double minVoltage, double maxVoltage,
            double stringLength, boolean reverseRange) {
        this(new AnalogChannel(slot, channel), minVoltage, maxVoltage, stringLength,
                reverseRange);
    }
    
    /**
     * Creates a String Potentiometer, the minimum and maximum voltages, the string's length.
     * @param analogChannel The Analog Channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     */
    public StringPot(AnalogChannel analogChannel, double minVoltage, double maxVoltage,
            double stringLength) {
        this(analogChannel, minVoltage, maxVoltage, stringLength, false);
    }
    
    /**
     * Creates a String Potentiometer on the specified channel, sets the minimum
     * and maximum voltages, the string's length.
     * @param channel The potentiometer's channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     */
    public StringPot(int channel, double minVoltage, double maxVoltage, double stringLength) {
        this(new AnalogChannel(channel), minVoltage, maxVoltage, stringLength, false);
    }
    
    /**
     * Creates a String Potentiometer on the specified channel and slot, sets the minimum
     * and maximum voltages, the string's length.
     * @param slot Slot on which potentiometer is inserted.
     * @param channel The potentiometer's channel.
     * @param minVoltage The minimum value of the voltage range.
     * @param maxVoltage The maximum value of the voltage range.
     * @param stringLength The length of the potentiometer's string.
     */
    public StringPot(int slot, int channel, double minVoltage, double maxVoltage,
            double stringLength) {
        this(new AnalogChannel(slot, channel), minVoltage, maxVoltage, stringLength, false);
    }
    
    /**
     * Returns the raw voltage on the potentiometer.
     * @return Raw voltage on potentiometer.
     */
    public double getRawVoltage() {
        return analogChannel.getVoltage();
    }
    
    /**
     * Returns the minimum voltage on the potentiometer.
     * @return The potentiometer's minimum voltage.
     */
    public double getMinVoltage() {
        return minVoltage;
    }
    
    /**
     * Returns the maximum voltage on the potentiometer.
     * @return The potentiometer's maximum voltage.
     */
    public double getMaxVoltage() {
        return maxVoltage;
    }
    
    /**
     * Returns the length of the string attached to the potentiometer based on the
     * raw voltage on the potentiometer.
     * @return The length of the potentiometer's string.
     */
    public double getStringLength() {
        if(reverseRange == false) {
            return ((analogChannel.getVoltage() - minVoltage)/(maxVoltage - minVoltage))
                    * stringLength;
        } else {
            return ((analogChannel.getVoltage() - maxVoltage)/(minVoltage - maxVoltage))
                    * stringLength;
        }
    }
    
}
