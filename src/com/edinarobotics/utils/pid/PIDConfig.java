package com.edinarobotics.utils.pid;

/**
 * This class is used to exchange PID configuration data with PIDTuningManager.
 * It provides methods to get the latest P, I and D values set by the dashboard
 * and to provide value and setpoint feedback to the dashboard.
 */
public class PIDConfig {
    private String name;
    boolean overrideDefault;
    double p,i,d, f, value, setpoint;
    
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
     * Returns the last value set by {@link #setSetpoint(double)}.
     * @return The last value set by setSetpoint(double).
     */
    public double getSetpoint(){
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
     * Resets the P, I and D values of this PIDConfig to their default values.
     * This method will undo any tuning performed by the dashboard.
     */
    public void reset(){
        overrideDefault = false;
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
        return "<PIDConfig "+p+":"+i+":"+d+":"+overrideDefault+">";
    }
}
