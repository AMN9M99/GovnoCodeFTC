package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController {
    private DcMotor intake1, intake2;
    private long lastShootTime = 0;
    private boolean feeding = false;
    private static final long SHOOT_DURATION_MS = 100;
    private static final long SHOOT_COOLDOWN_MS = 150;
    private static final double TOLERANCE = 60;
    private double targetSpeed = 0;

    public void init(HardwareMap hwMap) {
        intake1 = hwMap.get(DcMotor.class, "intake_1");
        intake2 = hwMap.get(DcMotor.class, "intake_2");
        intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setTargetSpeed(double target) {
        targetSpeed = target;
    }

    public void manualSuckNoShoot() {
        intake1.setPower(1.0);
        intake2.setPower(-1.0);
    }

    public void manualUnSuck() {
        intake1.setPower(-1.0);
        intake2.setPower(-1.0);
    }

    public void stopManual() {
        if (!feeding) {
            intake1.setPower(0);
            intake2.setPower(0);
        }
    }

    public void updateFeed(double shooterVelocity, boolean rightBumperPressed) {
        long now = System.currentTimeMillis();
        boolean atTarget = targetSpeed > 0 && Math.abs(shooterVelocity - targetSpeed) <= TOLERANCE;
        if (rightBumperPressed && atTarget && !feeding && now - lastShootTime > SHOOT_COOLDOWN_MS) {
            feeding = true;
            lastShootTime = now;
            intake1.setPower(1.0);
            intake2.setPower(1.0);
        }
        if (feeding) {
            if (now - lastShootTime >= SHOOT_DURATION_MS) {
                feeding = false;
                intake1.setPower(0);
                intake2.setPower(0);
            } else {
                intake1.setPower(1.0);
                intake2.setPower(1.0);
            }
        }
    }
}
