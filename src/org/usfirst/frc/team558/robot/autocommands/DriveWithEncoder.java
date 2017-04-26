package org.usfirst.frc.team558.robot.autocommands;

import org.usfirst.frc.team558.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithEncoder extends Command {

	private double mTime;
	private double mSpeed;
	private double mDistance;
	private double mKp;
	
	private double error;
	private double tolerance = .7;
	private double pidSpeed;
	
	
	 /**
     * Drive robot based off set encoder distance
     * 
     * @param aDistance
     *            Distance for robot to travel in inches
     * @param aSpeed
     *            Max speed for robot to travel -1 to 1
     * @param aTime
     *            Max time till timed out in seconds
     * @param aKp
     * 			  Proportional constant for desired distance
     */
    
    public DriveWithEncoder(double aDistance, double aSpeed, double aTime, double aKp) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        
        this.mDistance = aDistance;
        this.mSpeed = aSpeed;
        this.mTime = aTime;
        this.mKp = aKp;
        
        setTimeout(mTime);
        
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	error = Math.abs(mDistance - Robot.driveTrain.GetAverageEncoderDistance());
    	
    	if (mKp * error >= mSpeed){
    		pidSpeed = mSpeed;
    	}
    	else {
    		pidSpeed = mKp * error;
    	}

  
    	if (Robot.driveTrain.GetAverageEncoderDistance() < mDistance)
        {
    		Robot.driveTrain.drive(-pidSpeed, -pidSpeed);
        }
        else
        {
        	Robot.driveTrain.drive(pidSpeed, pidSpeed);
        }
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return ((Math.abs(error) <= tolerance) || isTimedOut());
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.driveTrain.drive(0, 0);
    	Robot.driveTrain.resetEncoders();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
