package com.edinarobotics.utils.pid;

/**
 * This class is used to exchange PID configuration data with PIDTuningManager.
 * It provides methods to get the latest P, I and D values set by the dashboard
 * and to provide value and setpoint feedback to the dashboard.
 * <br/><br/>
 * To use this class, send the PIDF values given by 
 * {@link #getP(double)}, {@link #getI(double)}, {@link #getD(double)}, and
 * {@link #getF(double)} to your PID controller to update its gains every
 * iteration of your control loop.<br/>
 * Each control loop, pass your desired setpoint value to the method
 * {@link #setSetpoint(double)}, and then pass the result of
 * {@link #getSetpoint()} to your PID controller as its setpoint. This allows
 * the remote PID tuning system to remote control the setpoint of the system for
 * easier tuning.<br/>
 * Finally, if direct remote control of the raw control values (i.e. raw PWM
 * values) is desired (for example to enable remote auto-tuning), if the result
 * of {@link #shouldOverrideRawControl()} is {@code true}, disable your
 * PID controller and use the result of {@link #getRemoteRawControlValue()} as
 * the direct raw control value to your controller.
 */
public class PIDConfig {
    private String name;
    boolean overrideDefault;
    PIDRemoteControlMode remoteControlMode;
    double p, i, d, f, value, setpoint, remoteSetpoint, remoteControlValue;
    
    protected PIDConfig(String name){
        this.name = name;
        overrideDefault = false;
    }
    
    /**
     * Returns the name of this PIDConfig instance.
     * @return The String name of this PIDConfig instance.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the P value that should be used by the relevant PID controller.
     * This method will return the value set by the dashboard if such a value
     * exists or the default value.
     * @param defaultP The default value of P that should be used if the
     * tuning process has not set a new value.
     * @return The value of P that should be used by the relevant PID controller.
     */
    public double getP(double defaultP){
        if(overrideDefault){
            return p;
        }
        return defaultP;
    }
    
    /**
     * Returns the I value that should be used by the relevant PID controller.
     * This method will return the value set by the dashboard if such a value
     * exists or the default value.
     * @param defaultI The default value of I that should be used if the
     * tuning process has not set a new value.
     * @return The value of I that should be used by the relevant PID controller.
     */
    public double getI(double defaultI){
        if(overrideDefault){
            return i;
        }
        return defaultI;
    }
    
    /**
     * Returns the D value that should be used by the relevant PID controller.
     * This method will return the value set by the dashboard if such a value
     * exists or the default value.
     * @param defaultD The default value of D that should be used if the
     * tuning process has not set a new value.
     * @return The value of D that should be used by the relevant PID controller.
     */
    public double getD(double defaultD){
        if(overrideDefault){
            return d;
        }
        return defaultD;
    }
    
    /**
     * Returns the feed forward value that should be used by the relevant PID controller.
     * This method will return the value set by the dashboard if such a value
     * exists or the default value.
     * @param defaultFeedForward The default value of feed forward that should be used if the
     * tuning process has not set a new value.
     * @return The value of D that should be used by the relevant PID controller.
     */
    public double getF(double f){
        if(overrideDefault){
            return this.f;
        }
        return f;
    }
    
    /**
     * Sets the value that should be sent as feedback to the dashboard
     * during the PID tuning process. This value should be the value
     * that is backing the PID controller. For example, it could be an
     * encoder or potentiometer reading.
     * @param value The value that should be sent as feedback to the dashboard.
     */
    public void setValue(double value){
        this.value = value;
    }
    
    /**
     * Sets the setpoint value that should be sent as feedback to the dashboard
     * during the PID tuning process. This value should be the current setpoint
     * of the PID controller. For example, it could be a target encoder
     * or potentiometer reading.
     * @param setpoint The setpoint that should be sent to the dashboard.
     */
    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
    }
    
    /**
     * Returns the last value set by {@link #setValue(double}.
     * @return The last value set by setValue(double).
     */
    public double getValue(){
        return value;
    }
    
    /**
     * Returns the last value set by {@link #setSetpoint(double)} or
     * the remote setpoint if the remote setpoint should be used. This value
     * should be used by the PID controller as its target setpoint.
     * @return The last value set by setSetpoint(double) or the remote
     * target setpoint.
     */
    public double getSetpoint(){
        if(getRemoteControlMode().equals(PIDRemoteControlMode.SETPOINT)){
            return this.remoteSetpoint;
        }
        return setpoint;
    }
    
    /**
     * Returns the last value set by {@link #setSetpoint(double)}, ignoring
     * any remote setpoint. This value should only be used if remote control
     * of the setpoint is not allowable.
     * @return The last value set by setSetpoint(double).
     */
    public double getLocalSetpoint(){
        return setpoint;
    }
    
    /**
     * This method is used internally by PIDTuningManager to override
     * the default P, I and D values used by a PID controller. This method
     * will replace any default values passed to the getP, getI and getD methods.
     * @param p The new value of P for the PID controller.
     * @param i The new value of I for the PID controller.
     * @param d The new value of D for the PID controller.
     * @param f The new value of the feed forward constant for the PID controller.
     */
    protected void setPID(double p, double i, double d, double f){
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        overrideDefault = true;
    }
    
    /**
     * This method is used internally by PIDTuningManager to specify the remote
     * PID tuning system's requested setpoint. It is used for remote control
     * of the PID system.
     * @param setpoint The setpoint specified by the remote tuning bench
     * for remote control purposes.
     */
    protected void setRemoteSetpoint(double setpoint){
        this.remoteSetpoint = setpoint;
    }
    
    /**
     * Resets the P, I, D, F and remote control values to their default states.
     * This method will undo any tuning performed by the remote tuning system.
     */
    public void reset(){
        overrideDefault = false;
        remoteControlMode = PIDRemoteControlMode.NONE;
    }
    
    /**
     * Indicates whether some other object is equal to this one.
     * Another object is equal to this PIDConfig if it is also a PIDConfig
     * and if its {@link #getName()} method returns the same value
     * as this objects getName() method.
     * @param other The object to be compared for equality to this object.
     * @return {@code true} if the {@code other} is equal to this object as
     * described above, {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof PIDConfig){
            return getName().equals(((PIDConfig)other).getName());
        }
        return false;
    }
    
    /**
     * Returns a hash code value for this object.
     * This value is equivalent to {@code getName().hashCode()}.
     * @return A hash code value for this object.
     */
    public int hashCode(){
        return getName().hashCode();
    }
    
    /**
     * Returns a String representation of this object.
     * @return A String representation of this object.
     */
    public String toString(){
        return "<PIDConfig "+p+":"+i+":"+d+":"+f+":"+overrideDefault+">";
    }
    
    /**
     * Returns the remote control mode specified by the remote tuning system for
     * this PID system. This mode is used to set whether remote control
     * specified values are used for the PID system.
     * @return The remote control mode specified by the remote tuning system.
     */
    public PIDRemoteControlMode getRemoteControlMode(){
        return this.remoteControlMode;
    }
    
    public boolean isRemoteControlEnabled(){
        return !getRemoteControlMode().equals(PIDRemoteControlMode.NONE);
    }
    
    protected void setRemoteControlMode(PIDRemoteControlMode remoteMode){
        this.remoteControlMode = remoteMode;
    }
    
    public boolean shouldOverrideRawControl(){
        return getRemoteControlMode().equals(PIDRemoteControlMode.VALUE);
    }
    
    public double getRemoteRawControlValue(){
        return this.remoteControlValue;
    }
    
    protected void setRemoteRawControlValue(double value){
        remoteControlValue = value;
    }
}
