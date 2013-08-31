package com.edinarobotics.utils.gamepad;

/**
 * Used to filter the joystick values of a Gamepad.
 */
public interface GamepadFilter {
    public GamepadResult filter(GamepadResult toFilter);
}
