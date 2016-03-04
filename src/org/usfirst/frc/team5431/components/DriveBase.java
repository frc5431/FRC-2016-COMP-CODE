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
		rearleft = new CANTalon(MotorMap.REAR_LEFT);
		frontleft = new CANTalon(MotorMap.FRONT_LEFT);
		rearright = new CANTalon(MotorMap.REAR_RIGHT);
		frontright = new CANTalon(MotorMap.FRONT_RIGHT);

		rearleft.enable();
		frontleft.enable();
		rearright.enable();
		frontright.enable();

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
		try {
			killThread.wait();
		} catch (InterruptedException e) {e.printStackTrace();}		
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

	public void driveForSeconds(double LeftSpeed, double RightSpeed, double seconds) {
		double refresh = 0.005;
		double loops = seconds / refresh;
		for (int amount = 0; amount < loops; amount++) {
			this.drive(LeftSpeed, RightSpeed);
			Timer.delay(refresh);
		}
		this.drive(0, 0);
	}

	/**
	 * Automagically drives straight
	 */
	public void auto_driveStraight(double distance, double speed, double curve, long timeout) { // Why
		Robot.encoder.resetDrive();

		double left = 0;
		double right = 0;
		killThread.setKillTime(timeout);
		killThread.notifyAll();

		while ((((left = Robot.encoder.LeftDistance()) < distance)
				&& ((right = Robot.encoder.RightDistance()) < distance)) && !killThread.getKilled()) {
			if (left < (right - 0.01)) {
				this.drive((speed + curve + .06), (speed - curve));
			} else if (left > (right + 0.01)) {
				this.drive((speed - curve), (speed + curve) + .06);
			} else {
				this.drive((speed), (speed));
			}
		}
		this.drive(0, 0);
		try {
			killThread.wait();
		} catch (InterruptedException e) {}
	}

	public void auto_driveStraightNoCorrection(double distance, double speed, double curve, long timeout) {
		Robot.encoder.resetDrive();
		killThread.setKillTime(timeout);
		killThread.notify();

		while ((((Robot.encoder.LeftDistance()) < distance) && ((Robot.encoder.RightDistance()) < distance))
				&& !killThread.getKilled()) {
			this.drive(speed, speed);
		}
		this.drive(0, 0);
		try {killThread.wait();} catch (InterruptedException e) {e.printStackTrace();}
	}

	/**
	 * Encoder-based turning with input in degrees and speed.
	 * 
	 * @param degrees
	 *            From 0 - 180 for left and 0 to -180 for right
	 * @param speed
	 *            Speed that robot turns, from -1 to 1.
	 * @param curve
	 *            How much to speed up a side if one side is going a pulse
	 *            faster. Should be extremely small (adds to motor value for a
	 *            side).
	 */
	public void auto_driveTurn(double degrees, double speed, double curve) {
		Robot.encoder.resetDrive();
	
		// We aren't doing straight in this function are we? How am I going to
		// find distance from just degrees (which is 0).
		if (degrees != 0) { // This is to make sure that even if build team
							// programs, they won't kill themselves immediately.
			if (degrees < 0) {
				leftDistance = ((1.0 / 2.0) * degrees) * robotWidth / (360.0); // degrees
																				// negates
																				// left
																				// for
																				// us
																				// (why
																				// type
																				// more?)
				rightDistance = ((1.0 / 2.0) * -degrees) * robotWidth / (360.0); // Negative
																					// because
																					// right
																					// will
																					// need
																					// to
																					// be
																					// positive
				// SmartDashboard.putString("turnLeft", "YES");
				leftNegate = -1;
			} else {
				leftDistance = ((1.0 / 2.0) * degrees) * robotWidth / (360.0); // Negating
																				// because
																				// left
																				// needs
																				// to
																				// go
																				// backward.
				rightDistance = ((1.0 / 2.0) * degrees) * robotWidth / (360.0);
				// SmartDashboard.putString("turnLeft", "NO");
				rightNegate = -1;
			}
			// SmartDashboard.putNumber("Robot width", robotWidth);
			// SmartDashboard.putNumber("leftTurnDistance", leftDistance);
			// SmartDashboard.putNumber("rightTurnDistance", rightDistance);
			// Lets just do copy and paste here, shall we? You don't mind -
			// right, David?
			while (((left = Robot.encoder.LeftDistance()) < leftDistance * leftNegate)
					&& ((right = Robot.encoder.RightDistance()) < rightDistance * rightNegate)) {
				if (left < (right - 0.01)) {
					this.drive(((speed + curve + .06) * leftNegate), (speed - curve) * rightNegate);
				} else if (left > (right + 0.01)) {
					this.drive((speed - curve) * leftNegate, ((speed + curve) + .06) * rightNegate);
				} else {
					this.drive((speed) * leftNegate, (speed) * rightNegate);
				}
				// SmartDashboard.putNumber("leftEncoderDistance",
				// Robot.encoder.LeftDistance());
				// SmartDashboard.putNumber("rightEncoderDistance",
				// Robot.encoder.RightDistance());
			}
			this.drive(0, 0);
		}
		// else I would return something (we need to make a list of error codes
		// and not have any void functions . . .)
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