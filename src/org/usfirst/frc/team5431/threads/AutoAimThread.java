package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.robot.Robot;
import org.usfirst.frc.team5431.robot.ThreadManager;

import edu.wpi.first.wpilibj.Timer;

public class AutoAimThread extends Thread {

	public static volatile boolean autoRun = false;

	private boolean isDriving() {
		return (Robot.oi.getDriveLeftYAxis() < -0.1 || Robot.oi.getDriveLeftYAxis() > 0.1
				|| Robot.oi.getDriveRightYAxis() < -0.1 || Robot.oi.getDriveRightYAxis() > 0.1);
	}

	private boolean driverAuto(double nums[]) {
		boolean pass = false;

//		if (nums[1] == 1) {
//			ThreadManager.Drive(-0.48, -0.48);
//
//		} else if (nums[1] == 2) {
//			ThreadManager.Drive(0.48, 0.48);
//
//		} else if (nums[0] == 1) {
//			// ThreadManager.Drive(-0.45, 0.45);
//
//		} else if (nums[0] == 2) {
//			// ThreadManager.Drive(0.45, -0.45);
//
//		} else if (nums[0] == 5) {
//			ThreadManager.Drive(0, 0);
//
//		} else {
//			ThreadManager.Drive(0, 0);
//			pass = true;
//		}

		return pass;
	}

	private void driverGun() {
		final double gunSpeed = VisionThread.getSpeed();
		Robot.table.putNumber("AUTO-AIM-SPEED", gunSpeed);
		Robot.turret.setMotorSpeed(gunSpeed);
		Robot.turret.shoot(false);
	}

	private void onKillAuton() {
		Robot.intake.stopIntake();
		Robot.turret.stopShoot();
		Robot.turret.shoot(false);
	}

	@Override
	public void run() {
		while (true) {
			try {
			if (Robot.oi.isAutoShoot()) {
				Robot.table.putString("ERROR", "Auto aim pressed");
				autoRun = true;
				while (autoRun) {
					if (this.isDriving()) {
						autoRun = false;
						break;
					}

					boolean shoot = false;

					shoot = (this.driverAuto(VisionThread.manVals));
					Timer.delay(0.005);

					if (shoot) {
						for (int tries = 0; tries < 200; tries++) {
							if (!autoRun || this.isDriving()) {
								this.onKillAuton();
								return;
							}
							this.driverGun();
							try {
								Thread.sleep(0, 5);
							} catch (InterruptedException e) {
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
						this.driverAuto(VisionThread.manVals);
						for (int a = 0; a < 310; a++) {
							if (!autoRun || this.isDriving()) {
								this.onKillAuton();
								return;
							}
							Robot.intake.intakeMax();
							Robot.intake.intake();
							try {
								Thread.sleep(0, 5);
							} catch (InterruptedException e) {
							}
						}
						this.onKillAuton();
						shoot = false;
						autoRun = false;
					} else {
						try {
							Thread.sleep(0, 5);
						} catch (InterruptedException e) {
						}
					}

				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		  } catch(Throwable crap) {}
		}
	}

}
