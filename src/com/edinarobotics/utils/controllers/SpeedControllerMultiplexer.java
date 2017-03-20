package com.edinarobotics.utils.controllers;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class SpeedControllerMultiplexer implements SpeedController {
    SpeedController[] speedController;
    
    public SpeedControllerMultiplexer(SpeedController[] speedController) {
        this.speedController = speedController;
    }
    
    public SpeedController getSpeedController(int num) {
    	return speedController[num];
    }

    public double get() {
        for(int i = 0;i < speedController.length;i++) {
            speedController[i].get();
        }
        return speedController[0].get();
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

	@Override
	public void setInverted(boolean isInverted) {
		
	}

	@Override
	public boolean getInverted() {
		return false;
	}

	@Override
	public void stopMotor() {
		set(0);
	}
    

}
