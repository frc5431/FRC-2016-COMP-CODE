package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.components.Intake;
import org.usfirst.frc.team5431.robot.Robot;

public class IntakeThread extends TemplateThread{
	private final Intake intake;

	public IntakeThread() {
		super(200);
		intake = new Intake();
	}
	
	public void startIntake() {
		intake.intakeMax();
		intake.intake();
	}
	
	public void stopIntake() {
		intake.stopIntake();
		intake.intake();
	}

	@Override
	public void action() {
		intake.update();
		intake.checkInput(Robot.oi);
	}

	@Override
	public void init() {
		
	}

}
