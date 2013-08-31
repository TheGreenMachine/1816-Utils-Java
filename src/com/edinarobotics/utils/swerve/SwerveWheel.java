package com.edinarobotics.utils.swerve;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Represents a wheel on a Swerve Drivetrain
 */
public class SwerveWheel {

	/**
	 * Enumeration of location of wheels for easier handling.
	 */
	public static class WheelLocation {
		/**
		 * The byte value of the enumeration
		 */
		public byte val;
		
		public static final WheelLocation FRONT_LEFT = new WheelLocation((byte) 0);
		public static final WheelLocation FRONT_RIGHT = new WheelLocation((byte) 1);
		public static final WheelLocation REAR_LEFT = new WheelLocation((byte) 2);
		public static final WheelLocation REAR_RIGHT = new WheelLocation((byte) 3);
		
		public boolean equals(Object other) {
			if(other instanceof WheelLocation){
                return ((WheelLocation) other).val == this.val;
            }
            return false;
		}
		
		public int hashCode() {
            return new Byte(val).hashCode();
        }
		
		private WheelLocation(byte value) {
			this.val = value;
		}
	}
	
	private WheelLocation location;
	private SpeedController speedController;
	private SpeedController angleController;
	private Encoder speedEncoder;
	private Encoder angleEncoder;
	private double lowerNoZone = 0.0;
	private double upperNoZone = 0.0;
	
	/**
	 * Creates a SwerveWheel object that represents a wheel on Swerve drivetrain robot.
	 * @param location The location of the wheel on the robot
	 * @param speedController The SpeedController that handles the speed of the wheel's rotation
	 * @param angleController The SpeedController that handles the angle of the wheel
	 * @param speedEncoder The encoder on the wheel for speed sensing
	 * @param angleEncoder The encoder on the wheel for angle sensing
	 */
	public SwerveWheel(WheelLocation location, SpeedController speedController,
			SpeedController angleController, Encoder speedEncoder, Encoder angleEncoder) {
		this.location = location;
		this.speedController = speedController;
		this.angleController = angleController;
		this.speedEncoder = speedEncoder;
		this.angleEncoder = angleEncoder;
	}
	
	/**
	 * Creates a SwerveWheel object that represents a wheel on Swerve drivetrain robot.
	 * @param location The location of the wheel on the robot
	 * @param speedController The SpeedController that handles the speed of the wheel's rotation
	 * @param angleController The SpeedController that handles the angle of the wheel
	 * @param speedEncoder The encoder on the wheel for speed sensing
	 * @param angleEncoder The encoder on the wheel for angle sensing
	 * @param lowerNoZone The lower limit of the no-rotation area, in degrees (-180 to 180 degrees,
	 * 0 being straight forward)
	 * @param upperNoZone The upper limit of the no-rotation area, in degrees (-180 to 180 degrees,
	 * 0 being straight forward)
	 */
	public SwerveWheel(WheelLocation location, SpeedController speedController,
			SpeedController angleController, Encoder speedEncoder, Encoder angleEncoder,
			double lowerNoZone, double upperNoZone) {
		this(location, speedController, angleController, speedEncoder, angleEncoder);
		this.lowerNoZone = lowerNoZone;
		this.upperNoZone = upperNoZone;
	}
	
	/**
	 * Returns the location of the wheel on the robot
	 * @return The wheel's location
	 */
	public WheelLocation getLocation() {
		return location;
	}
	
	/**
	 * Returns the SpeedController handling the rotation speed of the wheel
	 * @return The wheel's speed-controlling SpeedController
	 */
	public SpeedController getSpeedController() {
		return speedController;
	}
	
	/**
	 * Returns the SpeedController handling the angle-control of the wheel
	 * @return The wheel's angle-controlling SpeedController
	 */
	public SpeedController getAngleController() {
		return angleController;
	}
	
	/**
	 * Returns the Encoder sensing the speed of the wheel
	 * @return The wheel's speed encoder
	 */
	public Encoder getSpeedEncoder() {
		return speedEncoder;
	}
	
	/**
	 * Returns the Encoder sensing the angle of the wheel
	 * @return The wheel's angle encoder
	 */
	public Encoder getAngleEncoder() {
		return angleEncoder;
	}
	
	/**
	 * @FIXME
	 */
	public double getWheelSpeed() {
		return speedEncoder.getRate();
	}

	/**
	 * @FIXME
	 */
	public double getWheelAngle() {
		return angleEncoder.get();
	}
	
	/**
	 * Method that returns if the wheel has any no-rotation zones
	 * @return true if wheel has no-zones, false if it doesn't
	 */
	public boolean hasNoZones() {
		return (lowerNoZone == upperNoZone);
	}
	
	/**
	 * Checks if the given angle falls in the no-rotation zone of the wheel
	 * @param angle The angle to check against the no zone
	 * @return true if angle is within no zone, false if it isn't
	 */
	public boolean isInNoZone(double angle) {
		if(upperNoZone < lowerNoZone) {
			// if limits range includes the -180/180 break
			return (angle > lowerNoZone || angle < upperNoZone);
		} else {
			// if limits range DOESN'T include the -180/180 break
			return (angle > lowerNoZone && angle < upperNoZone);
		}
	}
	
}
