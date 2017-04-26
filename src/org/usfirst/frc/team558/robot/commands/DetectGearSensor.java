package org.usfirst.frc.team558.robot.commands;

import org.usfirst.frc.team558.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DetectGearSensor extends Command {

    public DetectGearSensor() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.irSensor);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(Robot.irSensor.LowGearSensorRead()){
    		
    		Robot.oi.rumble(.95, .95);
    		
    	}
    	else if (Robot.irSensor.HighGearSensorRead()){
    		Robot.oi.rumble(.35, .35);
    	}
    	else{
    		
    		Robot.oi.rumble(0, 0);
    		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
