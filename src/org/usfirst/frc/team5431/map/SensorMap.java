package org.usfirst.frc.team5431.map;

/**
 * Class which defines various constants for {@link Encoder Encoders} and {@link Counter Counters}
 * @author AcademyHS Robotics Team 5431
 *
 */
public class SensorMap {
	
	/**
	 * Constant which defines which sensor ID on the RoboRIO belongs to the sensor.
	 */
	public static final int RIGHT_ENCODER_1,
				RIGHT_ENCODER_2,
				LEFT_ENCODER_1,
				LEFT_ENCODER_2,
				FLY_WHEEL_RIGHT_COUNTER ,
				FLY_WHEEL_LEFT_COUNTER ,
				INTAKE_LIMIT;
				//RIGHT_INTAKE_LIMIT;
	
	
	
	static {
			RIGHT_ENCODER_1=1;
			RIGHT_ENCODER_2=0;
			LEFT_ENCODER_1=2;
			LEFT_ENCODER_2=3;
			FLY_WHEEL_RIGHT_COUNTER=4;
			FLY_WHEEL_LEFT_COUNTER=5;
			INTAKE_LIMIT=6;//hopefully 
	}

}
