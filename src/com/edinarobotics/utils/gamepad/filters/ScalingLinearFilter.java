package com.edinarobotics.utils.gamepad.filters;

import com.edinarobotics.utils.gamepad.GamepadFilter;
import com.edinarobotics.utils.gamepad.GamepadResult;

/**
 * Applies a linear scale to all axes of a gamepad's joysticks. This filter
 * is computed by multiplying the value of all axes by a given constant.
 */
public class ScalingLinearFilter implements GamepadFilter{
    double slope;
    
    /**
     * Constructs a new ScalingLinearFilter that applies the linear function
     * with given slope to all axes of a gamepad's joysticks.
     * @param slope 
     */
    public ScalingLinearFilter(double slope){
        this.slope = slope;
    }
    
    /**
     * Applies this filter to the given GamepadResult.
     * @param toFilter The GamepadResult to which this filter is to be applied.
     * @return A new GamepadResult that is equivalent to {@code toFilter} after
     * this filter is applied.
     */
    public GamepadResult filter(GamepadResult toFilter){
        return new GamepadResult(filter(toFilter.getLeftX()),
                filter(toFilter.getLeftY()),
                filter(toFilter.getRightX()),
                filter(toFilter.getRightY()));
    }
    
    /**
     * Internal function used to apply the filter value to {@code double}
     * values. It multiplies the value by the given constant, {@code slope}.
     * @param value The {@code double} that is to be filtered.
     * @return A double with this filter operation applied to it.
     */
    private double filter(double value){
        return slope*value;
    }
}
