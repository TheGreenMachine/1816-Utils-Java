package com.edinarobotics.utils.pid;

/**
 * Represents all possible remote control modes that can be specified by the
 * remote tuning system.
 */
public final class PIDRemoteControlMode {
    private final byte value;
    private final String name;
    
    /**
     * Indicates that remote control is currently disabled. The
     * remote tuning system is not remotely controlling the PID systems and
     * normal robot controls should be active.
     */
    public static final PIDRemoteControlMode NONE = new PIDRemoteControlMode((byte)0, "none");
    
    /**
     * Indicates that the remote tuning system has requested control
     * over the PID setpoint of the system. The setpoint specified by
     * the remote system should be given to the PID controllers for remote
     * control purposes.
     */
    public static final PIDRemoteControlMode SETPOINT = new PIDRemoteControlMode((byte)1, "setpoint");
    
    /**
     * Indicates that the remote tuning system has requested control over
     * the raw output values sent to the actually controllers (for example
     * the PWM value sent to Jaguars). The raw control value specified by the
     * remote system should be sent to the controller and the robot-side
     * PID controller should be disabled.
     */
    public static final PIDRemoteControlMode VALUE = new PIDRemoteControlMode((byte)2, "value");
    
    private PIDRemoteControlMode(byte value, String name){
        this.value = value;
        this.name = name;
    }

    /**
     * Returns a hash code value for this PIDRemoteControlMode as required by
     * {@link Object#hashCode()}.
     * @return A hash code value for this PIDRemoteControlMode.
     */
    public int hashCode() {
        return new Byte(value).hashCode();
    }
    
    /**
     * Tests another object against this one for equality.
     * 
     * An object is equal to this PIDRemoteControlMode if it is also a
     * PIDRemoteControlMode object and if it has the same internal byte value.
     * @param other The object to be tested against this PIDRemoteControlValue
     * for equality.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof PIDRemoteControlMode){
            return ((PIDRemoteControlMode) other).getValue() == this.getValue();
        }
        return false;
    }
    
    /**
     * Retrieves the human-readable name of this PIDRemoteControlMode.
     * 
     * The name is returned as a lower-case String.
     * @return The String human-readable name of this PIDRemoteControlMode.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns a String representation of this PIDRemoteControlMode object.
     * 
     * The returned String is equivalent to the value returned by
     * the {@link #getName()} method.
     * @return A String representation of this PIDRemoteControlMode object.
     */
    public String toString(){
        return getName();
    }
    
    /**
     * Gives access to the private byte value used to uniquely identify a
     * PIDRemoteControlMode value.
     * @return The internal byte value used to identify this
     * PIDRemoteControlMode value.
     */
    private byte getValue(){
        return value;
    }
}
