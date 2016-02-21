package org.usfirst.frc.team5431.threads;

import org.usfirst.frc.team5431.staticlibs.Grip;
import org.usfirst.frc.team5431.staticlibs.VisionMath;

public class VisionThread extends Thread {
	
	public VisionMath math;
	public Grip grip;
	public volatile double screenHalf = 120, offVal = 0;
	
	private static volatile double[]
			areas = {0},
			distances = {0},
			fromCenters = {0},
			holeSolids = {0};
	
	public static volatile double
			area = 0,
			distance = 0,
			fromCenter = 0,
			holeSolid = 0;
			
	public static volatile double[] manVals = {0, 0, 0};
	
	public static volatile double overspeed = 0.1;
	
	public VisionThread() {
		math = new VisionMath();
		grip = new Grip();
	}
	
	private void updateVals() {
		areas = grip.area();
		distances = grip.distance(math);
		fromCenters = grip.fromCenter(this.screenHalf, math);
		holeSolids = grip.solidity();
	}
	
	private void calcVals() {
		int toShoot = math.chooseHole(areas, distances, holeSolids, fromCenters); //Chooses an object to shoot at(Method below)
		//Robot.table.putNumber("HOLE-NUM", toShoot); //Display to dashboard what to shoot at
		if(toShoot != 666) {//Don't shoot at nothing (THE DEVIL)
			double tempCenter = grip.fromCenter(this.screenHalf, math)[toShoot]; //Temp center values
			/*
			//Display values to SmartDashboard!
			Robot.table.putNumber("HOLE-AREA", areas[toShoot]);
			Robot.table.putNumber("HOLE-DISTANCE", distances[toShoot]);
			Robot.table.putNumber("HOLE-CENTER", tempCenter);
			Robot.table.putNumber("HOLE-SOLITIY", holeSolids[toShoot]);
			*/
			
			manVals[1] = (math.withIn(distances[toShoot], VisionMath.minDistance, VisionMath.maxDistance)) ? 0 : 
					(distances[toShoot] < VisionMath.minDistance) ? 1 : 2; //Get which direction to drive
					
			manVals[0] = (math.withIn(tempCenter, VisionMath.leftTrig, VisionMath.rightTrig)) ? 0 :
					(tempCenter < VisionMath.leftTrig) ? 1 : 2; //Amount to turn the turrent
			
			//double readyVal = math.SpeedCalc(distances[toShoot]);
			
			//Robot.table.putNumber("AUTO-AIM-SPEED", readyVal);
			
			/*
			if((forback == 0) && (lefight == 0)) {
				//Robot.table.putString("FIRE", "F");
				//Robot.table.putString("PULL", "F");	
				//Robot.led.wholeStripRGB(255, 0, 0);
				manVals[0] = readyVal + overspeed;
				manVals[1] = 0;
				manVals[2] = 0;
			} else {
				String pulling = "";
				String firing = "";
				if (forback == 1) {
					pulling = "DB";
					//Robot.led.backwards(0, 0, 255, 60);
				}else if(forback == 2) {
					pulling = "DF";
					//Robot.led.forwards(0, 255, 255, 60);
				}
				
				if(lefight == 1) {
					firing = "TL";
					//Robot.led.turnLeft(255, 135, 0, 65);
				} else if(lefight == 2) {
					firing = "TR";
					//Robot.led.turnRight(255, 135, 0, 65);
				}
				//Robot.table.putString("PULL", pulling);
				//Robot.table.putString("FIRE", firing);
				
				manVals[0] = offVal;
				manVals[1] = lefight;
				manVals[2] = forback;
				
			}*/
		} else {
			///Robot.table.putString("FIRE", "NA");
			//Robot.table.putString("PULL", "NA");
			//Robot.led.wholeStripRGB(120, 140, 120);
			manVals[0] = 5;
			manVals[1] = 5;
		}
	}
	
	public double getSpeed() {
		return math.SpeedCalc(distance);
	}
	
	public double getDistance() {
		return distance;
	}
	
	public double getFromCenter() {
		return fromCenter;
	}
	
	public double getLeftRight() {
		return manVals[0];
	}
	
	public double getForwardBackward() {
		return manVals[1]; 
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				this.updateVals();
				this.calcVals();
				Thread.sleep(10);
			} catch(Throwable dontquit) {dontquit.printStackTrace();}
		}
		
	}
	
}
