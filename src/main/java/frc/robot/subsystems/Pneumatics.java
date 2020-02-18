/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.Constants.PneumaticsConstants.*;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
  
  private Compressor compressor = new Compressor(0);


  public Pneumatics() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void extendIntake() {

  }

  public void retractIntake() {
    
  }

  public void shiftDown() {

  }

  public void shiftUp() {

  }

  public void engagePTO() {

  }
  
}
