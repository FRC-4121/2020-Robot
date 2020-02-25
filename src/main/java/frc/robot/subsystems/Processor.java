/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.ProcessorConstants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Processor extends SubsystemBase {
  
  private CANSparkMax processorMain = new CANSparkMax(PROCESSOR_MAIN, MotorType.kBrushless);
  private WPI_TalonSRX processorLock = new WPI_TalonSRX(PROCESSOR_END);
  
  public Processor() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //invertDirection = false: normal intake->shooter direction
  public void runProcessor(boolean invertDirection){

    if(!invertDirection) processorMain.set(kProcessorSpeed);

    else processorMain.set(-kProcessorSpeed);
  }

  public void unlockProcessor(){

    processorLock.set(kEndProcessorSpeed);
  }

  public void lockProcessor(){

    processorLock.set(kLockSpeed);
  }

  public void stopProcessor(){

    processorMain.set(0);
    processorLock.set(0);
  }
}
