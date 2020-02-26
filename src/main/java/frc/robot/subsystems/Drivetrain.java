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
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  private ADXRS450_Gyro gyro;

  private CANSparkMax leftMasterSpark;
  private CANSparkMax leftSlave1Spark;
  private CANSparkMax leftSlave2Spark;
  private SpeedControllerGroup leftMotorGroup;

  private CANSparkMax rightMasterSpark;
  private CANSparkMax rightSlave1Spark;
  private CANSparkMax rightSlave2Spark;
  private SpeedControllerGroup rightMotorGroup;

  private WPI_TalonFX leftMasterFalcon;
  private WPI_TalonFX leftSlaveFalcon;

  private WPI_TalonFX rightMasterFalcon;
  private WPI_TalonFX rightSlaveFalcon;

  private DifferentialDrive drivetrain;

  private CANEncoder leftMasterEncoder;
  private CANEncoder leftSlave1Encoder;
  private CANEncoder leftSlave2Encoder;
  private CANEncoder rightMasterEncoder;
  private CANEncoder rightSlave1Encoder;
  private CANEncoder rightSlave2Encoder;

  private boolean sparkDrive;
  
  public Drivetrain() {

    initSparkDrivetrain();
    //initFalconDrivetrain();

    gyro = new ADXRS450_Gyro();
    zeroGyro();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Gyro", getGyroAngle());
    SmartDashboard.putNumber("Left Master Encoder", getMasterLeftEncoder());
    SmartDashboard.putNumber("Right Master Encoder", getMasterRightEncoder());
  }

  //Config functions
  private void initSparkDrivetrain(){

    //Init motors, speed controller groups, and drivetrain
    leftMasterSpark= new CANSparkMax(LEFT_MASTER_S, MotorType.kBrushless);
    leftSlave1Spark = new CANSparkMax(LEFT_SLAVE_1_S, MotorType.kBrushless);
    leftSlave2Spark = new CANSparkMax(LEFT_SLAVE_2_S, MotorType.kBrushless);
    leftMotorGroup = new SpeedControllerGroup(leftMasterSpark, leftSlave1Spark, leftSlave2Spark);

    rightMasterSpark = new CANSparkMax(RIGHT_MASTER_S, MotorType.kBrushless);
    rightSlave1Spark = new CANSparkMax(RIGHT_SLAVE_1_S, MotorType.kBrushless);
    rightSlave2Spark = new CANSparkMax(RIGHT_SLAVE_2_S, MotorType.kBrushless);
    rightMotorGroup = new SpeedControllerGroup(rightMasterSpark, rightSlave1Spark, rightSlave2Spark);

    drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

    //Set follower mode
    leftSlave1Spark.follow(leftMasterSpark);
    leftSlave2Spark.follow(leftMasterSpark);
    rightSlave1Spark.follow(rightMasterSpark);
    rightSlave2Spark.follow(rightMasterSpark);
    
    //Set brake mode
    leftMasterSpark.setIdleMode(IdleMode.kBrake);
    leftSlave1Spark.setIdleMode(IdleMode.kBrake);
    leftSlave2Spark.setIdleMode(IdleMode.kBrake);
    rightMasterSpark.setIdleMode(IdleMode.kBrake);
    rightSlave1Spark.setIdleMode(IdleMode.kBrake);
    rightSlave2Spark.setIdleMode(IdleMode.kBrake);
    
    //Init encoders
    leftMasterEncoder = leftMasterSpark.getEncoder();
    leftSlave1Encoder = leftSlave1Spark.getEncoder();
    leftSlave2Encoder = leftSlave2Spark.getEncoder();
    rightMasterEncoder = rightMasterSpark.getEncoder();
    rightSlave1Encoder = rightSlave1Spark.getEncoder();
    rightSlave2Encoder = rightSlave2Spark.getEncoder();

    //Config encoders
    // leftMasterEncoder.setInverted(!kMotorInvert);
    // leftSlave1Encoder.setInverted(!kMotorInvert);
    // leftSlave2Encoder.setInverted(!kMotorInvert);
    // rightMasterEncoder.setInverted(kMotorInvert);
    // rightSlave1Encoder.setInverted(kMotorInvert);
    // rightSlave2Encoder.setInverted(kMotorInvert);

    //Tell code what drivetype is used
    sparkDrive = true;

    //Zero encoders
    zeroEncoders();
  }

  private void initFalconDrivetrain(){

    //Init motors, speed controller groups, and drivetrain
    leftMasterFalcon = new WPI_TalonFX(LEFT_MASTER_F);
    leftSlaveFalcon = new WPI_TalonFX(LEFT_SLAVE_F);
    leftMotorGroup = new SpeedControllerGroup(leftMasterFalcon, leftSlaveFalcon);

    rightMasterFalcon = new WPI_TalonFX(RIGHT_MASTER_F);
    rightSlaveFalcon = new WPI_TalonFX(RIGHT_SLAVE_F);
    rightMotorGroup = new SpeedControllerGroup(rightMasterFalcon, rightSlaveFalcon);

    drivetrain = new DifferentialDrive(leftMotorGroup, rightMotorGroup);

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

    //Tell code what drivetype is used
    sparkDrive = false;

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
    if(GEAR.equals("High")) speedCap = kHighGearSpeedCap;

    else speedCap = kLowGearSpeedCap;

    drivetrain.tankDrive(speedCap * DIRECTION_MULTIPLIER * leftJoyY, speedCap * DIRECTION_MULTIPLIER * rightJoyY);   
  }

  public void autoDrive(double leftSpeed, double rightSpeed){

    drivetrain.tankDrive(leftSpeed, rightSpeed);
  }

  public void stopDrive(){

    drivetrain.tankDrive(0, 0);
  }

  //Utility functions
  public void zeroEncoders(){

    if (sparkDrive) {

      leftMasterEncoder.setPosition(0);
      leftSlave1Encoder.setPosition(0);
      leftSlave2Encoder.setPosition(0);
      rightMasterEncoder.setPosition(0);
      rightSlave1Encoder.setPosition(0);
      rightSlave2Encoder.setPosition(0);
    
    } else {

      leftMasterFalcon.setSelectedSensorPosition(0);
      leftSlaveFalcon.setSelectedSensorPosition(0);
      rightMasterFalcon.setSelectedSensorPosition(0);
      rightSlaveFalcon.setSelectedSensorPosition(0);
    
    }
    
  }

  public double[] getLeftEncoders(){

    double[] encoders = new double[2];

    if (sparkDrive) {

      encoders[0] = leftMasterEncoder.getPosition();
      encoders[1] = leftSlave1Encoder.getPosition();
      //encoders[2] = leftSlave2Encoder.getPosition(); //Third encoder excluded to simplify future autodrive structure with falcons

    } else {

      encoders[0] = leftMasterFalcon.getSelectedSensorPosition();
      encoders[1] = leftSlaveFalcon.getSelectedSensorPosition();
    }

    return encoders;
  }

  public double[] getRightEncoders(){

    double[] encoders = new double[2];

    if (sparkDrive) {

      encoders[0] = rightMasterEncoder.getPosition();
      encoders[1] = rightSlave1Encoder.getPosition();
      //encoders[2] = rightSlave2Encoder.getPosition(); //Third encoder excluded to simplify future autodrive structure with falcons

    } else {

      encoders[0] = rightMasterFalcon.getSelectedSensorPosition();
      encoders[1] = rightSlaveFalcon.getSelectedSensorPosition();
    }

    return encoders;
  }

  public double getMasterLeftEncoder(){

    if(sparkDrive) return leftMasterEncoder.getPosition();

    else return leftMasterFalcon.getSelectedSensorPosition();
  }

  public double getMasterRightEncoder(){

    if(sparkDrive) return rightMasterEncoder.getPosition();

    else return rightMasterFalcon.getSelectedSensorPosition();
  }

  public double getGyroAngle(){

    return gyro.getAngle();
  }

  public void zeroGyro(){

    gyro.reset();
  }
  
  public void invertDirection(){

    DIRECTION_MULTIPLIER *= -1;
  }

}
