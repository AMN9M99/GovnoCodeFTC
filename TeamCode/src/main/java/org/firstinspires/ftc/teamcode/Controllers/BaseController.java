package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class BaseController {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    private GoBildaPinpointDriver pinpoint;

    public void init(HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotor.class, "lfd");
        rightFront = hwMap.get(DcMotor.class, "rfd");
        leftBack = hwMap.get(DcMotor.class, "lbd");
        rightBack = hwMap.get(DcMotor.class, "rbd");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pinpoint = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.resetPosAndIMU();
    }

    public void drive(double forward, double strafe, double rotate) {
        double botHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        double rotStrafe = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotForward = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotForward) + Math.abs(rotStrafe) + Math.abs(rotate), 1);
        double frontLeftPower = (rotForward + rotStrafe + rotate) / denominator;
        double backLeftPower = (rotForward - rotStrafe + rotate) / denominator;
        double frontRightPower = (rotForward - rotStrafe - rotate) / denominator;
        double backRightPower = (rotForward + rotStrafe - rotate) / denominator;

        leftFront.setPower(frontLeftPower);
        rightFront.setPower(frontRightPower);
        leftBack.setPower(backLeftPower);
        rightBack.setPower(backRightPower);
    }

    public void resetHeading() {
        pinpoint.resetPosAndIMU();
    }
}
