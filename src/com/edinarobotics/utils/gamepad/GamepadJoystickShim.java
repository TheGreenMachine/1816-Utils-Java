package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.gamepad.buttons.ButtonShim;
import com.edinarobotics.utils.gamepad.buttons.GamepadButtons;
import com.edinarobotics.utils.gamepad.buttons.JoystickButtons;
import java.util.Hashtable;

/**
 * This class allows switching between gamepads and joysticks with minimal
 * code changes.
 * It provides access to fake Button-like objects that can be used to bind
 * commands just as with regular buttons as well as methods that regulate
 * access to appropriate axes.
 */
public class GamepadJoystickShim {
    private Gamepad gamepad;
    private Joystick joystick;
    private ControllerType controllerType;
    private Hashtable joystickButtonTable, gamepadButtonTable;
    
    // Initializer used to construct the button Hashtables in both constructors.
    {
        this.joystickButtonTable = new Hashtable();
        this.gamepadButtonTable = new Hashtable();
    }
    
    /**
     * Constructs a new GamepadJoystickShim backed by a Gamepad object.
     * @param gamepad The Gamepad object to use to back the GamepadJoystickShim.
     */
    public GamepadJoystickShim(Gamepad gamepad){
        this.controllerType = ControllerType.GAMEPAD;
        this.gamepad = gamepad;
    }
    
    /**
     * Constructs a new GamepadJoystickShim backed by a TwoAxisJoystick object.
     * @param joystick The TwoAxisJoystick object to use to back the
     * GamepadJoystickShim.
     */
    public GamepadJoystickShim(TwoAxisJoystick joystick){
        this.controllerType = ControllerType.TWOAXISJOY;
        this.joystick = joystick;
    }
    
    /**
     * Constructs a new GamepadJoystickShim backed by a ThreeAxisJoystick object.
     * @param joystick The ThreeAxisJoystick object to use to back the
     * GamepadJoystickShim.
     */
    public GamepadJoystickShim(ThreeAxisJoystick joystick){
        this.controllerType = ControllerType.THREEAXISJOY;
        this.joystick = joystick;
    }
    
    public static final class ControllerType {
        public static final ControllerType GAMEPAD = new ControllerType((byte)0, "gamepad");
        public static final ControllerType TWOAXISJOY = new ControllerType((byte)1, "twoaxisjoy");
        public static final ControllerType THREEAXISJOY = new ControllerType((byte)2, "threeaxisjoy");
        
        private byte controllerType;
        private String typeName;
        
        private ControllerType(byte controllerType, String typeName) {
            this.controllerType = controllerType;
            this.typeName = typeName;
        }
        
        private byte getControllerType() {
            return controllerType;
        }
        
        public boolean equals(Object controllerType) {
            if(controllerType instanceof ControllerType) {
                return ((ControllerType)controllerType).getControllerType() == this.getControllerType();
            }
            return false;
        }
        
        public String toString() {
            return typeName.toLowerCase();
        }
    }
    
    /**
     * Indicates whether this GamepadJoystickShim is backed by a real Gamepad
     * object.
     * @return {@code true} if this GamepadJoystickShim is backed by a Gamepad,
     * {@code false} otherwise.
     */
    public boolean isGamepad(){
        return isGamepad;
    }
    
    /**
     * Indicates whether this GamepadJoystickShim is backed by a real Joystick
     * object.
     * @return {@code true} if this GamepadJoystickShim is backed by a Joystick,
     * {@code false} otherwise.
     */
    public boolean isJoystick(){
        return !isGamepad();
    }
    
    /**
     * Returns the Gamepad backing this GamepadJoystickShim if it exists.
     * @return The Gamepad backing this GamepadJoystickShim, or {@code null}
     * if this instance is not backed by a Gamepad.
     */
    public Gamepad getGamepad(){
        return gamepad;
    }
    
