/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;

/**
 *
 */
public abstract class SwerveRotationGroup {
    protected final PIDConfig pidConfig;
    protected final Logger logger;
    protected AngleRestriction angleRestriction;
    protected boolean shouldReverseSpeed;
    private final String name;
    private SwerveWheel[] wheels;
    
    public SwerveRotationGroup(String name, SwerveWheel[] wheels, AngleRestriction angleRestriction){
        this.name = name;
        pidConfig = PIDTuningManager.getInstance().getPIDConfig(name);
        this.logger = LogSystem.getLogger("swerve.wheel."+name.toLowerCase().replace(' ', '_'));
        this.wheels = new SwerveWheel[wheels.length];
        this.shouldReverseSpeed = false;
        System.arraycopy(wheels, 0, this.wheels, 0, wheels.length);
    }
    
    public String getName(){
        return name;
    }
    
    public abstract void setAngle(double angleRadians);
    
    public abstract double getTargetAngle();
    
    public abstract double getMeasuredAngle();
    
    public SwerveWheel[] getWheels(){
        return wheels;
    }
    
    public AngleRestriction getAngleRestriction(){
        return this.angleRestriction;
    }
    
    public boolean getShouldReverseSpeed(){
        return this.shouldReverseSpeed;
    }
    
    public void setShouldReverseSpeed(boolean shouldReverseSpeed){
        this.shouldReverseSpeed = shouldReverseSpeed;
    }
    
    public abstract void update();
}
