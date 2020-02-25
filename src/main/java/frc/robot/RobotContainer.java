/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.*;
import static frc.robot.Constants.ProcessorConstants.*;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.extraClasses.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  //Driver controllers
  private final XboxController xbox = new XboxController(XBOX_PORT);
  private final Joystick launchpad = new Joystick(LAUNCHPAD_PORT);
  private final Joystick testingJoystick = new Joystick(TEST_JOYSTICK_PORT);  

  //Subsystems
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Processor processor = new Processor();
  private final Pneumatics pneumatics = new Pneumatics();
  private final NetworkTableQuerier ntables = new NetworkTableQuerier();

  //Commands
  //Driving
  private final AutoDrive autoCommand = new AutoDrive(drivetrain);
  private final DriveWithJoysticks driveCommand = new DriveWithJoysticks(drivetrain, xbox);
  //Pneumatics
  private final Shift shift = new Shift(pneumatics);
  private final OperateArm operateIntakeArm = new OperateArm(pneumatics);
  private final OperatePTO operatePTO = new OperatePTO(pneumatics);
  //Intake
  private final RunIntake in = new RunIntake(intake, kIntakeSpeed);
  private final RunIntake out = new RunIntake(intake, kOuttakeSpeed);
  //Processor
  private final RunProcessor runProcessor = new RunProcessor(processor, false);
  private final RunProcessor invertProcessor = new RunProcessor(processor, true);
  //Shooter

  //Climber


  //Buttons
  //Driving
  private JoystickButton invertDirectionButton = new JoystickButton(xbox, 6);
  //Pneumatics
  private JoystickButton shiftButton = new JoystickButton(xbox, 5);
  private JoystickButton intakeArmButton = new JoystickButton(testingJoystick, 1);
  private JoystickButton PTOButton = new JoystickButton(testingJoystick, 2);
  //Intake
  private JoystickButton inButton = new JoystickButton(testingJoystick, 3);
  private JoystickButton outButton = new JoystickButton(testingJoystick, 4);
  //Processor
  private JoystickButton runProcButton = new JoystickButton(testingJoystick, 5);
  private JoystickButton invertProcessorButton = new JoystickButton(testingJoystick, 6);
  //Shooter

  //Climber


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    
    //Configure default commands
    configureDefaultCommands();

    // Configure the button bindings
    configureButtonBindings();
  }

  //For subsystem default commands (driving, etc.)
  private void configureDefaultCommands(){

    //Drivetrain -> drive with xbox joysticks
    drivetrain.setDefaultCommand(driveCommand);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    //Driving
    invertDirectionButton.whenPressed(new InstantCommand(drivetrain::invertDirection, drivetrain));
    //Pneumatics
    shiftButton.whenPressed(shift);
    intakeArmButton.whenPressed(operateIntakeArm);
    PTOButton.whenPressed(operatePTO);
    //Intake
    inButton.whileHeld(in);
    outButton.whileHeld(out);
    inButton.whenReleased(new InstantCommand(intake::stopIntake, intake));
    outButton.whenReleased(new InstantCommand(intake::stopIntake, intake));
    //Processor
    runProcButton.whileHeld(runProcessor);
    invertProcessorButton.whileHeld(invertProcessor);
    runProcButton.whenReleased(new InstantCommand(processor::stopProcessor, processor));
    invertProcessorButton.whenReleased(new InstantCommand(processor::stopProcessor, processor));
    //Shooter

    //Climber

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand;
  }
}
