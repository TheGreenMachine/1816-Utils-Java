package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.Gamepad;

import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button{

	private final Gamepad gamepad;
	private final DPadButtonType buttonType;
	
	public DPadButton(Gamepad gamepad, DPadButtonType buttonType){
		this.gamepad = gamepad;
		this.buttonType = buttonType;
	}
	
	@Override
	public boolean get() {

		if(buttonType == DPadButtonType.UP){
            return gamepad.getDPadY() == 1;
        }
        else if(buttonType == DPadButtonType.DOWN){
            return gamepad.getDPadY() == -1;
        }
        else if(buttonType == DPadButtonType.LEFT){
            return gamepad.getDPadX() == -1;
        }
        else if(buttonType == DPadButtonType.RIGHT){
            return gamepad.getDPadX() == 1;
        }
		
		return false;
	}
	
	public enum DPadButtonType {
		UP, DOWN, LEFT, RIGHT;
	}

}
