package com.edinarobotics.utils.controllers;

import com.edinarobotics.utils.wheel.Wheel;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControllerWrapper implements SpeedController {

	private Wheel wheel1, wheel2, wheel3, wheel4;
	private boolean inverted;

	/**
	 * Constructs a SpeedControllerWrapper object.
	 * 
	 * @param wheel1
	 *            A wheel object.
	 * @param wheel2
	 *            A wheel object.
	 */
	public SpeedControllerWrapper(Wheel wheel1, Wheel wheel2) {
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
	}

	/**
	 * Constructs a SpeedControllerWrapper object.
	 * 
	 * @param wheel1
	 *            A wheel object.
	 * @param wheel2
	 *            A wheel object.
	 * @param wheel3
	 *            A wheel object.
	 */
	public SpeedControllerWrapper(Wheel wheel1, Wheel wheel2, Wheel wheel3) {
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
		this.wheel3 = wheel3;
	}

	/**
	 * Constructs a SpeedControllerWrapper object.
	 * 
	 * @param wheel1
	 *            A wheel object.
	 * @param wheel2
	 *            A wheel object.
	 * @param wheel3
	 *            A wheel object.
	 * @param wheel4
	 *            A wheel object.
	 */
	public SpeedControllerWrapper(Wheel wheel1, Wheel wheel2, Wheel wheel3, Wheel wheel4) {
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
		this.wheel3 = wheel3;
		this.wheel4 = wheel4;
	}

	@Override
	public void pidWrite(double d) {
		set(d);
	}

	@Override
	public void disable() {
		if (wheel1 != null)
			wheel1.disable();
		if (wheel2 != null)
			wheel2.disable();
		if (wheel3 != null)
			wheel3.disable();
		if (wheel4 != null)
			wheel4.disable();
	}

	@Override
	public double get() {
		return wheel1.get();
	}

	/**
	 * Sets the power of the wheels.
	 * 
	 * @param power
	 */
	@Override
	public void set(double power) {
		if (wheel1 != null)
			wheel1.set(power);
		if (wheel2 != null)
			wheel2.set(power);
		if (wheel3 != null)
			wheel3.set(power);
		if (wheel4 != null)
			wheel4.set(power);
	}

	@Override
	public void set(double d, byte b) {
		if (wheel1 != null)
			wheel1.set(d, b);
		if (wheel2 != null)
			wheel2.set(d, b);
		if (wheel3 != null)
			wheel3.set(d, b);
		if (wheel4 != null)
			wheel4.set(d, b);
	}

	/**
	 * Stops all motors.
	 */
	public void stop() {
		if (wheel1 != null)
			wheel1.set(0.0);
		if (wheel2 != null)
			wheel2.set(0.0);
		if (wheel3 != null)
			wheel3.set(0.0);
		if (wheel4 != null)
			wheel4.set(0.0);
	}

	/**
	 * Sets whether this wrapper should be inverted.
	 * 
	 * @param inverted
	 */
	public void invert(boolean inverted) {
		this.inverted = inverted;
	}

	@Override
	public void setInverted(boolean isInverted) {
		
	}

	@Override
	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}
}
