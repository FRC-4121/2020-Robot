/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.ShooterConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.extraClasses.PIDControl;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX shooterMaster = new WPI_TalonFX(SHOOTER_MASTER);
  private final WPI_TalonFX shooterSlave = new WPI_TalonFX(SHOOTER_SLAVE);
  
  private final WPI_TalonSRX turret = new WPI_TalonSRX(TURRET);

  private final Encoder turretEncoder = new Encoder(TURRET_ENCODER_1, TURRET_ENCODER_2);
  private final DigitalInput turretLimit = new DigitalInput(TURRET_LIMIT_SWITCH);

  private final PIDControl turretAnglePID = new PIDControl(kP_Turret, kI_Turret, kD_Turret);

  private boolean resetEncoder = false;

  //for testing
  private double save_p = kP_Shoot;
  private double save_i = kI_Shoot;
  private double save_d = kD_Shoot;
  private double save_f = kF_Shoot;
  private double speed;
 

  public Shooter() {

    //Configure slave
    shooterSlave.follow(shooterMaster);
    shooterSlave.setInverted(InvertType.OpposeMaster);

    //Set up encoder for speed control
    shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxShoot, kTimeoutMsShoot);
    
    //Config speed PID
		shooterMaster.config_kP(kPIDLoopIdxShoot, kP_Shoot, kTimeoutMsShoot);
		shooterMaster.config_kI(kPIDLoopIdxShoot, kI_Shoot, kTimeoutMsShoot);
    shooterMaster.config_kD(kPIDLoopIdxShoot, kD_Shoot, kTimeoutMsShoot);
    shooterMaster.config_kF(kPIDLoopIdxShoot, kF_Shoot, kTimeoutMsShoot);
    
    //for testing, we put PID tuning in the smart dash
    SmartDashboard.putNumber("P Shoot", kP_Shoot);
    SmartDashboard.putNumber("I Shoot", kI_Shoot);
    SmartDashboard.putNumber("D Shoot", kD_Shoot);
    SmartDashboard.putNumber("F Shoot", kF_Shoot);
    SmartDashboard.putNumber("Speed", 0);

    //Configured to calculate the angle around the turret from the limit switch
    //theta (radians) = arclength / radius
    turretEncoder.setDistancePerPulse(-kTurretSprocketDia * 360 / (kTurretEncoderPPR * kTurretGearReduction * kTurretDiskDia/2));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Turret Angle", turretEncoder.getDistance());
    SmartDashboard.putBoolean("Turret Limit", turretLimit.get()); 
    SmartDashboard.putNumber("Shooter RPM", getShooterRPM());
    SmartDashboard.putNumber("Shooter Speed", getShooterSpeed());
    
    if (turretLimit.get() == false)
    {
      //...and the reset flag is false...
      if(!resetEncoder)
      {
        //...reset the encoder and lock
        turretEncoder.reset();
        resetEncoder = true;
      }
    }
    else //if the limit switch moves away, make it reset the next time it comes down.
    {
      resetEncoder = false;
    }

    //For testing we grab the PID from the smart dash
    double p = SmartDashboard.getNumber("P Shoot", save_p);
    double i = SmartDashboard.getNumber("I Shoot", save_i);
    double d = SmartDashboard.getNumber("D Shoot", save_d);
    double f = SmartDashboard.getNumber("F Shoot", save_f);
    speed = SmartDashboard.getNumber("Speed", kShooterSpeed);
    
    if(p != save_p) {
      shooterMaster.config_kP(kPIDLoopIdxShoot, p, kTimeoutMsShoot);
      save_p = p;
    }
    if(i != save_i) {
      shooterMaster.config_kI(kPIDLoopIdxShoot, i, kTimeoutMsShoot);
      save_i = i;
    }
    if(d != save_d) {
      shooterMaster.config_kD(kPIDLoopIdxShoot, d, kTimeoutMsShoot);
      save_d = d;
    }
    if(f != save_f) {
      shooterMaster.config_kF(kPIDLoopIdxShoot, f, kTimeoutMsShoot);
      save_f = f;
    }

    //Warning - this will run all the time!!!
    shooterMaster.set(ControlMode.Velocity, speed);
  }

  public void shoot(double speed){

    shooterMaster.set(ControlMode.Velocity, speed);
  }

  public void rotateTurret(double speed){

    turret.set(speed);
  }

  public void stopTurret(){

    turret.set(0);
  }

  public void stopShooter(){

    shooterMaster.set(0);
  }

  public boolean getTurretLimit(){

    return turretLimit.get();
  }

  public double getTurretAngle(){

    return turretEncoder.getDistance();
  }

  public double getShooterRPM(){

    return shooterMaster.getSelectedSensorVelocity() * 10 * 60 / kTalonFXPPR;
  }

  public double getShooterSpeed(){

    return speed;
  }

}
