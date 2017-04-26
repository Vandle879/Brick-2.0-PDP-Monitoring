package org.usfirst.frc.team558.robot.subsystems;

import edu.wpi.first.wpilibj.*;

import java.util.Arrays;

public class PDPController {
	
	public PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	
	public double compressorCurrentLimit = 120;
	public double gearIntakeCurrentLimit = 120;
	public double drivetrainCurrentLimit = 120;
	private double currentError;
	private double cKp = .02;
	private double limiter;
	
	private double timeError;
	private double startTime;
	private double presentTime;
	private double tKp = 0.1;
	
	
	private int index = 0;
	private double[] pdpData;

	
	public PDPController(){
		pdpData = new double[25];
		startTime = Timer.getFPGATimestamp();
	
	}

	public double GetTotalCurrent(){
		return pdp.getTotalCurrent();
	}

	
	public double GetChannelCurrent(int channel){
		return pdp.getCurrent(channel);
	}
	
	public void StorePDPData(){
		if (index > 24){
			index = 0;
		}
		pdpData[index] = this.GetTotalCurrent();
		index += 1;
	}
	
	public double GetAverageTotalCurrent(){
		double sum = Arrays.stream(pdpData).sum();
		double length = pdpData.length;
		return (sum/length);
	}
	
	
	//Sends signal to compressor to shut down if current limit is exceeded
	public boolean CompressorFlag(){
		return (this.GetAverageTotalCurrent() < this.compressorCurrentLimit);
	}
	
	

	//Sends signal to intake to shut down if current limit is exceeded
	public boolean GearIntakeFlag(){
		return (this.GetAverageTotalCurrent() < this.gearIntakeCurrentLimit);
	}
	
	
	//Reduces allowable power to drivetrain if current limit is exceeded
	//Scales with amount over limit
	/*public double DrivetrainLimiter(){
		if (this.GetAverageTotalCurrent() > this.drivetrainCurrentLimit){
			currentError = this.GetAverageTotalCurrent() - this.drivetrainCurrentLimit;
			limiter = 1 - (currentError * cKp);
		}
		else{
			limiter = 1;
		}
		
		return limiter;
		
	}
	*/

	//Reduces allowable power to drivetrain if current limit is exceeded
	//Scales with amount over limit and time since limit was surpased
	public double DrivetrainLimiterWithTimeScale(){
		if (this.GetAverageTotalCurrent() > this.drivetrainCurrentLimit){
			presentTime = Timer.getFPGATimestamp();
			timeError = Math.abs(presentTime - startTime);
			
			currentError = Math.abs(this.GetAverageTotalCurrent() - this.drivetrainCurrentLimit);
			limiter = 1 - (currentError * cKp) - (timeError * tKp);
		}
		else{
			startTime = Timer.getFPGATimestamp();
			limiter = 1;
		}
		
		return limiter;
		
	}

	
	
	//*** Methods for monitoring class***
	public double GetTimeError(){
		return this.timeError;
	}
	
	public double GetCurrentError(){
		return this.currentError;
	}
		
	public double GetStartTime(){
		return this.startTime;
	}
	
	public double GetPresentTime(){
		return this.presentTime;
	}
	
	public double GetIndex(){
		return this.index;
	}
	
	
	
	
}