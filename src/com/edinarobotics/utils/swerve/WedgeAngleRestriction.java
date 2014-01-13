/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

/**
 *
 */
public class WedgeAngleRestriction extends AngleRestriction{
    private double startAngle, endAngle;
    private boolean reverseLogic;
    
    public WedgeAngleRestriction(double startAngleRadians, double endAngleRadians){
        startAngle = startAngleRadians;
        endAngle = endAngleRadians;
        reverseLogic = false;
        if(endAngle < startAngle){
            reverseLogic = true;
            double temp = startAngle;
            startAngle = endAngle;
            endAngle = temp;
        }
    }
    
    public boolean isValidAngle(double angleRadians){
        double workingAngle = angleRadians;
        boolean result = (workingAngle >= startAngle) && (workingAngle <= endAngle);
        if(reverseLogic){
            result = !result;
        }
        return result;
    }
    
    public double getClosestAngle(double angleRadians){
        double workingAngle = angleRadians;
        if(isValidAngle(workingAngle)){
            return workingAngle;
        }
        double startDistance = Math.abs(workingAngle - startAngle);
        double endDistance = Math.abs(workingAngle - endAngle);
        if(startDistance < endDistance){
            return startAngle;
        }
        return endAngle;
    }
}
