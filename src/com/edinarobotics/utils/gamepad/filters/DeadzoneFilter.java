package com.edinarobotics.utils.gamepad.filters;

import com.edinarobotics.utils.gamepad.GamepadFilter;
import com.edinarobotics.utils.gamepad.GamepadResult;

/**
 * This filter can be used to apply a deadzone to all the axes of a Gamepad.
 */
public class DeadzoneFilter implements GamepadFilter{
    private double deadzone;
    /**
     * Constructs a DeadzonFilter with the specified radius.
     * @param radius the radius of the deadzone (inclusive).
     */
    public DeadzoneFilter(double radius){
        deadzone = radius;
    }
    
    /**
     * Performs the deadzone filtering.
     * @param toFilter the input GamepadResult.
     * @return the deadzoned GamepadResult.
     */
    public GamepadResult filter(GamepadResult toFilter){
        double lx = deadzone(toFilter.getLeftX());
        double ly = deadzone(toFilter.getLeftY());
        double rx = deadzone(toFilter.getRightX());
        double ry = deadzone(toFilter.getRightY());
        return new GamepadResult(lx,ly,rx,ry);
    }
    
    private double deadzone(double value){
        if(Math.abs(value)<=deadzone){
            return 0;
        }
        return value;
    }
}
