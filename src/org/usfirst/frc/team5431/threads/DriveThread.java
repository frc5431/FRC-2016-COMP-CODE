package org.usfirst.frc.team5431.threads;

import java.io.File;

import javax.swing.ImageIcon;

import org.usfirst.frc.team5431.components.DriveBase;
import org.usfirst.frc.team5431.robot.Robot;

public class DriveThread extends TemplateThread{
	private final DriveBase drive;
	
	public DriveThread() {
		super(200);
		drive = new DriveBase();
	}

	@Override
	public void action() {
		drive.checkInput(Robot.oi);
	}
	
	public void drive(double left, double right){
		drive.drive(left, right);
	}

	@Override
	public void init() {		
	}

	
}
