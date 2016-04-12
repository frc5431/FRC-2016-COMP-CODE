package org.usfirst.frc.team5431.components;

import org.usfirst.frc.team5431.map.OI;
import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.threads.KillerThread;
import org.usfirst.frc.team5431.map.MotorMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class that handles tank drive.
 * 
 * @author AcademyHS Robotics Team 5431
 * 
 */
public class DriveBase {

	private static CANTalon rearleft, frontleft, rearright, frontright;
	private static RobotDrive drive;
	private static KillerThread killThread;

	private static final int robotWidth = 23 + (1 / 8);
	
	private static double left = 0;
	private static double right = 0;
	private static double leftDistance = 0;
	private static double rightDistance = 0;
	private static int leftNegate = 1;
	private static int rightNegate = 1;
	

	/**
	 * Default constructor
	 * 
	 * @see #DriveBase(boolean b)
	 */
	public DriveBase() {
		this(false);
	}

	/**
	 * Construtor with an option to have brakemode enabled
	 * 
	 * @param brakeMode
	 *            Whether to enable brakemode on the {@linkplain CANTalon
	 *            motors}
	 */
	public DriveBase(boolean brakeMode) {
		this.rearleft = new CANTalon(MotorMap.REAR_LEFT);
		this.frontleft = new CANTalon(MotorMap.FRONT_LEFT);
		this.rearright = new CANTalon(MotorMap.REAR_RIGHT);
		this.frontright = new CANTalon(MotorMap.FRONT_RIGHT);

		this.rearleft.enable();
		this.frontleft.enable();
		this.rearright.enable();
		this.frontright.enable();
		
		//this.frontleft.setInverted(true);
		//this.rearleft.setInverted(true);
		//this.frontright.setInverted(true);
		this.rearright.setInverted(true);
		
		this.rearleft.clearStickyFaults();
		this.frontleft.clearStickyFaults();
		this.rearright.clearStickyFaults();
		this.frontright.clearStickyFaults();

		rearright.setInverted(true);

		rearleft.clearStickyFaults();
		frontleft.clearStickyFaults();
		rearright.clearStickyFaults();
		frontright.clearStickyFaults();

		rearleft.enableBrakeMode(brakeMode);
		frontleft.enableBrakeMode(brakeMode);
		frontright.enableBrakeMode(brakeMode);
		rearright.enableBrakeMode(brakeMode);

		drive = new RobotDrive(frontleft, rearleft, frontright, rearright);
		
		killThread = new KillerThread();
			
	}

	/**
	 * Uses {@linkplain RobotDrive#tankDrive(double l, double r) tank drive} to
	 * drive.
	 * 
	 * @param left
	 *            Value of the left joystick, where -1 is the lowest, 0 is the
	 *            center, and 1 is the highest.
	 * @param right
	 *            Value of the right joystick, where -1 is the lowest, 0 is the
	 *            center, and 1 is the highest.
	 */
	public void drive(double left, double right) {
			Robot.table.putNumber("LEFT-DRIVE", left);
			Robot.table.putNumber("RIGHT-DRIVE", right);
			drive.tankDrive(left, right);
	}

	/*
	 * Make the joystick inputs curved for a natural dead zone(No jumping) And
	 * also allow smaller more precise movements
	 */
	private double exp(double Speed) {
		return Speed;// (double) Math.pow(Speed, 1.8);
	}

	public boolean checkState() {
		return true;
	}

	/**
	 * Checks input and drives based on an {@linkplain OI OI}
	 * 
	 * @param map
	 *            Current operator interface.
	 */
	public void checkInput(OI map) {
		this.drive(-exp(map.getDriveLeftYAxis()), -exp(map.getDriveRightYAxis()));

	}

}