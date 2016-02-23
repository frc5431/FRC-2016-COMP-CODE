package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;

public class KillerThread extends Thread {
	
	public static volatile boolean killed = false;
	private long time = 0;
	private boolean RPM = false;
	private double minRPM = 230;
	
	public KillerThread() {
		this.time = 0;
		killed = false;
	}
	
	public void setKillTime(long seconds) {
		time = (seconds * 1000) - 10; //Remove thread startup time
	}
	
	public void setRPMmin(double rpm) {
		RPM = true;
		minRPM = 200;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(time);
			if(RPM && ((Robot.encoder.rightFlyRPM() < minRPM) || (Robot.encoder.leftFlyRPM() < minRPM))) killed = true; 
		} catch (InterruptedException e) {
		} finally {
			if(!RPM) killed = true;
		}
	}
	
}
