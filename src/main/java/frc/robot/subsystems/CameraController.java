/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraController extends SubsystemBase {

  private UsbCamera intakeCamera;
  private UsbCamera reverseCamera;
  private UsbCamera shooterCamera;
  private CameraServer camServer;
  private VideoSink test;

  private boolean forwardCamera = true;
  
  public CameraController() {


    shooterCamera = CameraServer.getInstance().startAutomaticCapture("Shooter View", 2);
    camServer = CameraServer.getInstance();

    intakeCamera = new UsbCamera("Intake View", 0);
    intakeCamera.setResolution(160, 120);
    intakeCamera.setFPS(15);
    intakeCamera.setBrightness(50);

    reverseCamera = new UsbCamera("Reverse Drive View", 1);
    reverseCamera.setResolution(160, 120);
    reverseCamera.setFPS(15);
    reverseCamera.setBrightness(50);

    test = camServer.addSwitchedCamera("Switched");

    shooterCamera.setResolution(160, 120);
    shooterCamera.setFPS(15);
    shooterCamera.setBrightness(50);


    //intakeCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
    //reverseCamera.setConnectionStrategy(ConnectionStrategy.kKeepOpen);

    test.setSource(intakeCamera);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void switchCamera(){

    forwardCamera = !forwardCamera;
    if (forwardCamera)
    {
      test.setSource(intakeCamera);
    }
    else
    {
      test.setSource(reverseCamera);
    }
  }
}
