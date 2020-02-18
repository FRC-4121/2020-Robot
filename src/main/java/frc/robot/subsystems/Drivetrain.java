/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivetrainConstants.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private CANSparkMax leftMaster;
  private CANSparkMax leftSlave1;
  private CANSparkMax leftSlave2;
  private SpeedControllerGroup leftMotorGroup;

  private CANSparkMax rightMaster;
  private CANSparkMax rightSlave1;
  private CANSparkMax rightSlave2;
  private SpeedControllerGroup rightMotorGroup;

  private DifferentialDrive drivetrain;

  private CANPIDController leftPID;
  private CANPIDController rightPID;

  private CANEncoder leftMasterEncoder;
  private CANEncoder leftSlave1Encoder;
  private CANEncoder leftSlave2Encoder;
  private CANEncoder rightMasterEncoder;
  private CANEncoder rightSlave1Encoder;
  private CANEncoder rightSlave2Encoder;
  
  public Drivetrain() {

    initDrivetrain();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void initDrivetrain(){

    //Init motors, speed controller groups, and drivetrain
    leftMaster = new CANSparkMax(LEFT_MASTER, MotorType.kBrushless);
    leftSlave1 = new CANSparkMax(LEFT_SLAVE_1, MotorType.kBrushless);
    leftSlave2 = new CANSparkMax(LEFT_SLAVE_2, MotorType.kBrushless);
    leftMotorGroup = new SpeedControllerGroup(leftMaster, leftSlave1, leftSlave2);

    rightMaster = new CANSparkMax(RIGHT_MASTER, MotorType.kBrushless);
    rightSlave1 = new CANSparkMax(RIGHT_SLAVE_1, MotorType.kBrushless);
    rightSlave2 = new CANSparkMax(RIGHT_SLAVE_2, MotorType.kBrushless);
    leftMotorGroup = new SpeedControllerGroup(rightMaster, rightSlave1, rightSlave2);

    drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

    //Set follower mode
    leftSlave1.follow(leftMaster);
    leftSlave2.follow(leftMaster);
    rightSlave1.follow(rightMaster);
    rightSlave2.follow(rightMaster);
    
    //Set brake mode
    leftMaster.setIdleMode(IdleMode.kBrake);
    leftSlave1.setIdleMode(IdleMode.kBrake);
    leftSlave2.setIdleMode(IdleMode.kBrake);
    rightMaster.setIdleMode(IdleMode.kBrake);
    rightSlave1.setIdleMode(IdleMode.kBrake);
    rightSlave2.setIdleMode(IdleMode.kBrake);
    
    //Init PID Controllers
    leftPID = leftMaster.getPIDController();
    rightPID = rightMaster.getPIDController();

    //Init encoders
    leftMasterEncoder = leftMaster.getEncoder();
    leftSlave1Encoder = leftSlave1.getEncoder();
    leftSlave2Encoder = leftSlave2.getEncoder();
    rightMasterEncoder = rightMaster.getEncoder();
    rightSlave1Encoder = rightSlave1.getEncoder();
    rightSlave2Encoder = rightSlave2.getEncoder();

    //Config encoders
    leftMasterEncoder.setInverted(!kMotorInvert);
    leftSlave1Encoder.setInverted(!kMotorInvert);
    leftSlave2Encoder.setInverted(!kMotorInvert);
    rightMasterEncoder.setInverted(kMotorInvert);
    rightSlave1Encoder.setInverted(kMotorInvert);
    rightSlave2Encoder.setInverted(kMotorInvert);

    //Zero encoders
    leftMasterEncoder.setPosition(0);
    leftSlave1Encoder.setPosition(0);
    leftSlave2Encoder.setPosition(0);
    rightMasterEncoder.setPosition(0);
    rightSlave1Encoder.setPosition(0);
    rightSlave2Encoder.setPosition(0);

  }

  public void drive(double leftJoyY, double rightJoyY) {

    if(DIRECTION_MULTIPLIER == 1){

      drivetrain.tankDrive(leftJoyY, rightJoyY);   
    
    } else {

      drivetrain.tankDrive(-leftJoyY, -rightJoyY);
    }

  }

  public void autoDrive(double leftSpeed, double rightSpeed){
    
  }
  
}
