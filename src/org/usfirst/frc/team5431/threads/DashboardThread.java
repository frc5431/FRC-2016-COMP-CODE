package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.staticlibs.VisionMath;

public class DashboardThread extends Thread{

	@Override
	public void run() {
		while(true) {
			try {
				Robot.table.putBoolean("connection",true);
				Robot.table.putBoolean("ENABLED", Robot.connection);
				VisionMath.override = (Robot.oi.getGunThrottle()/2);
				Robot.table.putNumber("OVERDRIVE", VisionMath.override);
				
				Robot.table.putNumber("FLY-LEFT", Robot.encoder.leftFlyRPM());
				Robot.table.putNumber("FLY-RIGHT", Robot.encoder.rightFlyRPM());
				Robot.table.putNumber("DISTANCE-DRIVE", (Robot.encoder.LeftDistance() + Robot.encoder.RightDistance())/2);
				Thread.sleep(200);
			} catch (InterruptedException e) {}
		}
	}
	
}
