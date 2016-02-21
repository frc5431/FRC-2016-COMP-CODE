package org.usfirst.frc.team5431.robot;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.usfirst.frc.team5431.threads.DriveThread;
import org.usfirst.frc.team5431.threads.VisionThread;

public class ThreadManager extends Thread{
	
	private final Executor exe = Executors.newCachedThreadPool();
	
	public final VisionThread vision;
	private boolean visionOn = false;
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
	
	public void startVisionThread() {
		visionOn = true;
		exe.execute(vision);
	}
	
	@Override
	public void run() {
		visionOn=true;
		exe.execute(vision);
		exe.execute(drive);
		while(true) {
			try {
				this.visionHandle();
				
				Thread.sleep(100);
			} catch (Throwable error) {error.printStackTrace();}
		}
	}
}
