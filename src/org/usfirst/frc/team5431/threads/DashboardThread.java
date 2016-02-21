package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.staticlibs.VisionMath;

public class DashboardThread implements Runnable{

	@Override
	public void run() {
		while(true) {
			try {
				Robot.table.putBoolean("connection",true);
				Robot.table.putBoolean("ENABLED", Robot.connection);
				VisionMath.override = Robot.table.getNumber("OVERDRIVE", 0.0);
				Robot.table.putNumber("DISTANCE-DRIVE", (Robot.encoder.LeftDistance() + Robot.encoder.RightDistance())/2);
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
	}
	
}
