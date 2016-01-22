package com.edinarobotics.utils.sensors;

import com.edinarobotics.utils.common.Updatable;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Implements a general analog absolute encoder. This class simplifies the
 * task of working with absolute encoders by performing all required voltage
 * calculations and returning a simple angle.
 */
public class AnalogAbsoluteEncoder implements PIDSource, Updatable{
    private AnalogInput analogInput;
    private double minVolts, maxVolts, angleOffset, lastAngle, zeroingOffset;
    private boolean reversed, reverseReadDirection;
    private static final double MAX_PRE_READ_DISTANCE = 180.0;
    
    /**
     * Constructs a new AnalogAbsoluteEncoder given the analog channel to which
     * it is connected, the voltage that the encoder reads at zero degrees, and
     * the voltage that the encoder reads at 360 degrees.
     * @param analogChannel The analog channel of the absolute encoder.
     * @param voltage0Deg The voltage of the encoder at 0 degrees.
     * @param voltage360Deg The voltage of the encoder at 360 degrees.
     * @param zeroingOffset The angle offset to be applied in degrees. The angle
     * offset is <i>added</i> to the returned angle.
     * @param reverseReadDirection If {@code true} the encoder will read in the
     * opposite direction (the angle will be subtracted from 360 degrees).
     */
    public AnalogAbsoluteEncoder(AnalogInput analogChannel, double voltage0Deg, double voltage360Deg, double zeroingOffset, boolean reverseReadDirection){
        this.analogInput = analogChannel;
        minVolts = voltage0Deg;
        maxVolts = voltage360Deg;
        reversed = false;
        if(maxVolts < minVolts){
            double temp = minVolts;
            minVolts = maxVolts;
            maxVolts = temp;
            reversed = true;
        }
        this.zeroingOffset = zeroingOffset;
        this.reverseReadDirection = reverseReadDirection;
    }
    
    /**
     * Returns the raw voltage read from the analog encoder directly from the
     * analog channel.
     * @return The raw voltage (in volts) as read from the analog channel.
     */
    public double getVoltage(){
        return analogInput.getVoltage();
    }
    
    /**
     * Returns the angle read from the analog encoder in radians. This angle is
     * computed based on the voltages given to the constructor of
     * AnalogAbsoluteEncoder.
     * @return The angle indicated by this absolute encoder. The angle is
     * continuous and so could be negative or above 2*pi.
     */
    public double getAngleRadians(){
        return Math.toRadians(getAngleDegrees());
    }
    
    /**
     * Returns the angle read from the analog encoder in degrees. This angle is
     * computed based on the voltages given to the constructor of
     * AnalogAbsoluteEncoder.
     * @return The angle read from the analog encoder in degrees. The angle is
     * continuous and so could be negative or above 360.0.
     */
    public double getAngleDegrees(){
        double fractionAboveMin = (getVoltage() - minVolts) / (maxVolts - minVolts);
        double angle = (360.0 * fractionAboveMin);
        if(reversed){
            angle = 360.0 - angle;
        }
        if(reverseReadDirection){
            angle = 360.0 - angle;
        }
        angle += angleOffset;
        //Check wrap around
        double dist = angle - lastAngle;
        if(dist > MAX_PRE_READ_DISTANCE){
            //Current angle jumped too high, decrease it.
            double difference = Math.ceil(Math.abs(dist) / 360.0) * 360.0;
            angleOffset -= difference;
            angle -= difference;
        }
        if(dist < (-1.0*MAX_PRE_READ_DISTANCE)){
            //Current angle jumped too high, decrease it.
            double difference = Math.ceil(Math.abs(dist) / 360.0) * 360.0;
            angleOffset += difference;
            angle += difference;
        }
        lastAngle = angle;
        angle += zeroingOffset;
        return angle;
    }
    
    /**
     * This method is used by PIDControllers to read the value of this encoder.
     * It returns the angle of the encoder in <i>degrees</i>.
     * This method is an alias for {@link #getAngleDegrees()}.
     * @return The current angle on the encoder in degrees.
     * @see #getAngleDegrees()
     */
    public double pidGet(){
        return this.getAngleDegrees();
    }
    
    /**
     * This method returns the offset used to zero the encoder. This is the
     * offset that is added to the angle in the final step just prior to
     * returning.
     * @return The offset added to the angle to zero the encoder. This is
     * measured in degrees.
     */
    public double getOffset(){
        return zeroingOffset;
    }
    
    /**
     * This method sets the offset used by the encoder to zero its angle.
     * This angle is measured in degrees.
     * @param offset The new offset to be used by this encoder.
     */
    public void setOffset(double offset){
        this.zeroingOffset = (offset + zeroingOffset);
    }
    
    /**
     * Convenience method used to zero this encoder. After calling this method
     * the encoder will read zero.
     */
    public void zeroEncoder(){
        setOffset(-1.0*getAngleDegrees());
    }
    
    /**
     * Calling this method causes the encoder to compute its current angle and
     * update its rollover detection.
     */
    public void update(){
        getAngleDegrees();
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}
}
