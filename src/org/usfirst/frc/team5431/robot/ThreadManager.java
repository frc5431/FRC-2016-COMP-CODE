package org.usfirst.frc.team5431.robot;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.usfirst.frc.team5431.robot.Robot.AutoTask;
import org.usfirst.frc.team5431.threads.AutoAimThread;
import org.usfirst.frc.team5431.threads.AutonThread;
import org.usfirst.frc.team5431.threads.DashboardThread;
import org.usfirst.frc.team5431.threads.DriveThread;
//import org.usfirst.frc.team5431.threads.IntakeThread;
//import org.usfirst.frc.team5431.threads.TurretThread;
import org.usfirst.frc.team5431.threads.VisionThread;

import edu.wpi.first.wpilibj.Timer;

public class ThreadManager{
	
	private final Executor exe = Executors.newFixedThreadPool(4);
	
	public VisionThread vision;
	//private static DriveThread drive;
	//private static IntakeThread intake;
	//public static TurretThread turret;
	private static DashboardThread dashboard;
	private static AutonThread auton;
	private static AutoAimThread autoAim;
	
	public ThreadManager() {
		try {
			vision = new VisionThread();
			vision.setDaemon(true);
//			drive = new DriveThread();
//			drive.setDaemon(true);
//			//intake = new IntakeThread();
//			drive.setDaemon(true);
			//turret = new TurretThread();
			//turret.setDaemon(true);
			dashboard = new DashboardThread();
			dashboard.setDaemon(true);
			auton = new AutonThread();
			auton.setDaemon(true);
			autoAim = new AutoAimThread();
			autoAim.setDaemon(true);
		} catch(Throwable error) {
			Robot.table.putString("ERROR", "Error Starting the threads (In Thread manager)!");
			error.printStackTrace();
		}
	}
	
//	public static void Drive(double left, double right){
//		drive.drive(left, right);
//	}
//	
//	public static void DriveForSeconds(double left, double right, double seconds){
//		drive.driveForSeconds(left, right, seconds);
//	}
//	
//	public static void Drive_Straight(double distance, double speed, double curve, long timeout){
//		drive.drive_straight(distance, speed, curve, timeout);
//	}
//	
//	public static void Drive_Straight_No_Correction(double distance, double speed, double curve, long timeout){
//		drive.drive_straight_no_correction(distance, speed, curve, timeout);
//	}
//	
//	public static void Drive_Turn(double degrees, double speed, double curve){
//		drive.drive_turn(degrees, speed, curve);
//	}
	
//	public static void Shoot(double speed) {
//		turret.startShoot(speed);
//	}
//	
//	public static void stopShoot() {
//		turret.stopShoot();
//	}
	
//	public static void startIntake() {
//		intake.startIntake();
//	}
	
//	public static void stopIntake() {
//		intake.stopIntake();
//	}
	
	public void startVisionThread() {
		exe.execute(vision);
	}
	
//	public void startDriveThread() {
//		exe.execute(drive);
//	}
	
//	public void startIntakeThread() {
//		exe.execute(intake);
//	}
//	
//	public void startTurretThread() {
//		exe.execute(turret);
//	}
	
	public void startDashboardThread() {
		exe.execute(dashboard);
	}
	
	public void startAutonThread() {
		exe.execute(auton);
	}
	
	public void killAutonThread() {
		AutonThread.autoRun = false;
		Timer.delay(0.1);
		try {auton.wait();} catch (InterruptedException e) {e.printStackTrace();}
		auton.interrupt();
	}
	
	public void startAutoAimThread() {
		exe.execute(autoAim);
	}
	
	public void killAutoAimThread() {
		autoAim.interrupt();
	}
	
	public void setAutonMode(AutoTask task) {
		auton.setRunner(task);
	}
}
