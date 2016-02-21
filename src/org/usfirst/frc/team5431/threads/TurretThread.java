package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.components.TurretBase;
import org.usfirst.frc.team5431.robot.Robot;

public class TurretThread extends TemplateThread {
	private final TurretBase turret;

	public TurretThread() {
		super(200);
		turret = new TurretBase();
	}

	@Override
	public void action() {
		turret.checkInput(Robot.oi);
	}

	@Override
	public void init() {

	}

}
