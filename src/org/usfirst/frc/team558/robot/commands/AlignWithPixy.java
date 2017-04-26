package org.usfirst.frc.team558.robot.commands;

import org.usfirst.frc.team558.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignWithPixy extends Command {

	public double pixyCenter;
	
    public AlignWithPixy() {
    	requires(Robot.driveTrain);
    	requires(Robot.pixyCam);
    	
    	setTimeout(2);
    	
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.pixyCam.read();
    	pixyCenter = Robot.pixyCam.getLastOffset();
    	
    	if(pixyCenter > 160){
    		
    		Robot.driveTrain.drive(-.25, .25);
    		
    	}
    	else{
    		
    		Robot.driveTrain.drive(.25, -.25);
    		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        
        return (isTimedOut()); //|| pixyCenter == 160);
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.driveTrain.initDefaultCommand();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
