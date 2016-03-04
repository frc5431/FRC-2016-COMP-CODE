package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.robot.Robot.AutoTask;
import org.usfirst.frc.team5431.robot.ThreadManager;

import edu.wpi.first.wpilibj.Timer;

public class AutonThread extends Thread {
	
	public static volatile boolean autoRun = false;
	
	private AutoTask full;
	
	public AutonThread() {
		
	}
	
	private boolean driverAuto(double nums[]) {
		
//		if (nums[2] == 1) {
//			ThreadManager.Drive(-0.48, -0.48);
//
//		} else if(nums[2] == 2) {
//			ThreadManager.Drive(0.48, 0.48);
//
//		} else if(nums[1] == 1) {
//			ThreadManager.Drive(-0.45, 0.45);
//
//		} else if(nums[1] == 2) {
//			ThreadManager.Drive(0.45, -0.45);
//
//		} else if(nums[1] == 5){
//			ThreadManager.Drive(0, 0);
//
//		} else {
//			ThreadManager.Drive(0, 0);
//			return true;
//		}
		
		return false;
	}
	
	private void driverGun(double nums[]) {
		if(nums[1] != 5){
			Robot.table.putNumber("AUTO-AIM-SPEED", nums[0]);
			Robot.turret.setMotorSpeed(nums[0]);
			Robot.turret.shoot(false);
		}
	}
	
	public void lowbarMode() {
		// Drive 15 feet
//		Robot.encoder.resetDrive();
//		ThreadManager.DriveForSeconds(0.0, 0.0, 0.01);
//		Robot.encoder.resetDrive();
//		ThreadManager.Drive_Straight(155, 0.6, 0.05, 15);
//		ThreadManager.Drive(-0.4, -0.4);
//		Timer.delay(1);
//		ThreadManager.DriveForSeconds(0.62, 0, .3);
//		Robot.encoder.resetDrive();
//		ThreadManager.Drive_Straight(35, 0.5, 0.05, 2);
//		Timer.delay(0.25);
//		this.shoot();
	}
	
	private void onKillAuton() {
		Robot.intake.stopIntake();
		Robot.turret.stopShoot();
		Robot.turret.shoot(false);
	}
	
	private void shoot() {
			autoRun = true;
			while(autoRun) {				
				boolean shoot = false;
				shoot = (this.driverAuto(VisionThread.manVals));
				
				if(shoot) {
					for(int tries = 0; tries < 200; tries++) {
						if(!autoRun) {this.onKillAuton(); return;}
						this.driverGun(VisionThread.manVals);
						Timer.delay(0.005);
					}
					Timer.delay(0.1);
					this.driverAuto(VisionThread.manVals);
					for(int a = 0; a < 310; a++) {
						if(!autoRun) {this.onKillAuton(); return;}
						Robot.intake.intakeMax();
						Robot.intake.intake();
						Timer.delay(0.005);
					}
					this.onKillAuton();
					shoot = false;
					autoRun = false;
				}
				Timer.delay(0.005);
			}
	}
	
	public void setRunner(AutoTask current) {
		full = current;
	}
	
	@Override
	public void run() {
		try {
			Robot.encoder.resetDrive();
			Robot.encoder.resetModules();
			Thread.sleep(1);

			switch (full) {
			case AutoShootLowbar:
				this.lowbarMode();
				break; 
			case AutoShootCenter:
				
				break;
			case BarelyForward:
				
				break;
			case AutoShootMoat:
				
				break;
			case StandStill:
			default:
				Timer.delay(0.01);
				break;
			}
		} catch (Throwable e) {
			Robot.table.putString("ERROR", "Error executing autonomous");
			e.printStackTrace();
		}
	}
	
}
