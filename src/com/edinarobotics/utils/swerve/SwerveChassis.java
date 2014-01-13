/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.math.Vector2;

/**
 *
 */
public class SwerveChassis {
    private SwerveRotationGroup[] wheels;
    private SwerveStrategy strategy;
    private double rotationRadSec;
    private Vector2 direction;
    
    public SwerveChassis(SwerveRotationGroup[] wheels, SwerveStrategy strategy){
        this.wheels = new SwerveRotationGroup[wheels.length];
        System.arraycopy(wheels, 0, this.wheels, 0, wheels.length);
        this.strategy = strategy;
    }
    
    public void setSwerve(Vector2 direction, double rotationRadSec){
        this.direction = direction;
        this.rotationRadSec = rotationRadSec;
    }
    
    public Vector2 getDirection(){
        return direction;
    }
    
    public double getRotation(){
        return rotationRadSec;
    }
    
    public void update(){
        strategy.doSwerve(wheels, direction, rotationRadSec);
    }
}
