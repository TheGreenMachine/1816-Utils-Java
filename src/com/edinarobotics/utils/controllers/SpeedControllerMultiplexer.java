package com.edinarobotics.utils.controllers;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedControllerMultiplexer implements SpeedController {
    SpeedController[] speedController;
    
    public SpeedControllerMultiplexer(SpeedController[] speedController) {
        this.speedController = speedController;
    }
    
    public double get() {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].get();
        }
        return speedController[0].get();
    }

    public void set(double speed, byte syncGroup) {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].set(speed, syncGroup);
        }
    }

    public void set(double speed) {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].set(speed);
        }
    }

    public void disable() {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].disable();
        }
    }

    public void pidWrite(double output) {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].pidWrite(output);
        }
    }
    
}