    /**
     * Returns the Joystick backing this GamepadJoystickShim if it exists.
     * @return The Joystick backing this GamepadJoystickShim, or {@code null}
     * if this instance is not backed by a Joystick.
     */
    public Joystick getJoystick(){
        if(controllerType.equals(ControllerType.THREEAXISJOY))
            return (ThreeAxisJoystick)joystick;
        return (TwoAxisJoystick)joystick;
    }
    
    /**
     * Indicates the direction value to be used in mecanum control.
     * This value is computed using the halo-style gamepad control (left
     * joystick strafe, right joystick rotate) or the state of the joystick's
     * main axes.
     * @return The polar direction to be used in mecanum control.
     */
    public double getMecanumDirection(){
        if(isGamepad()){
            return gamepad.getGamepadAxisState().getLeftDirection();
        }
        return joystick.getJoystickAxisState().getDirection();
    }
    
    /**
     * Indicates the magnitude value to be used in mecanum control.
     * This value is computed using the halo-style gamepad control (left
     * joystick strafe, right joystick rotate) or the state of the joystick's
     * main axes.
     * @return The polar magnitude to be used in mecanum control.
     */
    public double getMecanumMagnitude(){
        if(controllerType.equals(ControllerType.GAMEPAD)) {
            return gamepad.getGamepadAxisState().getLeftMagnitude();
        } else if(controllerType.equals(ControllerType.TWOAXISJOY)) {
            return joystick.getJoystickAxisState().getMagnitude();
        }
        return joystick.getJoystickAxisState().getMagnitude();
    }
    
    /**
     * Indicates the rotation value to be used in mecanum control.
     * This value is computed using the halo-style gamepad control (left
     * joystick strafe, right joystick rotate) or the state of the joystick's
     * main axes.
     * @return The rotation to be used in mecanum control.
     */
    public double getMecanumRotation(){
        if(controllerType.equals(ControllerType.GAMEPAD)) {
            return gamepad.getGamepadAxisState().getRightJoystick().getX();
        } else if(controllerType.equals(ControllerType.TWOAXISJOY)) {
            return ((TwoAxisJoystick)joystick).getJoystickAxisState().getX();
        }
        return ((ThreeAxisJoystick)joystick).getTwist();
    }
    
    /**
     * Returns the state of the joystick's throttle axis, or the given default
     * value if this instance is backed by a gamepad (which has no throttle).
     * @param defaultValue The default value to return in the event
     * that the backing controller has no throttle axis.
     * @return The state of the joystick's throttle axis or the given default
     * value.
     */
    public double getThrottle(double defaultValue){
        if(controllerType.equals(ControllerType.GAMEPAD)) {
            return defaultValue;
        } else if(controllerType.equals(ControllerType.TWOAXISJOY)) {
            return ((TwoAxisJoystick)joystick).getThrottle();
        }
        return ((ThreeAxisJoystick)joystick).getThrottle();
    }
    
    /**
     * Returns the state of the primary x-axis of the gamepad or joystick.
     * The primary x-axis of the gamepad is the left x-axis. The primary
     * x-axis of the joystick is the main x-axis.
     * @return The state of the primary x-axis of the backing controller.
     */
    public double getPrimaryX(){
        if(isGamepad()){
            return gamepad.getLeftX();
        }
        return joystick.getX();
    }
    
    /**
     * Returns the state of the primary y-axis of the gamepad or joystick.
     * The primary y-axis of the gamepad is the left y-axis. The primary
     * y-axis of the joystick is the main y-axis.
     * @return The state of the primary y-axis of the backing controller.
     */
    public double getPrimaryY(){
        if(isGamepad()){
            return gamepad.getLeftY();
        }
        return joystick.getY();
    }
    
