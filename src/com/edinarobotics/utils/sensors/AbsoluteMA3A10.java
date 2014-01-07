package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 * Implements an analog absolute encoder class for US Digital's
 * analog (A10) model of the MA3 series of absolute encoders.
 * These encoder read from 0V-5V over a range of 0 - 360 degrees.
 */
public class AbsoluteMA3A10 extends AnalogAbsoluteEncoder{
    
    /**
     * Constructs a new AbsoluteMA3A10 encoder connected to the given
     * analog channel.
     * @param analogChannel The analog channel to which the absolute encoder
     * is connected.
     */
    public AbsoluteMA3A10(AnalogChannel analogChannel){
        super(analogChannel, 0.0, 5.0);
    }
}
