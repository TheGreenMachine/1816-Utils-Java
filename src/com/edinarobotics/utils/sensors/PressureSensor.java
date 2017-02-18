package com.edinarobotics.utils.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor {
	
	private AnalogInput input;
	private double inputVoltage;
	
	private static final double DEFAULT_VOLTAGE = 5.0;
	private final int SLOPE = 250;
	private final int Y_INTERCEPT = -15;
	
	/**
	 * Creates a new Pressure Sensor connected to the given analog channel.
	 * 
	 * @param input The port that the Pressure Sensor is plugged into. 
	 * @param inputVoltage The voltage that we are providing to the sensor.
	 */
	public PressureSensor(int input, double inputVoltage) {
		this.input = new AnalogInput(input);
		this.inputVoltage = inputVoltage;
	}
	
	/**
	 * Creates a new Pressure Sensor connected to the given analog channel.
	 * 
	 * @param input The port that the Pressure Sensor is plugged into.
	 */
	public PressureSensor(int input) {
		this.input = new AnalogInput(input);
		this.inputVoltage = DEFAULT_VOLTAGE;
	}
	
	/**
	 * @return The pressure (psi) the sensor is reading.
	 */
	public double getPressure() {
		return  SLOPE * (input.getVoltage()/inputVoltage) + Y_INTERCEPT;
	}
	
	/**
	 * @return The voltage that the Pressure Sensor is receiving.
	 */
	public double getVoltage() {
		return input.getVoltage();
	}

}
