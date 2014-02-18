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
    private SpeedController speedController;
    private double maxWheelRPM;
    private double targetWheelRPM;
    private boolean reversed;
    
    private final double GEAR_RATIO;
    
    public SpeedControlledWheel(String name, SpeedController speedController, double maxWheelRPM, 
            PIDConstant pidConstant, Encoder encoder, double gearRatio, double rotationsPerPulse, boolean reversed) {
        super(name, speedController, reversed);
        this.speedController = speedController;
        this.defaultPID = pidConstant;
        this.GEAR_RATIO = gearRatio;
        this.encoder = encoder;
        this.maxWheelRPM = maxWheelRPM;
        encoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate);
        encoder.setDistancePerPulse(rotationsPerPulse);
        pidConfig = PIDTuningManager.getInstance().getPIDConfig(name);
        this.pidController = new PIDController(defaultPID.getP(), defaultPID.getI(),
                                defaultPID.getD(), defaultPID.getF(), encoder, speedController);
        this.pidController.setOutputRange(-1.0, 1.0);
        pidController.enable();
        this.reversed = reversed;
    }
    
    public void setTargetWheelRPM(double targetWheelRPM) {
        if(reversed) {
            targetWheelRPM *= -1;
        }
        this.targetWheelRPM = Math1816.coerceValue(maxWheelRPM, -maxWheelRPM, targetWheelRPM);
        update();
    }
    
    public void setPower(double power) {
        if(reversed) {
            power *= -1;
        }
        setTargetWheelRPM(power * maxWheelRPM);
        update();
    }
    
    public double getTargetWheelRPM() {
        return targetWheelRPM;
    }
    
    public double getCurrentWheelRPM() {
        return encoder.getRate() * GEAR_RATIO;   
    }
    
    public PIDConfig getPIDConfig() {
        return pidConfig;
    }
    
    public void update() {
        pidConfig.setValue(encoder.pidGet() * GEAR_RATIO);
        
        if(pidConfig.shouldOverrideRawControl()) {
            pidController.disable();
            speedController.set(pidConfig.getRemoteRawControlValue());
        } else {
            pidController.setPID(pidConfig.getP(defaultPID.getP()), 
                    pidConfig.getI(defaultPID.getI()), 
                    pidConfig.getD(defaultPID.getD()),
                    pidConfig.getF(defaultPID.getF()));
            double encoderRPM = (1.0/GEAR_RATIO) * targetWheelRPM;
            pidConfig.setSetpoint(encoderRPM);
            pidController.setSetpoint(pidConfig.getSetpoint());
        }
    }
    
    
}
