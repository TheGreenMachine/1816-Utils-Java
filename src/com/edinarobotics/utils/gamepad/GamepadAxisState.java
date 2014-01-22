package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.math.Vector2;

/**
 * This class represents the state of all joystick axes of a Gamepad.
 */
public class GamepadAxisState {
    private Vector2 left, right;
    
    /**
     * Constructs a new GamepadAxisState storing the given joystick states.
     * @param left The state of the gamepad's left joystick as a Vector2.
     * @param right The state of the gamepad's right joystick as a Vector2.
     */
    public GamepadAxisState(Vector2 left, Vector2 right){
        this.left = left;
        this.right = right;
    }
    
    /**
     * Returns the Vector2 representing the state of the gamepad's left
     * joystick.
     * @return The Vector2 representing the state of the gamepad's left
     * joystick.
     */
    public Vector2 getLeftJoystick(){
        return left;
    }
    
    /**
     * Returns the Vector2 representing the state of the gamepad's right
     * joystick.
     * @return The Vector2 representing the state of the gamepad's right
     * joystick.
     */
    public Vector2 getRightJoystick(){
        return right;
    }
}
