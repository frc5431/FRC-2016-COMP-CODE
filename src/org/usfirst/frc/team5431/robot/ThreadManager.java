package org.usfirst.frc.team5431.robot;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.usfirst.frc.team5431.threads.DriveThread;
import org.usfirst.frc.team5431.threads.VisionThread;

public class ThreadManager extends Thread{
	
	private final Executor exe = Executors.newCachedThreadPool();
	
	public final VisionThread vision;
	private boolean visionOn = false, driveOn = false;
	private final DriveThread drive;
	
	public ThreadManager() {
		vision = new VisionThread();
		drive = new DriveThread();
	}
	
	private void visionHandle() {
		if(visionOn) {
			vision.checkAccess();
		}
	}
	
	public void drive(double left, double right){
		drive.drive(left, right);
	}
	
	public void driveHandle() {
		if(driveOn) {
			
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
