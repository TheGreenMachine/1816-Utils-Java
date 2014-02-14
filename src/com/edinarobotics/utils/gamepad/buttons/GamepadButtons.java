package com.edinarobotics.utils.gamepad.buttons;

/**
 * GamepadButtons represents all the possible button types on a gamepad.
 * For information on the names given to gamepad buttons, see
 * {@link com.edinarobotics.utils.gamepad.Gamepad Gamepad}.
 */
public final class GamepadButtons {
    private final byte value;
    private final String name;
    
    public static final GamepadButtons LEFT_BUMPER = new GamepadButtons((byte)0, "left bumper");
    public static final GamepadButtons RIGHT_BUMPER = new GamepadButtons((byte)1, "right bumper");
    public static final GamepadButtons LEFT_TRIGGER = new GamepadButtons((byte)2, "left trigger");
    public static final GamepadButtons RIGHT_TRIGGER = new GamepadButtons((byte)3, "right trigger");
    public static final GamepadButtons DIAMOND_DOWN = new GamepadButtons((byte)4, "diamond down");
    public static final GamepadButtons DIAMOND_UP = new GamepadButtons((byte)5, "diamond up");
    public static final GamepadButtons DIAMOND_LEFT =  new GamepadButtons((byte)6, "diamond left");
    public static final GamepadButtons DIAMOND_RIGHT = new GamepadButtons((byte)7, "diamond right");
    public static final GamepadButtons MIDDLE_LEFT = new GamepadButtons((byte)8, "middle left");
    public static final GamepadButtons MIDDLE_RIGHT = new GamepadButtons((byte)9, "middle right");
    public static final GamepadButtons LEFT_JOYSTICK_BUTTON = new GamepadButtons((byte)10, "left joystick button");
    public static final GamepadButtons RIGHT_JOYSTICK_BUTTON = new GamepadButtons((byte)11, "right joystick button");
    public static final GamepadButtons DPAD_DOWN = new GamepadButtons((byte)12, "dpad down");
    public static final GamepadButtons DPAD_UP = new GamepadButtons((byte)13, "dpad up");
    public static final GamepadButtons DPAD_LEFT = new GamepadButtons((byte)14, "dpad left");
    public static final GamepadButtons DPAD_RIGHT = new GamepadButtons((byte)15, "dpad right");
    
    /**
     * Internal constructor used to create a GamepadButtons value.
     * @param value The internal value used for equality comparisons.
     * @param name The name of this GamepadButtons value.
     */
    private GamepadButtons(byte value, String name){
        this.value = value;
        this.name = name;
    }
    
    /**
     * Returns the name of this button in lower case.
     * @return The name of this button in lower case.
     */
    public String getName(){
        return name.toLowerCase();
    }
    
    /**
     * Returns the internal byte value used for equality testing.
     * @return The byte value used for equality testing.
     */
    private byte getValue(){
        return value;
    }

    /**
     * Computes an integer hash code value for this GamepadButtons object.
     * @return An integer hash code value for this GamepadButtons object.
     */
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.value;
        return hash;
    }
    
    /**
     * Tests this object against another for equality. Another object is
     * equal to this one if it is also a GamepadButtons object and if
     * it has the same internal byte value.
     * @param other The other object to be compared to this one for equality.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof GamepadButtons){
            return ((GamepadButtons)other).getValue() == getValue();
        }
        return false;
    }
    
    /**
     * Produces a human-readable representation of this GamepadButtons object.
     * @return A human-readable representation of this GamepadButtons object.
     */
    public String toString(){
        return getName();
    }
}
