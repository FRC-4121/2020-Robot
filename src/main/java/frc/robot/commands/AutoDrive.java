/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.extraClasses.PIDControl;
import static frc.robot.Constants.DrivetrainConstants.*;
import static frc.robot.Constants.PneumaticsConstants.*;

public class AutoDrive extends CommandBase {
  
  // Declare class variables
  private final Drivetrain drivetrain;

  double targetDistance;
  double distanceTraveled;
	double direction; //-1 = Reverse, +1 = Forward (reverse is for gear forward is for shooting)
	double targetAngle;  //drive angle
	double stopTime;  //timeout time
	double angleCorrection, angleError;
	double startTime;
	
	PIDControl pidControl;

	public Timer timer = new Timer();
	
	public double leftEncoderStart;
	public double rightEncoderStart;


  /**
   * Class constructor
   * @param drive
   */
  public AutoDrive(Drivetrain drive) {
    
    drivetrain = drive;

    //Use addRequirements() to require any subsystems for the command
    addRequirements(drivetrain);
                
    //Set up PID control
    pidControl = new PIDControl(kP_Straight, kI_Straight, kD_Straight);
    
  }

  /**
   * Called when the command is initially scheduled
   */
  @Override
  public void initialize() {

    distanceTraveled = 0.0;
    timer.start();
    startTime= timer.get();
    leftEncoderStart = drivetrain.getMasterLeftEncoderPosition();
    rightEncoderStart = drivetrain.getMasterRightEncoderPosition();
    angleCorrection = 0;
    angleError = 0;

  }


  /**
   * Called every time the scheduler runs while the command is scheduled
   */
  @Override
  public void execute() {

    angleCorrection = pidControl.run(drivetrain.getGyroAngle(), targetAngle);
    //angleError = drivetrain.getGyroAngle()-targetAngle;
    //angleCorrection = kP_Straight*angleError;
    drivetrain.autoDrive(direction*kAutoDriveSpeed + angleCorrection, direction*kAutoDriveSpeed - angleCorrection);    	    	

  }


  /**
   * Called once the command ends or is interrupted
   */
  @Override
  public void end(boolean interrupted) {

    drivetrain.stopDrive();

  }


  /**
   * Returns true when the command should end
   */
  @Override
  public boolean isFinished() {

    boolean thereYet = false;
 
    //Check elapsed time
    if(stopTime<=timer.get()-startTime)
    {
      
      //Too much time has elapsed.  Stop this command.
      thereYet = true;
      
    }
    else
    {
          
      double totalRotationsRight = Math.abs((drivetrain.getMasterRightEncoderPosition() - rightEncoderStart)) / kFalconUnitsPerRev;
      double totalRotationsLeft = Math.abs((drivetrain.getMasterLeftEncoderPosition() - leftEncoderStart)) / kFalconUnitsPerRev;
      if (GEAR.equals("High")){
        distanceTraveled = (kWheelDiameter*Math.PI*((totalRotationsRight+totalRotationsLeft)/2))/ kHighGearRatio;
      }
      else{
        distanceTraveled = (kWheelDiameter*Math.PI*((totalRotationsRight+totalRotationsLeft)/2))/ kLowGearRatio;
      }
      
      if (targetDistance <= distanceTraveled)
      {
        
        //Robot has reached its destination.  Stop this command
        thereYet = true;
        
      }
    
    }
    
    return thereYet;

  }


  /**
   * Set the parameters 
   */
  public void setParams(double dis, double dir, double ang, double time){

    //Set local variables
    targetDistance = dis;
    direction = dir;
    targetAngle = ang;
    stopTime = time;

  }
}
