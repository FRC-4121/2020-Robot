/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.DrivetrainConstants.*;
import static frc.robot.Constants.PneumaticsConstants.*;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private ADXRS450_Gyro gyro;

  private WPI_TalonFX leftMasterFalcon;
  private WPI_TalonFX leftSlaveFalcon;

  private WPI_TalonFX rightMasterFalcon;
  private WPI_TalonFX rightSlaveFalcon;

  private SpeedControllerGroup leftMotorGroup;
  private SpeedControllerGroup rightMotorGroup;

  private DifferentialDrive drivetrain;

  
  public Drivetrain() {

    initFalconDrivetrain();

    gyro = new ADXRS450_Gyro();
    SmartDashboard.putNumber("Zero Gyro", 0);
    gyro.calibrate();
    zeroGyro();

    SmartDashboard.putNumber("Zero Encoders", 0);
    zeroEncoders();
  }

  @Override
  public void periodic() {

    // Put info on the dashboard
    SmartDashboard.putNumber("Gyro", getGyroAngle());
    SmartDashboard.putNumber("Left Master Encoder", getMasterLeftEncoderPosition());
    SmartDashboard.putNumber("Right Master Encoder", getMasterRightEncoderPosition());

    // Zero the gyro on driver command
    double zeroGyro = SmartDashboard.getNumber("Zero Gyro", 0);
    if (zeroGyro == 1)
    {
      SmartDashboard.putNumber("Zero Gyro", 0);
      zeroGyro();
    }

    // Zero the encoders on driver command
    double zeroEncoders = SmartDashboard.getNumber("Zero Encoders", 0);
    if (zeroEncoders == 1)
    {
      SmartDashboard.putNumber("Zero Encoders", 0);
      zeroEncoders();
    }


  }

  //Config functions
  private void initFalconDrivetrain(){

    //Init motors, speed controller groups, and drivetrain
    leftMasterFalcon = new WPI_TalonFX(LEFT_MASTER_F);
    leftSlaveFalcon = new WPI_TalonFX(LEFT_SLAVE_F);
    leftMotorGroup = new SpeedControllerGroup(leftMasterFalcon, leftSlaveFalcon);

    rightMasterFalcon = new WPI_TalonFX(RIGHT_MASTER_F);
    rightSlaveFalcon = new WPI_TalonFX(RIGHT_SLAVE_F);
    rightMotorGroup = new SpeedControllerGroup(rightMasterFalcon, rightSlaveFalcon);

    drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);
    drivetrain.setRightSideInverted(false);

    //Set follower mode
    leftSlaveFalcon.follow(leftMasterFalcon);
    rightSlaveFalcon.follow(rightMasterFalcon);

    //Set brake mode
    leftMasterFalcon.setNeutralMode(NeutralMode.Brake);
    leftSlaveFalcon.setNeutralMode(NeutralMode.Brake);
    rightMasterFalcon.setNeutralMode(NeutralMode.Brake);
    rightSlaveFalcon.setNeutralMode(NeutralMode.Brake);

    //Config encoders
    leftMasterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxDrive, kTimeoutMsDrive);
    leftSlaveFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxDrive, kTimeoutMsDrive);
    rightMasterFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxDrive, kTimeoutMsDrive);
    rightSlaveFalcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kPIDLoopIdxDrive, kTimeoutMsDrive);

    //Invert appropriately
    leftMasterFalcon.setInverted(!kMotorInvert);
    leftSlaveFalcon.setInverted(!kMotorInvert);
    rightMasterFalcon.setInverted(kMotorInvert);
    rightSlaveFalcon.setInverted(kMotorInvert);

    //Zero encoders
    leftMasterFalcon.setSelectedSensorPosition(0);
    leftSlaveFalcon.setSelectedSensorPosition(0);
    rightMasterFalcon.setSelectedSensorPosition(0);
    rightSlaveFalcon.setSelectedSensorPosition(0);
    
  }

  //Main drive functions
  public void drive(double leftJoyY, double rightJoyY) {

    //Configure software-based voltage protection measure (will require testing to determine optimal values)
    double speedCap = 1.0;
    if(GEAR.equals("High")){// 
      
      speedCap = kHighGearSpeedCap;

    }
    else{
      
      speedCap = kLowGearSpeedCap;

    }

    if(DIRECTION_MULTIPLIER == 1){

      drivetrain.tankDrive(speedCap * DIRECTION_MULTIPLIER * leftJoyY, speedCap * DIRECTION_MULTIPLIER * rightJoyY);   
    
    }
    else{

      drivetrain.tankDrive(speedCap * DIRECTION_MULTIPLIER * rightJoyY, speedCap * DIRECTION_MULTIPLIER * leftJoyY); 
    
    }
  }


  /**
   * Run drivetrain during autonomous
   * @param leftSpeed
   * @param rightSpeed
   */
  public void autoDrive(double leftSpeed, double rightSpeed){

    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }


  /**
   * Stop the drive train
   */
  public void stopDrive(){

    drivetrain.tankDrive(0, 0);
  }


  /**
   * Zero the encoders
   */
  public void zeroEncoders(){

    leftMasterFalcon.setSelectedSensorPosition(0);
    leftSlaveFalcon.setSelectedSensorPosition(0);
    rightMasterFalcon.setSelectedSensorPosition(0);
    rightSlaveFalcon.setSelectedSensorPosition(0);

  }


  /**
   * Get position of all left encoders
   * @return
   */
  public double[] getLeftEncoders(){

    double[] encoders = new double[2];

    encoders[0] = leftMasterFalcon.getSelectedSensorPosition();
    encoders[1] = leftSlaveFalcon.getSelectedSensorPosition();
    
    return encoders;

  }


  /**
   * Get position of all right encoders
   * @return
   */
  public double[] getRightEncoders(){

    double[] encoders = new double[2];

    encoders[0] = rightMasterFalcon.getSelectedSensorPosition();
    encoders[1] = rightSlaveFalcon.getSelectedSensorPosition();
    
    return encoders;

  }


  /**
   * Get position of left master encoder
   * @return
   */
  public double getMasterLeftEncoderPosition(){

    return leftMasterFalcon.getSelectedSensorPosition();

  }


  /**
   * Get position of right master encoder
   * @return
   */
  public double getMasterRightEncoderPosition(){

    return rightMasterFalcon.getSelectedSensorPosition();

  }


  /**
   * Get current gyro angle
   * @return
   */
  public double getGyroAngle(){

    double correctedGyro = gyro.getAngle() % 360.0;
    return correctedGyro;

  }


  /**
   * Reset current gyro heading to zero
   */
  public void zeroGyro(){

    gyro.reset();

  }
  

  /**
   * Invert the direction of driving
   */
  public void invertDirection(){

    DIRECTION_MULTIPLIER *= -1;

  }

}
