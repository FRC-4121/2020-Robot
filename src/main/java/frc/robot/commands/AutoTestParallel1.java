/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NetworkTableQuerier;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Processor;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoTestParallel1 extends ParallelCommandGroup {
  /**
   * Creates a new AutoTestParallel1.
   */
  public AutoTestParallel1(Drivetrain drive, Pneumatics shift, Turret turret, Processor processor, Shooter shooter, NetworkTableQuerier ntables) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(/*new ControlShooterSpeed(shooter, ntables), */new AutoTestGroup2(drive, shift, turret, processor, ntables));
  }
}
