package com.edinarobotics.utils.swerve;

import com.sun.squawk.util.MathUtils;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveDrive {

	/**
	 * Enumeration representing the method of angle matching being applied on the swerve-wheeled robot.
	 */
	public static class WheelGrouping {
		private byte value;
		
		/**
		 * All wheels are independently angle-controlled
		 */
		public static final WheelGrouping NONE = new WheelGrouping((byte) 0);
		
		/**
		 * FrontLeft and FrontRight wheels are matched together, and RearLeft and RearRight are
		 * matched together 
		 */
		public static final WheelGrouping FRONT_BACK = new WheelGrouping((byte) 1);
		
		/**
		 * FrontLeft and RearLeft wheels are matched together, and FrontRight and RearRight are
		 * matched together
		 */
		public static final WheelGrouping LEFT_RIGHT = new WheelGrouping((byte) 2);
		
		/**
		 * All wheels are matched and have the same angle
		 */
		public static final WheelGrouping CRAB = new WheelGrouping((byte) 3);
		
		public boolean equals(Object other) {
			if(other instanceof WheelGrouping){
                return ((WheelGrouping) other).value == this.value;
            }
            return false;
		}
		
		public int hashCode(){
            return new Byte(value).hashCode();
        }
		
		private WheelGrouping(byte value) {
			this.value = value;
		}
	}
	
	private static final int MOTOR_COUNT = 4;
	private final WheelGrouping grouping;
	
	private double wheelSpeeds[] = new double[MOTOR_COUNT];
	private double wheelAngles[] = new double[MOTOR_COUNT];	
	
	private SwerveWheel[] wheels = new SwerveWheel[MOTOR_COUNT];
	private double length, width, radius;
	
	/**
	 * Creates a SwerveDrive implementation using SwerveWheel objects, the grouping of wheels on the robot
	 * and the length and width of the robot drivetrain in arbitrary but relative units.
	 * @param wheels The 4 SwerveWheel objects represents wheels on the robot
	 * @param grouping The method in which the one or more robot wheels are angle-matched
	 * @param robotLength The length of the robot in arbitrary units
	 * @param robotWidth The width of the robot in arbitrary units
	 */
	public SwerveDrive(SwerveWheel[] wheels, WheelGrouping grouping, double robotLength, double robotWidth) {
		this.wheels = wheels;
		this.grouping = grouping;
		this.length = robotLength;
		this.width = robotWidth;
		this.radius = pythagorasHypotenuse(robotLength, robotWidth);
	}
	
	/**
	 * Polar drive method for swerve-wheeled robots.
	 * @param magnitude The strafe speed of the wheels in a given direction [0.0 - 1.0]
	 * @param direction The direction of strafing in degrees, with 0 being straight ahead
	 * and increasing clockwise
	 * @param rotation The rate of rotation of the robot [-1.0 (counterclockwise) - 1.0 (clockwise)]
	 */
	public void swerveDrivePolar(double magnitude, double direction, double rotation) {
		magnitude = limit(magnitude);
		double x = magnitude * Math.cos(direction + 90);
		double y = magnitude * Math.sin(direction + 90);
		swerveDriveCartesian(x, y, rotation);
	}
	
	/**
	 * Cartesian drive method for swerve-wheeled robots.
	 * @param x The strafe speed of the wheels on the X axis [0.0 - 1.0]
	 * @param y The strafe speed of the wheels on the Y axis [0.0 - 1.0]
	 * @param rotation The rate of rotation of the robot [-1.0 (counterclockwise) - 1.0 (clockwise)]
	 */
	public void swerveDriveCartesian(double x, double y, double rotation) {
		y = -y; // Negative because of WPIlib's conventions...
		
		// Values for reuse below
		double val_a = x - rotation * (length / radius);
		double val_b = x + rotation * (length / radius);
		double val_c = y - rotation * (width / radius);
		double val_d = y + rotation * (width / radius);
		
		// Target speed calculations
		wheelSpeeds[0] = pythagorasHypotenuse(val_b, val_c);
		wheelSpeeds[1] = pythagorasHypotenuse(val_b, val_d);
		wheelSpeeds[2] = pythagorasHypotenuse(val_a, val_d);
		wheelSpeeds[3] = pythagorasHypotenuse(val_a, val_c);
		normalize(wheelSpeeds);
		
		// Target angle calculations
		wheelAngles[0] = MathUtils.atan2(val_b, val_c);
		wheelAngles[1] = MathUtils.atan2(val_b, val_d);
		wheelAngles[2] = MathUtils.atan2(val_a, val_d);
		wheelAngles[3] = MathUtils.atan2(val_a, val_c);
		
		// Negation of speed if turn angle is large and there's a simpler way to go in a direction
		for(int i = 0;i < MOTOR_COUNT;i++) {
			double deltaAngle = Math.abs(wheelAngles[i] - wheels[i].getWheelAngle());
			double deltaComplementAngle;
			if(wheelAngles[i] < wheels[i].getWheelAngle()) {
				deltaComplementAngle = Math.abs(wheels[i].getWheelAngle() - (wheelAngles[i] + 180));
			} else {
				deltaComplementAngle = Math.abs(wheels[i].getWheelAngle() - (wheelAngles[i] - 180));
			}
			
			if(deltaAngle > deltaComplementAngle) {
				if(wheelAngles[i] < wheels[i].getWheelAngle()) {
					wheelAngles[i] = wheels[i].getWheelAngle() + deltaComplementAngle;
				} else {
					wheelAngles[i] = wheels[i].getWheelAngle() - deltaComplementAngle;
				}
				// Do the actual negation of speed
				wheelSpeeds[i] = -wheelSpeeds[i]; 
			}
		}
		
		// Application of motor speeds and angles goes here
	}
	
	/**
	 * Convenience method for calculation of hypotenuse using Pythagorean theorem
	 * @param l length/component_1
	 * @param w width/component_2
	 * @return The hypotenuse calculated using the Pythagorean theorem
	 */
	protected double pythagorasHypotenuse(double l, double w) {
		return Math.sqrt(MathUtils.pow(l, 2.0) + MathUtils.pow(w, 2.0));
	}
	
	/**
     * Limit motor values to the -1.0 to +1.0 range.
     * @param num The number to limit to range [-1.0, 1.0]
     * @return The limited number
     */
    protected static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }

    /**
     * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0.
     * @param wheelSpeeds The array of wheel speeds to normalize
     */
    protected static void normalize(double wheelSpeeds[]) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        int i;
        for (i=1; i<MOTOR_COUNT; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) maxMagnitude = temp;
        }
        if (maxMagnitude > 1.0) {
            for (i=0; i<MOTOR_COUNT; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

}
