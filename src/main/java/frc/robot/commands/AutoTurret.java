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

    // Initialize PID
    myPID = new PIDControl(kP_Turret, kI_Turret, kD_Turret);

  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Get status of flags
    foundTarget = myNTQuerier.getFoundTapeFlag();
    targetLock = myNTQuerier.getTargetLockFlag();
    targetOffset = myNTQuerier.getTapeOffset();

    // Maintain target lock
    if (!overrideAutoTurret){

      if (foundTarget){

        if (!targetLock){

          
        }

      }

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
