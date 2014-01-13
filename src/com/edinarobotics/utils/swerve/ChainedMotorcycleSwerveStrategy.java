package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.math.Vector2;
import com.sun.squawk.util.MathUtils;

/**
 *
 */
public class ChainedMotorcycleSwerveStrategy extends SwerveStrategy{
    
    public ChainedMotorcycleSwerveStrategy(){
        
    }

    public void doSwerve(SwerveRotationGroup[] chassisComponents, Vector2 directionMPerSec, double rotationRadSecCW) {
        final double v_x = directionMPerSec.getX();
        final double v_y = directionMPerSec.getY();
        final double omega_v = rotationRadSecCW;
        
        //Loop over all rotation groups to set angle of all wheels
        for(int i = 0; i < chassisComponents.length; i++){
            SwerveRotationGroup rotGroup = chassisComponents[i];
            Vector2 avgPos = getAveragePosition(rotGroup); //This is the motorcycle part, we treat crabbed wheels as if they were one for angle
            //Calculate constants from Ether's paper
            double x_i = avgPos.getX();
            double y_i = avgPos.getY();
            double w_xi = v_x + (omega_v * y_i);
            double w_yi = v_y + (omega_v * x_i);
            
            double steerAngle = (180.0/Math.PI)*MathUtils.atan2(w_xi, w_yi);
            double steerAngleOpp = steerAngle + Math.PI;
            
            double steerAngleDist = Math.abs(rotGroup.getMeasuredAngle() - steerAngle);
            double steerAngleDistOpp = Math.abs(rotGroup.getMeasuredAngle() - steerAngleOpp);
            
            //Pick best legal angle
            if(steerAngleDist < steerAngleDistOpp){
                double selectedAngle = steerAngle;
                double otherAngle = steerAngleOpp;
                //Steer angle is best
                if(!rotGroup.getAngleRestriction().isValidAngle((selectedAngle))){
                    //It's not valid, problem.
                    if(rotGroup.getAngleRestriction().isValidAngle(otherAngle)){
                        //But the other angle's ok
                        rotGroup.setAngle(otherAngle);
                        rotGroup.setShouldReverseSpeed(true);
                    }
                    else{
                        //Let's just go with the closes legal angle to the one we wanted.
                        rotGroup.setAngle(rotGroup.getAngleRestriction().getClosestAngle(selectedAngle));
                        rotGroup.setShouldReverseSpeed(false);
                    }
                }
            } else {
                double selectedAngle = steerAngleOpp;
                double otherAngle = steerAngle;
                //Steer angle is best
                if(!rotGroup.getAngleRestriction().isValidAngle((selectedAngle))){
                    //It's not valid, problem.
                    if(rotGroup.getAngleRestriction().isValidAngle(otherAngle)){
                        //But the other angle's ok
                        rotGroup.setAngle(otherAngle);
                        rotGroup.setShouldReverseSpeed(false);
                    }
                    else{
                        //Let's just go with the closes legal angle to the one we wanted.
                        rotGroup.setAngle(rotGroup.getAngleRestriction().getClosestAngle(selectedAngle));
                        rotGroup.setShouldReverseSpeed(true);
                    }
                }
            }
            
            //OK. We've chosen the angles for all rotation groups
        } //This is the end of the rotation for loop!!!
        
        //Now let's set wheel velocities
        for(int i = 0; i < chassisComponents.length; i++){
            //We're looping over all rot groups
            SwerveRotationGroup rotGroup = chassisComponents[i];
            for(int j = 0; j < rotGroup.getWheels().length; j++){
                SwerveWheel wheel = rotGroup.getWheels()[j];
                //Now we're looping over all swerve wheels
                double x_i = wheel.getPosition().getX();
                double y_i = wheel.getPosition().getY();
                double w_xi = v_x + (omega_v * y_i);
                double w_yi = v_y + (omega_v * x_i);
                double velocity = Math.sqrt(MathUtils.pow(w_xi, 2) + MathUtils.pow(w_yi, 2));
                if(rotGroup.getShouldReverseSpeed()) {
                    wheel.setVelocity(-velocity);
                } else {
                    wheel.setVelocity(velocity);
                }
                
            }
        }//Annnnnnd... we're done.
    }

    private Vector2 getAveragePosition(SwerveRotationGroup group){
        SwerveWheel[] motorArr = group.getWheels();
        double xSum = 0.0, ySum = 0.0;
        for(int i = 0; i < motorArr.length; i++){
            xSum += motorArr[i].getPosition().getX();
            ySum += motorArr[i].getPosition().getY();
        }
        return new Vector2(xSum / ((double)motorArr.length), ySum / ((double)motorArr.length));
    }
    
    protected double normalizeAngle(double angleRadians){
        if(angleRadians < 0){
            return (Math.PI * 2.0) - angleRadians;
        }
        if(angleRadians > (Math.PI * 2.0)){
            return angleRadians - (Math.PI * 2.0);
        }
        return angleRadians;
    }
}
