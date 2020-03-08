/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Constants.*;
import static frc.robot.Constants.ShooterConstants.*;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.NetworkTableQuerier;
import frc.robot.extraClasses.PIDControl;


public class AutoTurret extends CommandBase {

  //Declare class variables
  private Turret myTurret;
  private NetworkTableQuerier myNTQuerier;

  private boolean stopAutoTurret;
  private boolean overrideAutoTurret;
  private boolean foundTarget;
  private boolean targetLock;
  private double targetOffset;

  private double turretCorrection;

  //Declare PID controller
  PIDControl myPID;


  /**
   * Creates a new AutoTurret.
   */
  public AutoTurret(Turret turret, NetworkTableQuerier ntquerier) {

    // Assign variables
    myTurret = turret;
    myNTQuerier = ntquerier;

    // Declare subsystem dependencies
    addRequirements(myTurret);

  }

  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // Initialize flags
    stopAutoTurret = false;
    overrideAutoTurret = false;


    turretCorrection = 0;

    // Initialize PID
    myPID = new PIDControl(kP_Turret, kI_Turret, kD_Turret);

  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Get status of flags/NT data
    foundTarget = myNTQuerier.getFoundTapeFlag();
    targetLock = myNTQuerier.getTargetLockFlag();
    targetOffset = myNTQuerier.getTapeOffset();

    //Need to update the override flag here (get value from gamepad switch)

    // Maintain target lock
    //If the driver has not overridden for manual control
    if (!overrideAutoTurret){

      //If the camera has a target in sights
      if (foundTarget){

        //If the target is not centered in the screen
        if (!targetLock){

          //If the turret is in a safe operating range for the physical constraints of the robot
          if(myTurret.getTurretAngle() < kTurretMaxAngle && myTurret.getTurretAngle() > kTurretMinAngle){

            //Run PID control on the offset of the target in the image until target is locked
            turretCorrection = myPID.run(targetOffset, 0);
            myTurret.rotateTurret(turretCorrection);
          
          }
          else
          {
            //If we are at the bounds of the turret, we need to figure out how this will work.
          }
        
        }
        else
        {
          //If target is locked, stop the motor
          myTurret.stopTurret();
        }

      }
      else
      {
        //If the camera does not see a target, we need to figure out how to write the code for this
      }

    }
    else
    {
      //Manual control will pull data from the potentiometer on the oi board.  For now, we will access a joystick as the 'potentiometer'
    }

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return stopAutoTurret;
    
  }
}
