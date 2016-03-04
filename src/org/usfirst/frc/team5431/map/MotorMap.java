package org.usfirst.frc.team5431.map;

import org.usfirst.frc.team5431.robot.Robot;

/**
 * Class that defines the ID's for the motors.
 * 
 * @author AcademyHS Robotics Team 5431
 * @see CANTalon
 **/
public final class MotorMap {
	// to prevent instantiation
	private MotorMap() {
	}
	
	public static final double DEFAULT_FLYWHEEL_SPEED=0.4;
	public static final double DEFAULT_INTAKE_SPEED=0.7;

	/**
	 * Channel ID for the left flywheel in the shooter mechanism, which is
	 * {@value #LEFT_FLY}
	 * 
	 * @see Robot
	 **/
	public static final int LEFT_FLY;
	/**
	 * Channel ID for the right flywheel in the shooter mechanism, which is
	 * {@value #RIGHT_FLY}
	 * 
	 * @see Robot
	 **/
	public static final int RIGHT_FLY;
	/**
	 * Channel ID for the top intake motor in the shooter mechanism, which is
	 * {@value #INTAKE}
	 * 
	 * @see Robot
	 **/
	public static final int INTAKE;
	/**
	 * Channel ID for the bottom intake motor in the shooter mechanism, which is
	 * {@value #INTAKE_BOT}
	 * 
	 * @see Robot
	 **/
	public static final int CLIMBER;
	/**
	 * Channel ID for the front right wheel on the drive base, which is
	 * {@value #FRONT_RIGHT}
	 * 
	 * @see Robot
	 **/
	public static final int FRONT_RIGHT;
	/**
	 * Channel ID for the front left wheel on the drive base, which is
	 * {@value #FRONT_LEFT}
	 * 
	 * @see Robot
	 **/
	public static final int FRONT_LEFT;
	/**
	 * Channel ID for the rear right wheel on the drive base, which is
	 * {@value #REAR_RIGHT}
	 * 
	 * @see Robot
	 **/
	public static final int REAR_RIGHT;
	/**
	 * Channel ID for the rear left wheel on the drive base, which is
	 * {@value #REAR_LEFT}
	 * 
	 * @see Robot
	 **/
	public static final int REAR_LEFT;

	static {
		REAR_LEFT = 3;
		REAR_RIGHT = 6;
		FRONT_LEFT = 4;
		FRONT_RIGHT = 7;
		CLIMBER = 8;
		INTAKE = 5;
		RIGHT_FLY = 9;
		LEFT_FLY = 2;
	}
}
