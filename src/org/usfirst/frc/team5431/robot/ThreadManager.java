package org.usfirst.frc.team5431.robot;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.usfirst.frc.team5431.threads.DashboardThread;
import org.usfirst.frc.team5431.threads.DriveThread;
import org.usfirst.frc.team5431.threads.IntakeThread;
import org.usfirst.frc.team5431.threads.TurretThread;
import org.usfirst.frc.team5431.threads.VisionThread;

public class ThreadManager extends Thread{
	
	private final Executor exe = Executors.newCachedThreadPool();
	private boolean visionOn = false, driveOn = false;
	
	public final VisionThread vision;
	private final DriveThread drive;
	private final IntakeThread intake;
	private final TurretThread turret;
	private final DashboardThread dashboard;
	
	public ThreadManager() {
		vision = new VisionThread();
		drive = new DriveThread();
		intake = new IntakeThread();
		turret = new TurretThread();
		dashboard = new DashboardThread();
	}
	
	private void visionHandle() {
		if(visionOn) {
			vision.checkAccess();
		}
	}
	
	public void drive(double left, double right){
		drive.drive(left, right);
	}
	
	/*
	public void intake(double speed) {
		intake.intake();
	}
	
	public void turret(double speed) {
		turret.setMotorSpeed(speed);
		turret.shoot();
	}*/
	
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
