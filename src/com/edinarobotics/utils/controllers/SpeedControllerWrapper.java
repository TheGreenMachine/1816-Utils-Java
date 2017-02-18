package com.edinarobotics.utils.controllers;

import java.util.ArrayList;
import java.util.List;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControllerWrapper implements SpeedController {
	
	private List<CANTalon> wheels = new ArrayList<CANTalon>();
	private boolean inverted;
	
	/**
	 *  Constructs a SpeedControllerWrapper object.
	 * 
	 * @param wheels
	 * 			A collection of wheel objects.
	 */
	public SpeedControllerWrapper(CANTalon... wheels) {
		for (CANTalon wheel : wheels) {
			this.wheels.add(wheel);
		}
	}

	@Override
	public void pidWrite(double d) {
		set(d);
	}

	@Override
	public void disable() {
		for (CANTalon wheel : wheels) {
			wheel.disable();
		}
	}

	/**
	 * Gets the speed that the speed controllers are traveling at.
	 */
	@Override
	public double get() {
		return wheels.get(0).get();
	}

	/**
	 * Sets the power of the wheels.
	 * 
	 * @param power
	 */
	@Override
	public void set(double power) {
		for (CANTalon wheel : wheels) {
			wheel.set(power);
		}
	}

	/**
	 * Stops all motors.
	 */
	public void stop() {
		for (CANTalon wheel : wheels) {
			wheel.set(0.0);
		}
	}

	/**
	 * Sets whether this wrapper should be inverted.
	 * 
	 * @param inverted
	 */
	@Override
	public void setInverted(boolean isInverted) {
		for (CANTalon wheel : wheels) {
			wheel.setInverted(isInverted);
		}
	}

	/**
	 * Gets whether the wheels are inverted.
	 */
	@Override
	public boolean getInverted() {
		return wheels.get(0).getInverted();
	}
	
	public void setPID(double p, double i, double d) {
		for (CANTalon wheel : wheels) {
			wheel.setPID(p, i, d);
		}
	}
	
	public void enableBrakeMode(boolean isBrakeMode) {
		for (CANTalon wheel : wheels) {
			wheel.enableBrakeMode(isBrakeMode);
		}
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}
}
