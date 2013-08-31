package com.edinarobotics.utils.gamepad.filters;

import com.edinarobotics.utils.gamepad.GamepadFilter;
import com.edinarobotics.utils.gamepad.GamepadResult;
import com.sun.squawk.util.MathUtils;

/**
 * Implements a gamepad joystick filter that raises the value of all axes
 * to a given power. Regardless of the value of this power, this filter
 * preserves the sign of the joystick value (a negative joystick value
 * will still be negative after this filter is applied).
 */
public class ScalingPowerFilter implements GamepadFilter{
    private int power;
    
    /**
     * Constructs a new ScalingPowerFilter that will raise the value of all
     * gamepad axes to the given {@code power}.
     * @param power The power to which all gamepad axis values will be raised.
     */
    public ScalingPowerFilter(int power){
        this.power = power;
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
     * values. Raises the given value to the specified power,
     * takes takes the absolute value of the result and multiplies that
     * value by the sign of the original value.
     * @param value The {@code double} that is to be filtered.
     * @return A double with this filter operation applied to it.
     */
    private double filter(double value){
        return signum(value)*Math.abs(MathUtils.pow(value, power));
    }
    /**
     * Internal signum function. Returns the sign of the given value as a byte.
     * @param value The value whose sign is to be returned.
     * @return The sign of the given value.
     */
    private byte signum(double value){
        if(value > 0){
            return 1;
        }
        else if(value < 0){
            return -1;
        }
        return 0;
    }

}
