/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.math.Math1816;
import com.edinarobotics.utils.math.Vector2;
import com.edinarobotics.utils.pid.PIDConstant;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 */
public class CANJaguarSwerveWheel extends SwerveWheel {
    private final CANJaguar jaguar;
    private double velocity, wheelCircumference;
    private final int encoderCountsPerRev;
    private final PIDConstant pidDefaults;
    
    public CANJaguarSwerveWheel(String name, Vector2 position, CANJaguar jaguar, int encoderCountsPerRev, double wheelCircumference, PIDConstant pidConstants){
        super(name, position);
        this.jaguar = jaguar;
        this.velocity = 0;
        this.encoderCountsPerRev = encoderCountsPerRev;
        this.wheelCircumference = wheelCircumference;
        this.pidDefaults = pidConstants;
        try{
            jaguar.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            jaguar.configEncoderCodesPerRev(encoderCountsPerRev);
            jaguar.changeControlMode(CANJaguar.ControlMode.kSpeed);
        } catch(CANTimeoutException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to communicate with CANJaguar during construction. Timeout.");
        }
    }
    
    public void setVelocity(double velocity){
        this.velocity = velocity;
        update();
    }
    
    public double getTargetVelocity(){
        return velocity; //Convert to m/s
    }
    
    public double getMeasuredVelocity(){
        try {
            return (this.jaguar.getX() / 60.0) * wheelCircumference; //in m/s
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Unable to communicate with CANJaguar. Timeout.");
            return Double.NaN;
        }
    }
    
    private double makeValidControlValue(double value){
        return Math1816.coerceValue(1.0, -1.0, value);
    }
    
    public void update(){
        try{
            jaguar.setPID(pidConfig.getP(pidDefaults.getP()),
                        pidConfig.getI(pidDefaults.getI()),
                        pidConfig.getD(pidDefaults.getD()));
            if(!pidConfig.shouldOverrideRawControl()){
                //No raw remote
                jaguar.changeControlMode(CANJaguar.ControlMode.kSpeed);
                pidConfig.setSetpoint((velocity / this.wheelCircumference) * 60.0); //Convert to RPM
                jaguar.setX(pidConfig.getSetpoint());
            }
            else{
                //Yes raw remote
                jaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
                jaguar.setX(makeValidControlValue(pidConfig.getRemoteRawControlValue()));
            }
        } catch (CANTimeoutException ex) {
            logger.log(Level.SEVERE, "Unable to communicate with CANJaguar. Timeout.");
            ex.printStackTrace();
        }
    }
}
