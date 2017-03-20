package com.edinarobotics.utils.wheel;

import com.edinarobotics.utils.math.Math1816;
import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDConstant;
import com.edinarobotics.utils.pid.PIDTuningManager;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControlledWheel extends Wheel {
    private PIDConstant defaultPID;
    private PIDController pidController;
    private PIDConfig pidConfig;
    private Encoder encoder;
    private double maxWheelRPM;
    private double targetWheelRPM;
    
    private final double GEAR_RATIO; // Ratio is (wheel / encoder)
    
    /**
     * Constructs a SpeedControlledWheel object.
     * 
     * @param name A name for the PIDTuningManager to identify this wheel by.
     * @param speedController The speed controller for this wheel's motor.
     * @param maxWheelRPM The maximum RPM that this wheel should ever travel at.
     * @param pidConstant The pidConstant for this wheel's speed controller.
     * @param encoder The encoder that is attached to this wheel's motor.
     * @param gearRatio The gear ratio for this wheel's motor.
     * @param rotationsPerPulse The amount of rotations that occur per pulse.
     * @param reversed Whether or not this wheel should be inverted or not.
     */
    public SpeedControlledWheel(String name, SpeedController speedController, double maxWheelRPM, 
            PIDConstant pidConstant, Encoder encoder, double gearRatio, double rotationsPerPulse, boolean reversed) {
        super(name, speedController, reversed);
        this.defaultPID = pidConstant;
        this.GEAR_RATIO = gearRatio;
        this.encoder = encoder;
        this.maxWheelRPM = maxWheelRPM;
        encoder.setDistancePerPulse(rotationsPerPulse);
        //this.encoder.start();
        pidConfig = PIDTuningManager.getInstance().getPIDConfig(name);
        this.pidController = new PIDController(defaultPID.getP(), defaultPID.getI(),
                                defaultPID.getD(), defaultPID.getF(), encoder, getSpeedController(),
                0.025);
        this.pidController.setOutputRange(-1.0, 1.0);
        pidController.enable();
    }
    
    /**
     * Sets the target RPM that this wheel should be traveling at.
     * @param targetWheelRPM The RPM that this wheel should be traveling at.
     */
    public void setSpeed(double targetWheelRPM) {
        this.targetWheelRPM = Math1816.coerceValue(maxWheelRPM, -maxWheelRPM, targetWheelRPM);
        update();
    }
    
    /**
     * Sets the target "power" that this wheel should be using.
     * @param power A decimal value representing the percentage of power the 
     * 	wheel should travel at.
     */
    public void setPower(double power) {
        setSpeed(power * maxWheelRPM);
        update();
    }
    
    /**
     * Returns the wheel's target speed in RPM.
     * @return The wheel's target speed in RPM.
     */
    public double getTargetSpeed() {
        return targetWheelRPM;
    }
    
    /**
     * Returns the wheel's actual current speed in RPM.
     * @return The wheel's current speed in RPM.
     */
    public double getCurrentSpeed() {
        return encoder.getRate() * GEAR_RATIO;   
    }
    
    /**
     * Updates the speed controller to the set speed.
     */
    public void update() {
        pidConfig.setValue(encoder.pidGet());
        if(pidConfig.shouldOverrideRawControl()) {
            pidController.disable();
            getSpeedController().set(pidConfig.getRemoteRawControlValue());
        }
        else {
            pidController.enable();
            pidController.setPID(pidConfig.getP(defaultPID.getP()), 
                    pidConfig.getI(defaultPID.getI()), 
                    pidConfig.getD(defaultPID.getD()),
                    pidConfig.getF(defaultPID.getF()));
            double encoderRPS = ((1.0/GEAR_RATIO) * targetWheelRPM * (isReversed() ? -1.0 : 1.0))/60.0;
            pidConfig.setSetpoint(encoderRPS);
            pidController.setSetpoint(pidConfig.getSetpoint());
        }
    }
}
