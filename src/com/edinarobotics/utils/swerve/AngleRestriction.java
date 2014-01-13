/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

/**
 *
 */
public abstract class AngleRestriction {
    public abstract boolean isValidAngle(double angleRadians);
    
    public abstract double getClosestAngle(double angleRadians);
    
    protected double normalizeAngle(double angleRadians){
        if(angleRadians < 0){
            return (Math.PI * 2.0) - angleRadians;
        }
        if(angleRadians > (Math.PI * 2.0)){
            return angleRadians - (Math.PI * 2.0);
        }
        return angleRadians;
    }
}
