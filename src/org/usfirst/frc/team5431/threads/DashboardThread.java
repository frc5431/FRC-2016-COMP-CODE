package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;

public class DashboardThread implements Runnable{

	@Override
	public void run() {
		Robot.table.putBoolean("connection",true);
	}
	
}