    /**
     * Returns the state of the secondary x-axis of the gamepad or the given
     * default value.
     * The secondary x-axis of the gamepad is the right x-axis.
     * @param defaultValue The default value to return in the event that
     * the backing controller is a joystick.
     * @return The state of the secondary x-axis of the backing gamepad
     * or the given default value.
     */
    public double getSecondaryX(double defaultValue){
        if(controllerType.equals(ControllerType.GAMEPAD)) {
            return gamepad.getRightX();
        }
        return defaultValue;
    }
    
    /**
     * Returns the state of the secondary y-axis of the gamepad, or the given
     * default value.
     * The secondary y-axis of the gamepad is the right y-axis.
     * @param defaultValue The default value to return in the event that
     * the backing controller is a joystick.
     * @return The state of the secondary y-axis of the backing gamepad
     * or the given default value.
     */
    public double getSecondaryY(double defaultValue){
        if(controllerType.equals(ControllerType.GAMEPAD)) {
            return gamepad.getRightY();
        }
        return defaultValue;
    }
    
    /**
     * Internal method used to manage the Hashtable storing ButtonShim
     * instances.
     * @param joystickButton The type of button to be stored.
     * @param button The button object to use if no existing ButtonShim
     * of this type is stored.
     * @return The ButtonShim object to be used as the given button type.
     */
    private ButtonShim hashtableButton(JoystickButtons joystickButton, ButtonShim button){
        if(joystickButtonTable.containsKey(joystickButton)){
            return (ButtonShim)joystickButtonTable.get(joystickButton);
        }
        joystickButtonTable.put(joystickButton, button);
        return button;
    }
    
    /**
     * Internal method used to manage the Hashtable storing ButtonShim
     * instances.
     * @param gamepadButton The type of button to be stored.
     * @param button The button object to use if no existing ButtonShim
     * of this type is stored.
     * @return The ButtonShim object to be used as the given button type.
     */
    private ButtonShim hashtableButton(GamepadButtons gamepadButton, ButtonShim button){
        if(gamepadButtonTable.containsKey(gamepadButton)){
            return (ButtonShim)gamepadButtonTable.get(gamepadButton);
        }
        gamepadButtonTable.put(gamepadButton, button);
        return button;
    }
    
