/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.edinarobotics.utils.swerve;

import com.edinarobotics.utils.math.Vector2;

/**
 *
 */
public abstract class SwerveStrategy {
    public abstract void doSwerve(SwerveRotationGroup[] chassisComponents, Vector2 directionMPerSec, double rotationRadSecCW); //UNITS m, rad, rad/s, m/s
}
