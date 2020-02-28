/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

public final class Constants {

    //Spark Max IDs (must be unique, may range from 1+)
    //With NEOs
    public static final int LEFT_MASTER_S = 4;
    public static final int LEFT_SLAVE_1_S = 5;
    public static final int LEFT_SLAVE_2_S = 6;
    public static final int RIGHT_MASTER_S = 1;
    public static final int RIGHT_SLAVE_1_S = 2;
    public static final int RIGHT_SLAVE_2_S = 3;
    public static final int PROCESSOR_MAIN = 7;

    //Without NEOs
    public static final int HOOD = -1;//??
    public static final int HOOK = 5;

    //Talon SRX and FX IDs (must be unique, may range from 0+)
    public static final int LEFT_MASTER_F = -1;
    public static final int LEFT_SLAVE_F = -1;
    public static final int RIGHT_MASTER_F = -1;
    public static final int RIGHT_SLAVE_F = -1;
    public static final int INTAKE = 3;
    public static final int PROCESSOR_END = 4;
    public static final int TURRET = 2;
    public static final int SHOOTER_MASTER = 0;
    public static final int SHOOTER_SLAVE = -1;

    //Drive control port IDs
    public static final int XBOX_PORT = 0;
    public static final int LAUNCHPAD_PORT = 1;
    public static final int TEST_JOYSTICK_PORT = 2;

    //DigitalInput port IDs
    public static final int TURRET_ENCODER_1 = 0;
    public static final int TURRET_ENCODER_2 = 1;
    public static final int TURRET_LIMIT_SWITCH  = 2;
    public static final int PROCESSOR_INDEX_1 = 3;
    public static final int PROCESSOR_INDEX_2 = 4;


    public static class DrivetrainConstants {

        public static final boolean kMotorInvert = true;//True -> right side motors are inverted
        public static final int kPIDLoopIdxDrive = 0;
        public static final int kTimeoutMsDrive = 20;
        public static final double kTalonFXPPR = 2048;
        public static final double kWheelDiameter = 6.0;
        public static final double kLowGearSpeedCap = 1.0;//In case full speed draws excessive power, these are an emergency measure
        public static final double kHighGearSpeedCap = 1.0;

        public static int DIRECTION_MULTIPLIER = 1;//Controls whether forward on joysticks is forward or backward on robot
    }

    public static class ShooterConstants {

        //PID constants
        public static final double kP_Shoot = 0.5;
        public static final double kI_Shoot = 0;
        public static final double kD_Shoot = 0;
        public static final double kF_Shoot = -1; 
        public static final double kP_Turret = .04;
        public static final double kI_Turret = 0;
        public static final double kD_Turret = 0.0;

        public static final int kPIDLoopIdxShoot = 0;
        public static final int kTimeoutMsShoot = 20;
        public static final int kShooterMaxRPM = 6380;

        public static final double kTalonFXPPR = 2048;
        public static final double kTurretEncoderPPR = 7;

        //Turret dimensions/other config
        public static final double kTurretGearReduction = 71;
        public static final double kTurretSprocketDia = 1;
        public static final double kTurretDiskDia = 13.75;

        public static final double kTurretMaxAngle = 300;

        //
        public static final double kShooterSpeed = -1.0;
        public static final double kTurretSpeed = .8;
    }

    public static class PneumaticsConstants {

        public static final int[] LEFT_SHIFTER = {1, 4};
        public static final int[] RIGHT_SHIFTER = {2, 5};
        public static final int[] INTAKE_PNEU = {0, 3};
        public static final int[] PTO_PNEU = {6, 7};

        public static String GEAR = "Low";
        public static String INTAKE_STATUS = "Retracted";
        public static String PTO_STATUS = "Disengaged";
    }

    public static class ProcessorConstants {

        //All values experimental
        public static final double kIntakeSpeed = -.55;
        public static final double kOuttakeSpeed = .75;
        public static final double kProcessorSpeed = -0.7;
        public static final double kUnlockSpeed = 0.75;
        public static final double kLockSpeed = -.20;
    }

    public static class ClimberConstants {

        public static final int kClimbEncoderPPR = 2048;
        public static final double kMaxHookHeight = 84;//inches
        public static final double kShaftSize = 0.5; //diameter, inches
        public static final double kClimberGearReduction = 1;
        public static final double kHookSpeed = 1.0;
        public static final double kHeightTolerance = 2;//inches
    }

}
