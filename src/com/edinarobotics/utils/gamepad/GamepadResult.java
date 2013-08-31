package com.edinarobotics.utils.gamepad;

import com.sun.squawk.util.MathUtils;

/**
 * Represents the state of both of a Gamepad's joysticks.
 * Can be used to filter these joysticks (deadzoning, scaling, etc.).
 */
public class GamepadResult {
    private double lx;
    private double ly;
    private double rx;
    private double ry;
    
    /**
     * Creates a GamepadResult from the cartesian coordinates of two joysticks.
     * @param lx the left x-value.
     * @param ly the left y-value.
     * @param rx the right x-value.
     * @param ry  the right y-value.
     */
    public GamepadResult(double lx,double ly,double rx,double ry){
        this.lx = lx;
        this.ly = ly;
        this.rx = rx;
        this.ry = ry;
        
    }
    
    public double getLeftX(){
        return lx;
    }
    
    public double getLeftY(){
        return ly;
    }
    
    public double getRightX(){
        return rx;
    }
    
    public double getRightY(){
        return ry;
    }
    
    public double getLeftMagnitude(){
        return Math.sqrt(MathUtils.pow(getLeftX(), 2)+MathUtils.pow(getLeftY(), 2));
    }
    
    public double getRightMagnitude(){
        return Math.sqrt(MathUtils.pow(getRightX(), 2)+MathUtils.pow(getRightY(), 2));
    }
    
    /**
     * Returns the direction of the left joystick on the gamepad. The angle
     * is clockwise from straight up (along the y-axis) and is suitable
     * to pass to
     * {@link edu.wpi.first.wpilibj.RobotDrive#mecanumDrive_Polar(double, double, double)}.
     * @return The direction angle in degrees of the left joystick.
     */
    public double getLeftDirection(){
        return Math.toDegrees(MathUtils.atan2(getLeftX(), getLeftY()));
    }
    
    /**
     * Returns the direction of the right joystick on the gamepad. The angle
     * is clockwise from straight up (along the y-axis) and is suitable
     * to pass to
     * {@link edu.wpi.first.wpilibj.RobotDrive#mecanumDrive_Polar(double, double, double)}.
     * @return The direction angle in degrees of the right joystick.
     */
    public double getRightDirection(){
        return Math.toDegrees(MathUtils.atan2(getRightX(), getRightY()));
    }
}
