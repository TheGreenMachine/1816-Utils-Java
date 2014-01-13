package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import com.edinarobotics.utils.math.Vector2;
import com.edinarobotics.utils.pid.PIDConfig;
import com.edinarobotics.utils.pid.PIDTuningManager;

/**
 *
 */
public abstract class SwerveWheel {
    protected final PIDConfig pidConfig;
    protected final Logger logger;
    private final String name;
    private final Vector2 position;
    
    public SwerveWheel(String name, Vector2 position){
        this.name = name;
        this.position = position;
        pidConfig = PIDTuningManager.getInstance().getPIDConfig(name);
        this.logger = LogSystem.getLogger("swerve.wheel."+name.toLowerCase().replace(' ', '_'));
    }
    
    public String getName(){
        return name;
    }
    
    public Vector2 getPosition(){
        return position;
    }
    
    public abstract void setVelocity(double velocity);
    
    public abstract double getTargetVelocity();
    
    public abstract double getMeasuredVelocity();
    
    public abstract void update();
}
