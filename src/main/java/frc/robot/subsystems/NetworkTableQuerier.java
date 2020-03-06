package frc.robot.subsystems;

import java.lang.Thread;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableQuerier implements Runnable {

    private static NetworkTableInstance networkTableInstance;
    private static NetworkTable visionTable;
    private static NetworkTable navxTable;

    private static NetworkTableEntry robotStop;
    private static NetworkTableEntry zeroGyro;
    private static NetworkTableEntry piGyroAngle;
    private static NetworkTableEntry ballDistance;
    private static NetworkTableEntry ballAngle;
    private static NetworkTableEntry ballScreenPercent;
    private static NetworkTableEntry foundBall;
    private static NetworkTableEntry foundTape;
    private static NetworkTableEntry tapeDistance;
    private static NetworkTableEntry tapeOffset;
    private static NetworkTableEntry targetLock;

    public NetworkTableQuerier(){

        initNetworkTables();
    }
    
    public void run(){
        
        while(true){

            queryNetworkTables();
             
        }
    }

    public void start(){

        Thread ntThread = new Thread(this);
        ntThread.start();
    }

    private void initNetworkTables(){

        networkTableInstance = NetworkTableInstance.getDefault();
        visionTable = networkTableInstance.getTable("vision");
        navxTable = networkTableInstance.getTable("navx");

        robotStop = visionTable.getEntry("RobotStop");
        zeroGyro = navxTable.getEntry("ZeroGyro");

        robotStop.setNumber(0);
        zeroGyro.setNumber(0);

        queryNetworkTables();

    }


    private void queryNetworkTables(){

        robotStop = visionTable.getEntry("RobotStop");
        zeroGyro = navxTable.getEntry("ZeroGyro");

        piGyroAngle = navxTable.getEntry("GyroAngle");

        ballDistance = visionTable.getEntry("BallDistance");
        ballAngle = visionTable.getEntry("BallAngle");
        ballScreenPercent = visionTable.getEntry("BallScreenPercent");
        foundBall = visionTable.getEntry("FoundBall");

        foundTape = visionTable.getEntry("FoundTape");
        targetLock = visionTable.getEntry("TargetLock");
        tapeDistance = visionTable.getEntry("TapeDistance");
        tapeOffset = visionTable.getEntry("TapeOffset");
    }

    /*
     * @param entry The ID of the NetworkTables entry to return
     * @return the double value of the NetworkTables entry chosen; an error will be returned if entry is not a double 
     * 
     * List of available entries:
     * "BallDistance"
     * "BallAngle"
     * "BallScreenPercent"
     * "TapeOffset"
     * "TapeDistance" 
     */

    public synchronized double getVisionDouble(String entry){

        return visionTable.getEntry(entry).getDouble(0);
    }

    /*
     * @param entry The ID of the NetworkTables entry to return
     * @return the boolean value of the NetworkTables entry chosen; an error will be returned if entry is not a boolean 
     * 
     * List of available entries:
     * "FoundBall"
     * "FoundTape"
     * "TargetLock" 
     */
    public synchronized boolean getVisionBoolean(String entry){

        return visionTable.getEntry(entry).getBoolean(false);
    }

    public synchronized double getPiGyro(){

        return navxTable.getEntry("GyroAngle").getDouble(0);
    }


    public synchronized void robotStop(){

        robotStop.setNumber(1);
    }

    public synchronized void zeroPiGyro(){

        zeroGyro.setNumber(1);
    }

}