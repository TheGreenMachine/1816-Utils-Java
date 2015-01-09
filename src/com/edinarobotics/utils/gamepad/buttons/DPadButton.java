package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.Gamepad;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Represents a button on the gamepad D-Pad.
 * 
 * This class can be used as a regular command-based programming button and
 * have Command object bound to it.
 */
public class DPadButton extends Button{
    private final Gamepad gamepad;
    private final DPadButtonType buttonType;
    
    /**
     * Constructs a new DPadButton attached to the given gamepad, monitoring
     * the given direction on the D-Pad.
     * @param gamepad The Gamepad object to which this button is bound.
     * @param buttonType The direction on the D-Pad that will be monitored
     * by this DPadButton.
     */
    public DPadButton(Gamepad gamepad, DPadButtonType buttonType){
        this.gamepad = gamepad;
        this.buttonType = buttonType;
    }

    /**
     * Returns the current state of this DPadButton.
     * @return The current state of this DPadButton.
     */
    public boolean get() {
        if(buttonType.equals(DPadButtonType.UP)){
            return gamepad.getDPadY() == 1;
        }
        else if(buttonType.equals(DPadButtonType.DOWN)){
            return gamepad.getDPadY() == -1;
        }
        else if(buttonType.equals(DPadButtonType.LEFT)){
            return gamepad.getDPadX() == -1;
        }
        else if(buttonType.equals(DPadButtonType.RIGHT)){
            return gamepad.getDPadX() == 1;
        }
        return false;
    }
    
    /**
     * Indicates the type of this DPadButton.
     * @return The direction on the D-Pad that is monitored by this button.
     */
    public DPadButtonType getButtonType(){
        return this.buttonType;
    }
    
    /**
     * Represents a direction on the D-Pad that can be monitored by a
     * DPadButton.
     */
    public static final class DPadButtonType {
        private final byte value;
        private final String name;
        
        /**
         * Represents the up direction on the D-Pad.
         */
        public static final DPadButtonType UP = new DPadButtonType((byte)1, "up");
        
        /**
         * Represents the down direction on the D-Pad.
         */
        public static final DPadButtonType DOWN = new DPadButtonType((byte)2, "down");
        
        /**
         * Represents the left direction on the D-Pad.
         */
        public static final DPadButtonType LEFT = new DPadButtonType((byte)3, "left");
        
        /**
         * Represents the right direction on the D-Pad.
         */
        public static final DPadButtonType RIGHT = new DPadButtonType((byte)4, "right");
        
        private DPadButtonType(byte value, String name){
            this.value = value;
            this.name = name;
        }
        
        private byte getValue(){
            return value;
        }
        
        /**
         * Gives the name of this DPadButtonType for debugging purposes.
         * @return The name of this DPadButtonType in lower case.
         */
        public String getName(){
            return name.toLowerCase();
        }
        
        /**
         * Indicates whether this DPadButtonType is equal to another object.
         * This object is equal to another if the other object is also
         * a DPadButtonType and if it represents the same direction
         * on the D-Pad as this object.
         * @param other The object to be compared against this one for equality.
         * @return {@code true} if the objects are equal as described above,
         * {@code false} otherwise.
         */
        public boolean equals(Object other){
            if(other instanceof DPadButtonType){
                return ((DPadButtonType)other).getValue() == this.getValue();
            }
            return false;
        }
        
        /**
         * Provides a human-readable String representation of this object.
         * @return A human-readable String representation of this object.
         */
        public String toString(){
            return "D-Pad "+getName();
        }
    }
}
