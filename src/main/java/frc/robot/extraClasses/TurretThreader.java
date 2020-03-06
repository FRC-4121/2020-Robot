/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.extraClasses;

public class TurretThreader implements Runnable {
  
  public TurretThreader() {

  }

  public void run(){
        
    while(true){

         
    }
  }

public void start(){

    Thread ntThread = new Thread(this);
    ntThread.start();
}

  public void periodic() {
    // This method will be called once per scheduler run
  }
}
