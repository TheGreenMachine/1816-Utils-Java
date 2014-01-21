package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.Gamepad;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 */
public class DPadButton extends Button{
    private final Gamepad gamepad;
    private final DPadButtonType buttonType;
    private static final double THRESHOLD = 0.9;
    
    public DPadButton(Gamepad gamepad,DPadButtonType buttonType){
        this.gamepad = gamepad;
        this.buttonType = buttonType;
    }

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
    
    public DPadButtonType getButtonType(){
        return this.buttonType;
    }
    
    public static final class DPadButtonType {
        private final byte value;
        private final String name;
        
        public static final DPadButtonType UP = new DPadButtonType((byte)1, "up");
        public static final DPadButtonType DOWN = new DPadButtonType((byte)2, "down");
        public static final DPadButtonType LEFT = new DPadButtonType((byte)3, "left");
        public static final DPadButtonType RIGHT = new DPadButtonType((byte)4, "right");
        
        private DPadButtonType(byte value, String name){
            this.value = value;
            this.name = name;
        }
        
        private byte getValue(){
            return value;
        }
        
        public String getName(){
            return name.toLowerCase();
        }
        
        public boolean equals(Object other){
            if(other instanceof DPadButtonType){
                return ((DPadButtonType)other).getValue() == this.getValue();
            }
            return false;
        }
        
        public String toString(){
            return "D-Pad "+getName();
        }
    }
}
