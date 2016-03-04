package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.components.DriveBase;
import org.usfirst.frc.team5431.robot.Robot;

public class DriveThread extends TemplateThread{
	private final DriveBase drive;
	
	public DriveThread() {
		super(200);
		drive = new DriveBase();
	}

	@Override
	public void action() {
		drive.checkInput(Robot.oi);
	}
	
	public void drive(double left, double right){
		drive.drive(left, right);
	}
	
//	public void driveForSeconds(double left, double right, double seconds){
//		drive.driveForSeconds(left, right, seconds);
//	}
//	
//	public void drive_straight(double distance, double speed, double curve, long timeout){
//		drive.auto_driveStraight(distance, speed, curve, timeout);
//	}
//	
//	public void drive_turn(double degrees, double speed, double curve){
//		drive.auto_driveTurn(degrees, speed, curve);
//	}
//	
//	public void drive_straight_no_correction(double distance, double speed, double curve, long timeout){
//		drive.auto_driveStraightNoCorrection(distance, speed, curve, timeout);
//	}
	
	public boolean checkDrive() {
		return drive.checkState();
	}

	@Override
	public void init() {		
	}

	
}