    /**
     * Returns a ButtonShim instance wrapping the given button type or an
     * inactive ButtonShim instance. The result of this method will always
     * be safe to use.
     * @param joystickButton The type of the button to be returned, if possible,
     * from the backing controller.
     * @return A ButtonShim wrapping the given button type, or an empty,
     * inactive ButtonShim.
     */
    public ButtonShim getButton(JoystickButtons joystickButton){
        if(!isJoystick()){
            return new ButtonShim();
        }
        // Find the trigger
        if(joystickButton.equals(JoystickButtons.TRIGGER)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.trigger()));
        }
        // Find the shoulder button
        if(joystickButton.equals(JoystickButtons.SHOULDER_BUTTON)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.shoulderButton()));
        }
        // Find the hat buttons
        if(joystickButton.equals(JoystickButtons.HAT_BUTTON_LEFT_TOP)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatButtonLeftTop()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_BUTTON_LEFT_BOTTOM)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatButtonLeftBottom()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_BUTTON_RIGHT_TOP)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatButtonRightTop()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_BUTTON_RIGHT_BOTTOM)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatButtonRightBottom()));
        }
        // Find the hat switch buttons
        if(joystickButton.equals(JoystickButtons.HAT_SWITCH_UP)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatSwitchUp()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_SWITCH_DOWN)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatSwitchDown()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_SWITCH_LEFT)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatSwitchLeft()));
        }
        if(joystickButton.equals(JoystickButtons.HAT_SWITCH_RIGHT)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.hatSwitchRight()));
        }
        // Find the ring buttons
        if(joystickButton.equals(JoystickButtons.OUTER_RING_TOP)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.outerRingTop()));
        }
        if(joystickButton.equals(JoystickButtons.OUTER_RING_MIDDLE)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.outerRingMiddle()));
        }
        if(joystickButton.equals(JoystickButtons.OUTER_RING_BOTTOM)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.outerRingBottom()));
        }
        if(joystickButton.equals(JoystickButtons.INNER_RING_TOP)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.innerRingTop()));
        }
        if(joystickButton.equals(JoystickButtons.INNER_RING_MIDDLE)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.innerRingMiddle()));
        }
        if(joystickButton.equals(JoystickButtons.INNER_RING_BOTTOM)){
            return hashtableButton(joystickButton, new ButtonShim(joystick.innerRingBottom()));
        }
        return new ButtonShim();
    }
    
    /**
     * Returns a ButtonShim instance wrapping the given button type or an
     * inactive ButtonShim instance. The result of this method will always
     * be safe to use.
     * @param gamepadButton The type of the button to be returned, if possible,
     * from the backing controller.
     * @return A ButtonShim wrapping the given button type, or an empty,
     * inactive ButtonShim.
     */
    public ButtonShim getButton(GamepadButtons gamepadButton){
        if(!isGamepad()){
            return new ButtonShim();
        }
        // Find bumpers
        if(gamepadButton.equals(GamepadButtons.LEFT_BUMPER)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.leftBumper()));
        }
        if(gamepadButton.equals(GamepadButtons.RIGHT_BUMPER)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.rightBumper()));
        }
        // Find triggers
        if(gamepadButton.equals(GamepadButtons.LEFT_TRIGGER)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.leftTrigger()));
        }
        if(gamepadButton.equals(GamepadButtons.RIGHT_TRIGGER)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.rightTrigger()));
        }
        // Find diamond buttons
        if(gamepadButton.equals(GamepadButtons.DIAMOND_UP)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.diamondUp()));
        }
        if(gamepadButton.equals(GamepadButtons.DIAMOND_RIGHT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.diamondRight()));
        }
        if(gamepadButton.equals(GamepadButtons.DIAMOND_DOWN)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.diamondDown()));
        }
        if(gamepadButton.equals(GamepadButtons.DIAMOND_LEFT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.diamondLeft()));
        }
        // Find middle buttons
        if(gamepadButton.equals(GamepadButtons.MIDDLE_LEFT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.middleLeft()));
        }
        if(gamepadButton.equals(GamepadButtons.MIDDLE_RIGHT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.middleRight()));
        }
        // Find joystick buttons
        if(gamepadButton.equals(GamepadButtons.LEFT_JOYSTICK_BUTTON)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.leftJoystickButton()));
        }
        if(gamepadButton.equals(GamepadButtons.RIGHT_JOYSTICK_BUTTON)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.rightJoystickButton()));
        }
        // Find dpad buttons
        if(gamepadButton.equals(GamepadButtons.DPAD_UP)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.dPadUp()));
        }
        if(gamepadButton.equals(GamepadButtons.DPAD_RIGHT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.dPadRight()));
        }
        if(gamepadButton.equals(GamepadButtons.DPAD_DOWN)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.dPadDown()));
        }
        if(gamepadButton.equals(GamepadButtons.DPAD_LEFT)){
            return hashtableButton(gamepadButton, new ButtonShim(gamepad.dPadLeft()));
        }
        return new ButtonShim();
    }
    
    /**
     * Returns a ButtonShim instance wrapping the given button type or an
     * inactive ButtonShim instance. The result of this method will always
     * be safe to use. This method will pick the button type that is possible
     * given the type of the backing controller.
     * @param gamepadButton The type of button on a backing Gamepad to be
     * looked up.
     * @param joystickButton The type of button on a backing Joystick to be
     * looked up.
     * @return A ButtonShim object wrapping the type of button selected from
     * the backing controller.
     */
    public ButtonShim getButton(GamepadButtons gamepadButton, JoystickButtons joystickButton){
        if(isGamepad()){
            return getButton(gamepadButton);
        }
        return getButton(joystickButton);
    }
}
