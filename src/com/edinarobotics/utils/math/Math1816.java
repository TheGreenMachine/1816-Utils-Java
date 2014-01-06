package com.edinarobotics.utils.math;

/**
 * This class provides common math functions that are not implemented
 * elsewhere in WPILib.
 */
public final class Math1816 {
    
    private Math1816(){
        //Hide constructor
    }
    
    /**
     * Implements a signum function.
     * 
     * If the parameter is {@code NaN} or {@code 0.0} returns the original value.
     * <br/>
     * If the parameter is positive, returns {@code 1.0}.<br/>
     * If the parameter is negative, returns {@code -1.0}<br/>
     * @param num The number whose signum will be returned.
     * @return The signum of the number as described above.,
     */
    public static double signum(double num){
        if(Double.isNaN(num) || num == 0.0){
            //For NaN or 0, return the same value
            return num;
        }
        if(num > 0.0){
            //If positive, return 1
            return 1;
        }
        //Only case left is negative
        return -1;
    }
    
    /**
     * Implements a signum function.
     * 
     * If the parameter is {@code NaN} or {@code 0.0} returns the original value.
     * <br/>
     * If the parameter is positive, returns {@code 1.0}.<br/>
     * If the parameter is negative, returns {@code -1.0}<br/>
     * @param num The number whose signum will be returned.
     * @return The signum of the number as described above.,
     */
    public static float signum(float num){
        if(Double.isNaN(num) || num == 0.0){
            //For NaN or 0, return the same value
            return num;
        }
        if(num > 0.0){
            //If positive, return 1
            return 1;
        }
        //Only case left is negative
        return -1;
    }
}
