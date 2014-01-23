package com.edinarobotics.utils.gamepad.gamepadfilters;

/**
 * This filter multiplies the values of each gamepad axis by a given value.
 * This multiplication is done simply, negative values will negate the gamepad
 * axis and values greater than {@code 1} can bring the axis range outside of
 * the standard {@code -1} to {@code 1} range.
 */
public class GamepadConstantMultipleFilter extends SimpleGamepadFilter{
    private final double multiplier;
    
    /**
     * Constructs a new GamepadConstantMultipleFilter that will multiply each
     * axis value by the given multiplier.
     * @param multiplier The multiplier to be applied to each axis value.
     */
    public GamepadConstantMultipleFilter(double multiplier){
        this.multiplier = multiplier;
    }

    /**
     * Internal function which applies the multiplier to the gamepad axes.
     * @param value The current value of the gamepad axis.
     * @return The filtered value of the gamepad axis.
     */
    protected double applyFilter(double value) {
        return multiplier*value;
    }
}
