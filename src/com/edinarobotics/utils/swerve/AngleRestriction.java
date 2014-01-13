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
    
}
