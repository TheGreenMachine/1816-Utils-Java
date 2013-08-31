package com.edinarobotics.utils.gamepad;

import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {
    private Gamepad gamepad;
    private byte dPadAxis; //determines which button on the d-pad this button object represents
    public static final byte DPAD_UP = 1;
    public static final byte DPAD_DOWN = 2;
    public static final byte DPAD_LEFT = 3;
    public static final byte DPAD_RIGHT = 4;
    
    protected DPadButton(Gamepad gamepad, byte dPadAxis) {
        this.gamepad = gamepad;
        this.dPadAxis = dPadAxis;
    }
    
    public boolean get() {
        if(dPadAxis == DPAD_UP) {
            return gamepad.getDPadY() == 1;
        }
        if(dPadAxis == DPAD_DOWN) {
            return gamepad.getDPadY() == -1;
        }
        if(dPadAxis == DPAD_RIGHT) {
            return gamepad.getDPadX() == 1;
        }
        if(dPadAxis == DPAD_LEFT) {
            return gamepad.getDPadX() == -1;
        }
        return false;
    }
}
