package org.usfirst.frc.team5431.robot;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.usfirst.frc.team5431.robot.Robot.AutoTask;
import org.usfirst.frc.team5431.threads.AutonThread;
import org.usfirst.frc.team5431.threads.DashboardThread;
import org.usfirst.frc.team5431.threads.DriveThread;
import org.usfirst.frc.team5431.threads.IntakeThread;
import org.usfirst.frc.team5431.threads.TurretThread;
import org.usfirst.frc.team5431.threads.VisionThread;

public class ThreadManager extends Thread{
	
	private final Executor exe = Executors.newCachedThreadPool();
	private boolean visionOn = false, driveOn = false;
	
	public VisionThread vision;
	private static DriveThread drive;
	private static IntakeThread intake;
	public static TurretThread turret;
	private final DashboardThread dashboard;
	private final AutonThread auton;
	
	public ThreadManager() {
		vision = new VisionThread();
		drive = new DriveThread();
		intake = new IntakeThread();
		turret = new TurretThread();
		dashboard = new DashboardThread();
		auton = new AutonThread();
	}
	
	private void visionHandle() {
		if(visionOn) {
			vision.checkAccess();
			if(!vision.isAlive()) {
				vision = new VisionThread();
				vision.start();
			}
		}
	}
	
	public static void Drive(double left, double right){
		drive.drive(left, right);
	}
	
	public static void DriveForSeconds(double left, double right, double seconds){
		drive.driveForSeconds(left, right, seconds);
	}
	
	public static void Drive_Straight(double distance, double speed, double curve, long timeout){
		drive.drive_straight(distance, speed, curve, timeout);
	}
	
	public static void Drive_Straight_No_Correction(double distance, double speed, double curve, long timeout){
		drive.drive_straight_no_correction(distance, speed, curve, timeout);
	}
	
	public static void Drive_Turn(double degrees, double speed, double curve){
		drive.drive_turn(degrees, speed, curve);
	}
	
	public static void Shoot(double speed) {
		turret.startShoot(speed);
	}
	
	public static void stopShoot() {
		turret.stopShoot();
	}
	
	public static void startIntake() {
		intake.startIntake();
	}
	
	public static void stopIntake() {
		intake.stopIntake();
	}
	
	public void driveHandle() {
		if(driveOn) {
			if(!drive.checkDrive()) {
				drive.drive(0, 0);
			}
		}
	}
	
	public void startVisionThread() {
		visionOn = true;
		exe.execute(vision);
	}
	
	public void startDriveThread() {
		driveOn = true;
		exe.execute(drive);
	}
	
	public void startIntakeThread() {
		exe.execute(intake);
	}
	
	public void startTurretThread() {
		exe.execute(turret);
	}
	
	public void startDashboardThread() {
		exe.execute(dashboard);
	}
	
	public void startAutonThread() {
		exe.execute(auton);
	}
	
	public void killAutonThread() {
		AutonThread.autoRun = false;
	}
	
	public void setAutonMode(AutoTask task) {
		auton.setRunner(task);
	}
	
	@Override
	public void run() {
		visionOn=true;
		while(true) {
			try {
				this.visionHandle();
				this.driveHandle();
				Thread.sleep(600);
			} catch (Throwable error) {error.printStackTrace();}
		}
	}
}
