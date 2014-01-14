![The Green Machine Logo](http://edinarobotics.com/sites/all/themes/greenmachine/assets/images/Logo.gif)

#1816-Utils-Java
This is the Java code repository for FRC Team 1816's Utilities package. This project contains various utility classes built on top of WPIlib.

##Features
* Pre-defined utility commands for command-based robot programming
* Abstracted Gamepad classes representing a standard two-joystick gamepad, with various filters and scaling abilities
* PID Tuning through standalone tuning system
* Hierarchical logging framework

##Build Instructions
You will need Apache Ant installed on your system to build this project. You will also need to have the SunSpot FRC SDK installed ("sunspotfrcsdk" directory in your HOME directory), with `networktables-crio.jar`, `squawk.jar` and `wpilibj.jar` in the `lib` folder.

Then run:

    ant jar-app
and the compiled `app.jar` will be present in the /build folder.

##Import into IDE
###Netbeans
Works out of the box, just open the project. Build using command-line or right-click project and `build`.

###Eclipse
1. Import project
2. Modify build path and add `networktables-crio.jar`, `squawk.jar` and `wpilibj.jar` as libraries
3. Build using the FRC External Build Configuration
