/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.extraClasses.Ballistics;
import frc.robot.extraClasses.PIDControl;
import frc.robot.subsystems.NetworkTableQuerier;
import frc.robot.subsystems.Shooter;

import static frc.robot.Constants.ShooterConstants.*;

public class AutoShooter extends CommandBase {
  
  private Shooter shooter;
  private NetworkTableQuerier ntQuerier;
  private Ballistics ballistics;

  private PIDControl myPID;
  private double distance;
  private boolean targetLock;
  private double targetSpeed;
  private double targetSpeedCorrected;
  private double speed;
  private double speedCorrection;

  private boolean runSpeedControl = true;
  
  public AutoShooter(Shooter shoot, NetworkTableQuerier querier) {

    shooter = shoot;
    ntQuerier = querier;
    ballistics = new Ballistics(98.25, 22.5, 5, 6380, 6, .227);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    myPID = new PIDControl(kP_Shoot, kI_Shoot, kD_Shoot);
    speed = .75;
    speedCorrection = 0;

    targetSpeed = 1.0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(runSpeedControl){

      targetLock = ntQuerier.getTargetLockFlag();

      if(targetLock){
        
        distance = ntQuerier.getTapeDistance();
        targetSpeed = ballistics.queryBallisticsTable(distance)[2];
        targetSpeedCorrected = targetSpeed * kSpeedCorrectionFactor;
        SmartDashboard.putNumber("Ballistics Speed", targetSpeed);

        //speedCorrection = myPID.run(shooter.getShooterRPM() / kShooterMaxRPM, -targetSpeedCorrected);

        shooter.shoot(-targetSpeedCorrected);
      }
      else {

        shooter.shoot(-speed);
      }

    }

    SmartDashboard.putNumber("Shooter Speed", shooter.getShooterSpeed());
    SmartDashboard.putNumber("Shooter RPM", shooter.getShooterRPM());
    SmartDashboard.putNumber("Speed Correct", speedCorrection);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void stopAutoSpeed(){


  }
}
