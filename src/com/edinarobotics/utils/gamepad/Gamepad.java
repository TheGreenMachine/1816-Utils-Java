package com.edinarobotics.utils.gamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Gamepad {
    private Joystick joystick;
    
    private final double DPAD_THRESHOLD = 0.5;
    
    public final Button LEFT_BUMPER;
    public final Button RIGHT_BUMPER;
    public final Button LEFT_TRIGGER;
    public final Button RIGHT_TRIGGER;
    public final Button DIAMOND_LEFT;
    public final Button DIAMOND_DOWN;
    public final Button DIAMOND_RIGHT;
    public final Button DIAMOND_UP;
    public final Button MIDDLE_LEFT;
    public final Button MIDDLE_RIGHT;
    public final Button LEFT_JOYSTICK_BUTTON;
    public final Button RIGHT_JOYSTICK_BUTTON;
    public final Button DPAD_UP;
    public final Button DPAD_DOWN;
    public final Button DPAD_RIGHT;
    public final Button DPAD_LEFT;
    
    public Gamepad(int port) {
        joystick = new Joystick(port);
        LEFT_BUMPER = new JoystickButton(joystick, 5);
        LEFT_TRIGGER = new JoystickButton(joystick, 7);
        RIGHT_BUMPER = new JoystickButton(joystick, 6);
        RIGHT_TRIGGER = new JoystickButton(joystick, 8);
        DIAMOND_LEFT = new JoystickButton(joystick, 1);
        DIAMOND_DOWN = new JoystickButton(joystick, 2);
        DIAMOND_RIGHT = new JoystickButton(joystick, 3);
        DIAMOND_UP = new JoystickButton(joystick, 4);
        MIDDLE_LEFT = new JoystickButton(joystick, 9);
        MIDDLE_RIGHT = new JoystickButton(joystick, 10);
        LEFT_JOYSTICK_BUTTON = new JoystickButton(joystick, 11);
        RIGHT_JOYSTICK_BUTTON = new JoystickButton(joystick, 12);
        
        DPAD_UP = new DPadButton(this, DPadButton.DPAD_UP);
        DPAD_DOWN = new DPadButton(this, DPadButton.DPAD_DOWN);
        DPAD_LEFT = new DPadButton(this, DPadButton.DPAD_LEFT);
        DPAD_RIGHT = new DPadButton(this, DPadButton.DPAD_RIGHT);
    }
    
    public double getLeftX() {
        return joystick.getRawAxis(1);
    }
    
    public double getLeftY() {
        return -joystick.getRawAxis(2);
    }
    
    public double getRightX() {
        return joystick.getRawAxis(3);
    }
    
    public double getRightY() {
        return -joystick.getRawAxis(4);
    }
    
    public byte getDPadX() {
        return dPadToByte(joystick.getRawAxis(5));
    }
    
    public byte getDPadY() {
        return dPadToByte(-joystick.getRawAxis(6));
    }
    
    public GamepadResult getJoysticks(){
        return new GamepadResult(getLeftX(),getLeftY(),getRightX(),getRightY());
    }
    
    protected byte dPadToByte(double value) {
        if(value >= DPAD_THRESHOLD){
            return 1;
        }
        else if(value <= -DPAD_THRESHOLD){
            return -1;
        }
        return 0;
    }
    
}
