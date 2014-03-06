package com.edinarobotics.utils.wheel;

import com.edinarobotics.utils.math.Math1816;
import com.edinarobotics.utils.pid.PIDConfig;
import edu.wpi.first.wpilibj.SpeedController;
import com.edinarobotics.utils.pid.PIDConstant;
import com.edinarobotics.utils.pid.PIDTuningManager;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;

public class SpeedControlledWheel extends Wheel {
    private PIDConstant defaultPID;
    private PIDController pidController;
    private PIDConfig pidConfig;
    private Encoder encoder;
    private double maxWheelRPM;
    private double targetWheelRPM;
    
    private final double GEAR_RATIO; // Ratio is (wheel / encoder)
    
    public SpeedControlledWheel(String name, SpeedController speedController, double maxWheelRPM, 
            PIDConstant pidConstant, Encoder encoder, double gearRatio, double rotationsPerPulse, boolean reversed) {
        super(name, speedController, reversed);
        this.defaultPID = pidConstant;
        this.GEAR_RATIO = gearRatio;
        this.encoder = encoder;
        this.maxWheelRPM = maxWheelRPM;
        encoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate);
        encoder.setDistancePerPulse(rotationsPerPulse);
        this.encoder.start();
        pidConfig = PIDTuningManager.getInstance().getPIDConfig(name);
        this.pidController = new PIDController(defaultPID.getP(), defaultPID.getI(),
                                defaultPID.getD(), defaultPID.getF(), encoder, getSpeedController(),
                0.025);
        this.pidController.setOutputRange(-1.0, 1.0);
        pidController.enable();
    }
    
    public void setSpeed(double targetWheelRPM) {
        this.targetWheelRPM = Math1816.coerceValue(maxWheelRPM, -maxWheelRPM, targetWheelRPM);
        update();
    }
    
    public void setPower(double power) {
        setSpeed(power * maxWheelRPM);
        update();
    }
    
    public double getTargetSpeed() {
        return targetWheelRPM;
    }
    
    public double getCurrentSpeed() {
        return encoder.getRate() * GEAR_RATIO;   
    }
    
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
