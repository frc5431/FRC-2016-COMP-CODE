package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.threads.VisionThread;

public class ThreadManager extends Thread{
	
	public VisionThread vision;
	private boolean visionOn = false;
	
	public ThreadManager() {
		vision = new VisionThread();
	}
	
	private void visionHandle() {
		if(visionOn) {
			vision.checkAccess();
			//vision.
		}
	}
	
	public void startVisionThread() {
		visionOn = true;
		vision.start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				this.visionHandle();
				
				Thread.sleep(100);
			} catch (Throwable error) {error.printStackTrace();}
		}
	}
}
