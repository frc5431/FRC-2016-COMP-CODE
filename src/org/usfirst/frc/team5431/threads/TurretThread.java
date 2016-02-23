package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.components.TurretBase;
import org.usfirst.frc.team5431.robot.Robot;

public class TurretThread extends TemplateThread {
	public static TurretBase turret;

	public TurretThread() {
		super(200);
		turret = new TurretBase();
	}
	
	public void startShoot(double speed) {
		turret.setMotorSpeed(speed);
		turret.shoot();
	}
	
	public void stopShoot() {
		turret.stopShoot();
		turret.shoot();
	}

	@Override
	public void action() {
		turret.checkInput(Robot.oi);
	}

	@Override
	public void init() {

	}

}
