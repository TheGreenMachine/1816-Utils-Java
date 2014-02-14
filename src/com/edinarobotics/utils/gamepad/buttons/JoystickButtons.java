package com.edinarobotics.utils.gamepad.buttons;

/**
 * JoystickButtons represents all the possible button types on a joystick.
 * For information on the names given to joystick buttons, see
 * {@link com.edinarobotics.utils.gamepad.Joystick Joystick}.
 */
public class JoystickButtons {
    private final byte value;
    private final String name;
    
    public static final JoystickButtons TRIGGER = new JoystickButtons((byte)0, "trigger");
    public static final JoystickButtons HAT_BUTTON_LEFT_TOP = new JoystickButtons((byte)1, "hat button left top");
    public static final JoystickButtons HAT_BUTTON_LEFT_BOTTOM = new JoystickButtons((byte)2, "hat button left bottom");
    public static final JoystickButtons HAT_BUTTON_RIGHT_TOP = new JoystickButtons((byte)3, "hat button right top");
    public static final JoystickButtons HAT_BUTTON_RIGHT_BOTTOM = new JoystickButtons((byte)4, "hat button right bottom");
    public static final JoystickButtons SHOULDER_BUTTON = new JoystickButtons((byte)5, "shoulder button");
    public static final JoystickButtons HAT_SWITCH_UP = new JoystickButtons((byte)6, "hat switch up");
    public static final JoystickButtons HAT_SWITCH_DOWN = new JoystickButtons((byte)7, "hat switch down");
    public static final JoystickButtons HAT_SWITCH_LEFT = new JoystickButtons((byte)8, "hat switch left");
    public static final JoystickButtons HAT_SWITCH_RIGHT = new JoystickButtons((byte)9, "hat switch right");
    public static final JoystickButtons OUTER_RING_TOP = new JoystickButtons((byte)10, "outer ring top");
    public static final JoystickButtons OUTER_RING_MIDDLE = new JoystickButtons((byte)11, "outer ring middle");
    public static final JoystickButtons OUTER_RING_BOTTOM = new JoystickButtons((byte)12, "outer ring bottom");
    public static final JoystickButtons INNER_RING_TOP = new JoystickButtons((byte)13, "inner ring top");
    public static final JoystickButtons INNER_RING_MIDDLE = new JoystickButtons((byte)14, "inner ring middle");
    public static final JoystickButtons INNER_RING_BOTTOM = new JoystickButtons((byte)15, "inner ring bottom");
            
    
    /**
     * Internal constructor used to create a JoystickButtons value.
     * @param value The internal value used for equality comparisons.
     * @param name The name of this JoystickButtons value.
     */
    public JoystickButtons(byte value, String name){
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
     * Tests this object against another for equality. Another object is
     * equal to this one if it is also a JoystickButtons object and if
     * it has the same internal byte value.
     * @param other The other object to be compared to this one for equality.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof JoystickButtons){
            return ((JoystickButtons)other).getValue() == getValue();
        }
        return false;
    }
    
    /**
     * Produces a human-readable representation of this JoystickButtons object.
     * @return A human-readable representation of this JoystickButtons object.
     */
    public String toString(){
        return getName();
    }
}
