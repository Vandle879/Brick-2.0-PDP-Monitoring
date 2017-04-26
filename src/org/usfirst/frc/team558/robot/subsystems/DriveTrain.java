package org.usfirst.frc.team558.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team558.robot.Robot;
import org.usfirst.frc.team558.robot.RobotMap;
import org.usfirst.frc.team558.robot.commands.ElmCityDrive;
import edu.wpi.first.wpilibj.*;

import com.ctre.*;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	VictorSP leftDriveMaster = new VictorSP(0);
	VictorSP leftDriveSlave1 = new VictorSP(1);
	VictorSP rightDriveMaster = new VictorSP(2);
	VictorSP rightDriveSlave1 = new VictorSP(3);

	public boolean currentLimit;

	
	public DriveTrain(){
		

	}
	
	public void drive(double leftPower, double rightPower){
		this.leftDriveMaster.set(-leftPower * Robot.pdpController.DrivetrainLimiterWithTimeScale());
		this.leftDriveSlave1.set(-leftPower * Robot.pdpController.DrivetrainLimiterWithTimeScale());
		
		this.rightDriveMaster.set(rightPower * Robot.pdpController.DrivetrainLimiterWithTimeScale());
		this.rightDriveSlave1.set(rightPower * Robot.pdpController.DrivetrainLimiterWithTimeScale());
	}
   
    public void initDefaultCommand() {
       setDefaultCommand(new ElmCityDrive());
    }
    
    public double GetLeftEncoder(){
    	return this.leftDriveMaster.getPosition();
    }
    
    public double GetRightEncoder(){
    	return this.rightDriveMaster.getPosition();
    }
    
    public double GetAverageEncoderDistance(){
    	return ((this.leftDriveMaster.getPosition() + this.rightDriveMaster.getPosition())/2);
    }
    
    public void resetEncoders() {
    	this.leftDriveMaster.setPosition(0.0);
    	this.rightDriveMaster.setPosition(0.0);
    }
    
    public double GetLeftDrive(){
    	
    	return this.leftDriveMaster.get();
    	
    }
    
    public double GetRightDrive(){
    	
    	return this.rightDriveMaster.get();
    	
    }
    
     
}

