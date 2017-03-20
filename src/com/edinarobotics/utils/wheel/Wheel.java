package com.edinarobotics.utils.wheel;

import com.edinarobotics.utils.common.Updatable;
import com.edinarobotics.utils.math.Math1816;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Wheel represents an abstract wheel that has a {@link SpeedController}
 * implementation.
 */
public abstract class Wheel implements Updatable, SpeedController {
    private SpeedController speedController;
    private String name;
    private double power;
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
     * Indicates whether the Wheel is reversed.
     * @return {@code true} if the wheel is reversed, {@code false} otherwise.
     */
    public boolean isReversed(){
        return isReversed;
    }
    
    /**
     * Sets the power of the speed controller.
     * @param power 
     */
    public void setPower(double power) {
        this.power = Math1816.coerceValue(1.0, -1.0, power);
        update();
    }
    
    /**
     * Updates the speed controller to the set speed.
     */
    public void update() {
        speedController.set((isReversed() ? -1.0 : 1.0) * power);
    }

    /**
     * Returns the most recently set power for the wheel.
     * @return The most recently set power for the wheel.
     */
    public double get() {
        return getPower();
    }

    /**
     * Sets the wheel to the given power. This method ignores
     * the syncGroup parameter.
     * @param power The power to be set.
     * @param syncGroup The sync group for this wheel, ignored.
     */
    public void set(double power, byte syncGroup) {
        setPower(power);
    }

    /**
     * Sets the wheel to the given power.
     * @param power The power to be set.
     */
    public void set(double power) {
        setPower(power);
    }

    /**
     * Stops the wheel and disables the internal speed controller.
     */
    public void disable() {
        setPower(0.0);
        speedController.disable();
    }
    
    /**
     * Writes the given power to the speed controller.
     * @param power The power to write.
     */
    public void pidWrite(double power) {
        set(power);
    }

    /**
     * Sets the motor to be inverted.
     * 
     * @param isInverted Whether the motor should be inverted or not.
     */
	@Override
	public void setInverted(boolean isInverted) {
        this.isReversed = isInverted;		
	}

	/**
	 * Returns if the motor is inverted.
	 */
	@Override
	public boolean getInverted() {
		return isReversed;
	}

	/**
	 * Stops the motor by setting the power to zero.
	 */
	@Override
	public void stopMotor() {
		setPower(0);
	}
    
}
