package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;

public class KillerThread extends Thread {
	
	public volatile boolean killed = false;
	private long time = 0;
	private boolean RPM = false;
	private double minRPM = 230;
	
	public KillerThread() {
		this.time = 0;
		killed = false;
	}
	
	public void stopKiller() {
		killed = false;
		this.interrupt();
	}
	
	public void setKillTime(long seconds) {
		time = (seconds * 1000) - 30; //Remove thread startup time
	}
	
	public void setRPMmin(double rpm) {
		RPM = true;
		minRPM = rpm;
	}
	
	public boolean getKilled() {
		return killed;
	}
	
	@Override
	public void run() {
		try {
			do {
				Thread.sleep(time);
				if(RPM && ((Robot.encoder.rightFlyRPM() < minRPM) || (Robot.encoder.leftFlyRPM() < minRPM))) {
					killed = true;
					TurretThread.turret.stopShoot();
					TurretThread.turret.shoot();
				} 
			} while(RPM);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Robot.table.putString("ERROR", "Killer-Thread got interrupted");
		} finally {
			if(!RPM) killed = true;
		}
	}
	
}
