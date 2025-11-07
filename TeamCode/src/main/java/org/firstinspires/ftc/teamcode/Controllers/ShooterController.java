package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShooterController {
    private DcMotorEx shooterL, shooterR;
    private Servo servoL, servoR;
    private double targetVelocity = 0;

    public void init(HardwareMap hwMap) {
        shooterL = hwMap.get(DcMotorEx.class, "shooter_l");
        shooterR = hwMap.get(DcMotorEx.class, "shooter_r");
        servoL = hwMap.get(Servo.class, "l_angle");
        servoR = hwMap.get(Servo.class, "r_angle");

        shooterL.setDirection(DcMotorSimple.Direction.REVERSE);
        servoL.setDirection(Servo.Direction.REVERSE);

        shooterL.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterR.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    public void shootHigh() {
        targetVelocity = 1960;
        shooterL.setVelocity(targetVelocity);
        shooterR.setVelocity(targetVelocity);
        servoL.setPosition(0.85);
        servoR.setPosition(0.85);
    }

    public void shootMid() {
        targetVelocity = 1650;
        shooterL.setVelocity(targetVelocity);
        shooterR.setVelocity(targetVelocity);
        servoL.setPosition(0.8);
        servoR.setPosition(0.8);
    }

    public void shootLow() {
        targetVelocity = 1440;
        shooterL.setVelocity(targetVelocity);
        shooterR.setVelocity(targetVelocity);
        servoL.setPosition(0.75);
        servoR.setPosition(0.75);
    }

    public void stop() {
        targetVelocity = 0;
        shooterL.setVelocity(0);
        shooterR.setVelocity(0);
    }

    public double getLeftVelocity() { return shooterL.getVelocity(); }
    public double getRightVelocity() { return shooterR.getVelocity(); }
    public double getTargetVelocity() { return targetVelocity; }
}
