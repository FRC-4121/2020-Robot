/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  
  private WPI_TalonSRX intakeMotor = new WPI_TalonSRX(INTAKE);
  
  public Intake() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void runIntake(double speed){

    intakeMotor.set(speed);
  }

  public void stopIntake(){

    intakeMotor.set(0);
  }

}
