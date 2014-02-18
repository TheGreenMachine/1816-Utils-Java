package com.edinarobotics.utils.wheel;

import com.edinarobotics.utils.common.Updatable;
import com.edinarobotics.utils.math.Math1816;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Wheel represents an abstract wheel that has a {@link SpeedController}
 * implementation.
 */
public class Wheel implements Updatable, SpeedController {
    private SpeedController speedController;
    private String name;
    protected double power;
    private boolean isReversed;
    
    /**
     * Constructs a Wheel object.
     * @param name A name for the PID Tuning Bench to identify this wheel.
     * Eg. "FRONT_LEFT"
     * @param speedController The speed controller for this wheel's motor.
     */
    public Wheel(String name, SpeedController speedController, boolean isReversed) {
        this.name = name;
        this.speedController = speedController;
        this.power = 0;
        this.isReversed = isReversed;
    }
    
    /**
     * Returns the speed controller associated with this wheel.
     * @return The speed controller object.
     */
    public SpeedController getSpeedController() {
        return speedController;
    }

    /**
     * Returns the name of this wheel.
     * @return The wheel's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current power of this wheel.
     * @return Current power of the wheel
     */
    public double getPower() {
        return power;
    }
    
    /**
     * Sets the power of the speed controller.
     * @param power 
     */
    public void setPower(double power) {
        if(isReversed) {
            power*=-1;
        }
        this.power = Math1816.coerceValue(1.0, -1.0, power);
        update();
    }
    
    /**
     * Updates the speed controller to the set speed.
     */
    public void update() {
        speedController.set(power);
    }

    public double get() {
        return getPower();
    }

    public void set(double d, byte b) {
        setPower(d);
    }

    public void set(double d) {
        setPower(d);
    }

    public void disable() {
        setPower(0.0);
        speedController.disable();
    }

    public void pidWrite(double d) {
        setPower(d);
    }
    
}
