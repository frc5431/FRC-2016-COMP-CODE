package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.components.TurretBase;
import org.usfirst.frc.team5431.robot.Robot;

public class TurretThread extends TemplateThread {
	public static TurretBase turret;
	private KillerThread killThread;

	public TurretThread() {
		super(200);
		turret = new TurretBase();
		killThread = new KillerThread();
		killThread.setKillTime(10);
		killThread.setRPMmin(40);
	}
	
	public void startShoot(double speed) {
		turret.setMotorSpeed(speed);
		turret.shoot();
		killThread.notifyAll();
	}
	
	public void stopShoot() {
		turret.stopShoot();
		turret.shoot();
		try {
			killThread.wait();
		} catch (InterruptedException e) {}
	}

	@Override
	public void action() {
		turret.checkInput(Robot.oi);
	}

	@Override
	public void init() {

	}

}
