/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.ShooterConstants.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX shooterMaster = new WPI_TalonFX(SHOOTER_MASTER);
  private final WPI_TalonFX shooterSlave = new WPI_TalonFX(SHOOTER_SLAVE);
  
  private final WPI_TalonSRX turret = new WPI_TalonSRX(TURRET);

  private final Encoder turretEncoder = new Encoder(TURRET_ENCODER_1, TURRET_ENCODER_2);
  private final DigitalInput turretLimit = new DigitalInput(TURRET_LIMIT_SWITCH);

  private final PIDController turretPID = new PIDController(kP_Turret, kI_Turret, kD_Turret);

  private boolean resetEncoder = false;
 

  public Shooter() {

    //Configure slave
    shooterSlave.follow(shooterMaster);
    shooterSlave.setInverted(InvertType.OpposeMaster);

    //Set up encoder for speed control
    shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxShoot, kTimeoutMsShoot);

    //Configured to calculate the angle around the turret from the limit switch
    //theta (radians) = arclength / radius
    turretEncoder.setDistancePerPulse(-kTurretSprocketDia * 360 / (kTurretEncoderPPR * kTurretGearReduction * kTurretDiskDia/2));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Turret Angle", turretEncoder.getDistance());
    SmartDashboard.putBoolean("Turret Limit", turretLimit.get()); 
  }

  public void shootSingleBall(double speed, double angle) {
    
  }

  public void shootContinuous(double speed, double angle) {
    
  }
}
