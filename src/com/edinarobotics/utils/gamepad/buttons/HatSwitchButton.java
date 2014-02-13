package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Represents a button on the joystick hat switch.
 * 
 * This class can be used as a regular command-based programming button and
 * have Command object bound to it.
 */
public final class HatSwitchButton extends Button {
    private final Joystick joystick;
    private final HatSwitchButtonType buttonType;
    
    /**
     * Constructs a new HatSwitchButton attached to the given joystick, monitoring
     * the given direction on the hat switch.
     * @param joystick The Joystick object to which this button is bound.
     * @param buttonType The direction on the hat switch that will be monitored
     * by this HatSwitchButton.
     */
    public HatSwitchButton(Joystick joystick, HatSwitchButtonType buttonType){
        this.joystick = joystick;
        this.buttonType = buttonType;
    }

    /**
     * Returns the current state of this HatSwitchButton.
     * @return The current state of this HatSwitchButton.
     */
    public boolean get() {
        if(buttonType.equals(HatSwitchButtonType.UP)){
            return joystick.getHatSwitchY() == 1;
        }
        else if(buttonType.equals(HatSwitchButtonType.DOWN)){
            return joystick.getHatSwitchY() == -1;
        }
        else if(buttonType.equals(HatSwitchButtonType.LEFT)){
            return joystick.getHatSwitchX() == -1;
        }
        else if(buttonType.equals(HatSwitchButtonType.RIGHT)){
            return joystick.getHatSwitchX() == 1;
        }
        return false;
    }
    
    /**
     * Indicates the type of this HatSwitchButton.
     * @return The direction on the hat switch that is monitored by this button.
     */
    public HatSwitchButtonType getButtonType(){
        return buttonType;
    }
    
    /**
     * Represents a direction on the hat switch that can be monitored by a
     * HatSwitchButton.
     */
    public static final class HatSwitchButtonType{
        private final byte value;
        private final String name;
        
        /**
         * Represents the up direction on the hat switch.
         */
        public static final HatSwitchButtonType UP = new HatSwitchButtonType((byte)0, "up");
        
        /**
         * Represents the down direction on the hat switch.
         */
        public static final HatSwitchButtonType DOWN = new HatSwitchButtonType((byte)1, "down");
        
        /**
         * Represents the left direction on the hat switch.
         */
        public static final HatSwitchButtonType LEFT = new HatSwitchButtonType((byte)2, "left");
        
        /**
         * Represents the right direction on the hat switch.
         */
        public static final HatSwitchButtonType RIGHT = new HatSwitchButtonType((byte)3, "right");
        
        private HatSwitchButtonType(byte value, String name){
            this.value = value;
            this.name = name;
        }
        
        private byte getValue(){
            return value;
        }
        
        /**
         * Gives the name of this HatSwitchButtonType for debugging purposes.
         * @return The name of this HatSwitchButtonType in lower case.
         */
        public String getName(){
            return name.toLowerCase();
        }
        
        /**
         * Indicates whether this HatSwitchButtonType is equal to another
         * object.
         * This object is equal to another if the other object is also
         * a HatSwitchButtonType and if it represents the same direction
         * on the hat switch as this object.
         * @param other The object to be compared against this one for equality.
         * @return {@code true} if the objects are equal as described above,
         * {@code false} otherwise.
         */
        public boolean equals(Object other){
            if(other instanceof HatSwitchButtonType){
                return ((HatSwitchButtonType)other).getValue() == this.getValue();
            }
            return false;
        }
        
        /**
         * Provides a human-readable String representation of this object.
         * @return A human-readable String representation of this object.
         */
        public String toString(){
            return "Hat Switch "+getName();
        }
    }
}
