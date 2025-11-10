package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class basa extends OpMode {

    private DcMotor leftFront,rightFront,leftBack,rightBack;
    private DcMotor intake1,intake2;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class,"lfd");
        rightFront = hardwareMap.get(DcMotor.class,"rfd");
        leftBack = hardwareMap.get(DcMotor.class,"lbd");
        rightBack = hardwareMap.get(DcMotor.class,"rbd");
        intake1 = hardwareMap.get(DcMotor.class,"intake_1");
        intake2 = hardwareMap.get(DcMotor.class,"intake_2");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
double forward = -gamepad1.left_stick_y;
double strafe = gamepad1.left_stick_x;
double rotate = gamepad1.right_stick_x;

double frontLeft = forward + strafe + rotate;
double backLeft = forward - strafe + rotate;
double frontRight = forward - strafe - rotate;
double backRight = forward + strafe - rotate;

leftFront.setPower(frontLeft);
leftBack.setPower(backLeft);
rightFront.setPower(frontRight);
rightBack.setPower(backRight);

if (gamepad1.right_trigger > 0.1){
    intake1.setPower(1);
    intake2.setPower(-1);
}else if (gamepad1.left_bumper){
    intake1.setPower(1);
    intake2.setPower(1);
} else if (gamepad1.left_trigger > 0.1) {
    intake1.setPower(-1);
    intake2.setPower(-1);
} else if (gamepad1.left_bumper) {
    intake1.setPower(0);
    intake2.setPower(0);
}
    }
}
