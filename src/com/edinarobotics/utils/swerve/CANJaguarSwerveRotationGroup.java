package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.math.Math1816;
import com.edinarobotics.utils.pid.PIDConstant;
import com.edinarobotics.utils.pid.ReadablePIDOutput;
import com.edinarobotics.utils.sensors.AnalogAbsoluteEncoder;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 */
public class CANJaguarSwerveRotationGroup extends SwerveRotationGroup{
    private PIDConstant pidDefaults;
    private PIDController pidController;
    private AnalogAbsoluteEncoder encoder;
    private ReadablePIDOutput pidOut;
    private AngleRestriction angleRestriction;
    private CANJaguar rotationJaguar;
    private double targetAngle = 0.0;
    private boolean lastSetWasRemote = false;
    private double ROTATION_SPEED_FACTOR = 0.2;
    
    public CANJaguarSwerveRotationGroup(String name, SwerveWheel[] wheels, AngleRestriction angleRestriction, CANJaguar rotationJaguar, AnalogAbsoluteEncoder encoder, PIDConstant pidConstants){
        super(name, wheels, angleRestriction);
        this.rotationJaguar = rotationJaguar;
        this.pidDefaults = pidConstants;
        this.encoder = encoder;
        this.pidOut = new ReadablePIDOutput();
        this.pidController = new PIDController(pidDefaults.getP(), pidDefaults.getI(),
                pidDefaults.getD(), pidDefaults.getF(), encoder, pidOut);
        try{
            this.rotationJaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
        } catch(CANTimeoutException ex){
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Could not communicate with CANJaguar during construction. Timeout.");
        }
        pidController.enable();
    }

    public void setAngle(double angleRadians) {
        targetAngle = angleRestriction.getClosestAngle(angleRadians);
        update();
    }

    public double getTargetAngle(){
        return targetAngle;
    }
    
    public double getMeasuredAngle(){
        return encoder.getAngleRadians();
    }
    
    public AnalogAbsoluteEncoder getEncoder(){
        return encoder;
    }

    public void update() {
        pidController.setPID(pidConfig.getP(pidDefaults.getP()),
                        pidConfig.getI(pidDefaults.getI()),
                        pidConfig.getD(pidDefaults.getD()),
                        pidConfig.getF(pidDefaults.getF()));
        try{
            if(!pidConfig.shouldOverrideRawControl()){
                //NO RAW REMOTE CONTROL
                if(lastSetWasRemote){
                    pidController.reset();
                    pidController.enable();
                }
                lastSetWasRemote = false;
                pidConfig.setSetpoint(getTargetAngle());
                pidController.setSetpoint(pidConfig.getSetpoint());
                rotationJaguar.setX(pidOut.getValue()*ROTATION_SPEED_FACTOR);
            }
            else{
                //YES RAW REMOTE CONTROL
                lastSetWasRemote = true;
                pidController.disable();
                rotationJaguar.setX(Math1816.coerceValue(1.0, -1.0, pidConfig.getRemoteRawControlValue())*ROTATION_SPEED_FACTOR);
            }
            if(!angleRestriction.isValidAngle(getMeasuredAngle())){
                lastSetWasRemote = true;
                pidController.disable();
                rotationJaguar.setX(0.0);
            }
        } catch (CANTimeoutException ex){
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Could not communicate with CANJaguar during update. Timeout.");
        }
    }
}
