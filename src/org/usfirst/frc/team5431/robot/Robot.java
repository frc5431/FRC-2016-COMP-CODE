
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.components.DriveBase;
import org.usfirst.frc.team5431.components.EncoderBase;
import org.usfirst.frc.team5431.components.Intake;
import org.usfirst.frc.team5431.components.TurretBase;
import org.usfirst.frc.team5431.map.OI;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	public static enum AutoTask {
		AutoShootLowbar, 
		AutoShootCenter, 
		AutoShootMoat, 
		BarelyForward, 
		StandStill
	};

    static SendableChooser 
    			chooser, 
    			auton_select;
    public static volatile OI oi;
    public static volatile NetworkTable table;
    public static volatile ThreadManager threadManager; 
    public static volatile EncoderBase encoder;
    public static volatile Intake intake;
    public static volatile DriveBase drive;
    public static volatile TurretBase turret;
	public static volatile AutoTask currentAuto;
	
	public static volatile boolean 
			connection = false, 
			runOnce = false;
	
	public static volatile double[] 
			flyRPM = {0, 0};
	

    public void robotInit() {
    	
    	table = NetworkTable.getTable("5431");
        oi = new OI();
        encoder = new EncoderBase();
    	encoder.resetModules();
    	
		auton_select = new SendableChooser();
		auton_select.addObject("AutoShoot Lowbar", AutoTask.AutoShootLowbar);
		auton_select.addDefault("AutoShoot Center4", AutoTask.AutoShootCenter);
		auton_select.addObject("StandStill", AutoTask.StandStill);
		auton_select.addObject("AutoShoot Moat", AutoTask.AutoShootMoat);
		auton_select.addObject("Barely Forward", AutoTask.BarelyForward);
		SmartDashboard.putData("Auto choices", auton_select);
		intake = new Intake();
		turret = new TurretBase();
		drive = new DriveBase();
		this.startThreads();
	     	
        runOnce = true;
    }
    
    private void startThreads() {
    	try {
	        threadManager = new ThreadManager();
	        threadManager.startVisionThread(); 
	        threadManager.startDashboardThread();
    	} catch(Throwable error) {
    		error.printStackTrace();
    	}
    }
    
    public void autonomousInit() {
    	connection = true;
		currentAuto = (AutoTask) auton_select.getSelected();    
		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
		try {
			threadManager.setAutonMode(currentAuto);
			threadManager.startAutonThread();
		} catch(Throwable error) {
			error.printStackTrace();
		}
    }

    public void teleopInit(){
    	connection=true;
    	try {
    		threadManager.killAutonThread();
    		threadManager.startAutoAimThread();
    	} catch(Throwable error) {
    		error.printStackTrace();
    	}
    }
    
    public void autonomousPeriodic() {Timer.delay(0.1);}
    
    public void teleopPeriodic() {
    	try {
    		intake.checkInput(oi);
    		intake.update();
    		turret.checkInput(oi);
    		drive.checkInput(oi);
    		Timer.delay(0.005);
    	} catch(Throwable threadingError) {
    		table.putString("ERROR", "The threading process has crashed in some way!!!");
    		SmartDashboard.putString("ERROR", threadingError.getMessage());
    		threadingError.printStackTrace();
    	}
    }
    
	public void disabledPeriodic() {
		try {
			threadManager.killAutonThread();
			threadManager.killAutoAimThread();
		} catch(Throwable error) {
			error.printStackTrace();
		}
		runOnce = true;
		connection = false;
	}
    
    public void testPeriodic() {}
}
