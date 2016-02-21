
package org.usfirst.frc.team5431.robot;

import org.usfirst.frc.team5431.components.EncoderBase;
import org.usfirst.frc.team5431.map.OI;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	enum AutoTask {
		AutoShootLowbar, AutoShootCenter, AutoShootMoat, BarelyForward, StandStill
	};
	AutoTask currentAuto;
	SendableChooser auton_select;
    SendableChooser chooser;
    public static volatile OI oi;
    public static volatile NetworkTable table;
    private static ThreadManager threadManager;
    public static EncoderBase encoder;
    public volatile boolean runOnce = false;
	public static volatile boolean connection = false;
    
    //java -jar C:\Users\USERNAME\AppData\Local\GRIP\app\core-1.2.0-all.jar [MY_PROJECT.GRIP]

    public void robotInit() {
    	/*
    	 *         try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	 */
		auton_select = new SendableChooser();
		auton_select.addObject("AutoShoot Lowbar", AutoTask.AutoShootLowbar);
		auton_select.addDefault("AutoShoot Center4", AutoTask.AutoShootCenter);
		auton_select.addObject("StandStill", AutoTask.StandStill);
		auton_select.addObject("AutoShoot Moat", AutoTask.AutoShootMoat);
		auton_select.addObject("Barely Forward", AutoTask.BarelyForward);
        
        table = NetworkTable.getTable("5431");
        
        oi = new OI();
        encoder = new EncoderBase();
        
        threadManager = new ThreadManager();
        threadManager.startVisionThread();
        threadManager.startDriveThread();
        threadManager.startIntakeThread();
        threadManager.startTurretThread();
        threadManager.startDashboardThread();
        threadManager.start();
        
        runOnce = true;
        
    }
    
    public void autonomousInit() {
		currentAuto = (AutoTask) auton_select.getSelected();
		SmartDashboard.putString("Auto Selected: ", currentAuto.toString());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
		connection = false;
		Timer.delay(0.1);

		switch (currentAuto) {
		case AutoShootLowbar:
			if (runOnce) {
				//this.lowbarMode();
				runOnce = false;
			}
			break; 
		case AutoShootCenter:
			if(runOnce){
				//this.centerMode();
				runOnce = false;
			}
			break;
		case BarelyForward:
			if(runOnce){
				//this.barelyForwardMode();
				runOnce = false;
			}
			break;
		case AutoShootMoat:
			if(runOnce){
				//this.moatMode();
				runOnce = false;
			}
			break;
		case StandStill:
		default:
			Timer.delay(0.01);
			break;
		}
    }
    
    public void teleopPeriodic() {
    	connection = true;
    }
    
	public void disabledPeriodic() {
		runOnce = true;
		connection = false;
	}
    
    @Deprecated
    public void testPeriodic() {}
}
