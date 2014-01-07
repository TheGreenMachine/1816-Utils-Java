package com.edinarobotics.utils.pid;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Allows tuning of PID systems through the dashboard using a
 * {@link NetworkTable}.
 * This class handles exchanging data with the dashboard and passing that data
 * to the relevant PIDConfig instance. This class <em>does not</em> save
 * tuning data, it will be lost as soon as the robot is power-cycled.
 */
public class PIDTuningManager {
    private static PIDTuningManager instance;
    private NetworkTable pidTable;
    private Hashtable pidConfigs;
    
    private PIDTuningManager(){
        pidTable = NetworkTable.getTable("pid");
        pidTable.putBoolean("tuning", false);
        pidConfigs = new Hashtable();
    }
    
    /**
     * Returns the instance of the PIDTuningManager singleton.
     * @return The PIDTuningManager instance.
     */
    public static PIDTuningManager getInstance(){
        if(instance == null){
            instance = new PIDTuningManager();
        }
        return instance;
    }
    
    /**
     * Returns the PIDConfig with the given name. If no PIDConfig with
     * {@code name} exists, a new PIDConfig is created. {@code name} <em>must not</em>
     * contain a comma (",") as this will cause bugs with the dashboard.
     * @param name The name of the requested PIDConfig.
     * @return The PIDConfig instance with the requested {@code name}.
     */
    public PIDConfig getPIDConfig(String name){
        if(!pidConfigs.containsKey(name)){
            pidConfigs.put(name, new PIDConfig(name));
        }
        return (PIDConfig)pidConfigs.get(name);
    }
    
    /**
     * Returns a comma-separated String of the names of all existing
     * PIDConfig instances. This is used internally by PIDTuningManager.
     * @return A comma-separated String of the names of all existing PIDConfig
     * instances.
     */
    private String getConfigNames(){
        String names = "";
        Enumeration e = pidConfigs.keys();
        if(e.hasMoreElements()){
            names = (String)e.nextElement();
        }
        while(e.hasMoreElements()){
            names += ","+((String)e.nextElement());
        }
        return names;
    }
    
    /**
     * Runs a single iteration of the PID tuning loop. This method should be
     * called periodically during the PID tuning process (for example
     * in the testPeriodic() method of IterativeRobot).
     * This method will handle the NetworkTable data exchange with the dashboard.
     */
    public void runTuning(){
        if(isTuningEnabled()){
            //Set list of subsystems
            pidTable.putString("subsystems", getConfigNames());
            //Get actively selected subsystem name
            String systemName = pidTable.getString("system", "");
            if(!systemName.equals((""))){
                //Get active PID subsystem
                PIDConfig pidSystem = getPIDConfig(systemName);
                //Set PIDF values
                pidSystem.setPID(pidTable.getNumber("p", 0), pidTable.getNumber("i", 0),
                        pidTable.getNumber("d", 0), pidTable.getNumber("f", 0));
                //Send current setpoint and value to the tuning bench for feedback
                pidTable.putNumber("value", pidSystem.getValue());
                pidTable.putNumber("setpoint", pidSystem.getSetpoint());
                //Get and set remote control values
                pidSystem.setRemoteSetpoint(pidTable.getNumber("remotesetpoint", 0.0));
                pidSystem.setRemoteRawControlValue(pidTable.getNumber("remotevalue", 0.0));
                pidSystem.setRemoteControlMode(getEffectiveRemoteControlMode());
            }
        }
    }
    
    /**
     * Resets all PIDConfig objects to default values. This will undo
     * any tuning performed by the dashboard.
     */
    public void resetAll(){
        for(Enumeration e = pidConfigs.elements(); e.hasMoreElements();){
            ((PIDConfig)e.nextElement()).reset();
        }
    }
    
    /**
     * Checks if PID tuning is currently enabled. This method can be used to
     * determine whether PID tuning using NetworkTables is currently in
     * progress.
     * @return {@code true} if PID tuning is in progress, {@code false}
     * otherwise.
     */
    private boolean isTuningEnabled(){
        return pidTable.getBoolean("tuning", false);
    }

    /**
     * Returns the remote control mode specified by the remote tuning
     * system.
     * @return The PIDRemoteControlMode specified by the remote tuning system. 
     */
    private PIDRemoteControlMode getRemoteControlMode(){
        boolean remoteRequested = pidTable.getBoolean("remotecontrol", false);
        if(!remoteRequested){
            return PIDRemoteControlMode.NONE;
        }
        String remoteMode = pidTable.getString("remotecontrolmode", "");
        if(remoteMode.equalsIgnoreCase("setpoint")){
            return PIDRemoteControlMode.SETPOINT;
        }
        else if(remoteMode.equalsIgnoreCase("value")){
            return PIDRemoteControlMode.VALUE;
        }
        return PIDRemoteControlMode.NONE;
    }
    
    /**
     * Returns the effective remote control mode specified by the remote
     * tuning system, taking into account whether tuning is actually
     * enabled. If tuning is not enabled, remote control is effectively
     * not enabled.
     * @return The effective remote control mode as described above.
     */
    private PIDRemoteControlMode getEffectiveRemoteControlMode(){
        if(isTuningEnabled()){
           return getRemoteControlMode();
        }
        return PIDRemoteControlMode.NONE;
    }
}
