/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.pid;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Implements a PIDOutput that can be read by an outside user.
 * This can be used to perform more complex processing of PIDController
 * output.
 */
public class ReadablePIDOutput implements PIDOutput{
    private double lastValue;
    
    /**
     * Constructs a new ReadablePIDOutput with default value set to 0.
     */
    public ReadablePIDOutput(){
    
    }
    
    /**
     * This method is used by the PIDController to write the latest control
     * value to this ReadablePIDOutput.
     * @param value The latest PID control value to write to this
     * ReadablePIDOutput.
     */
    public void pidWrite(double value){
        lastValue = value;
    }
    
    /**
     * Returns the latest PID control value that was written to this
     * ReadablePIDOutput.
     * @return The latest PID control value written to this ReadablePIDOutput.
     */
    public double getValue(){
        return lastValue;
    }
}
