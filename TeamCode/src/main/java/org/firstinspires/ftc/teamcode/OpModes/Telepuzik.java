package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Controllers.BaseController;
import org.firstinspires.ftc.teamcode.Controllers.ShooterController;
import org.firstinspires.ftc.teamcode.Controllers.IntakeController;

@TeleOp
public class Telepuzik extends LinearOpMode {
    private BaseController base = new BaseController();
    private ShooterController shooter = new ShooterController();
    private IntakeController intake = new IntakeController();

    @Override
    public void runOpMode() throws InterruptedException {
        base.init(hardwareMap);
        shooter.init(hardwareMap);
        intake.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            base.drive(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );

            if (gamepad1.options) base.resetHeading();

            if (gamepad1.a) shooter.shootLow();
            else if (gamepad1.b) shooter.shootMid();
            else if (gamepad1.y) shooter.shootHigh();
            else if (gamepad1.x) shooter.stop();

            intake.setTargetSpeed(shooter.getTargetVelocity());

            if (gamepad1.right_trigger > 0.1) intake.manualSuckNoShoot();
            else if (gamepad1.left_trigger > 0.1) intake.manualUnSuck();
            else intake.stopManual();

            intake.updateFeed((shooter.getLeftVelocity() + shooter.getRightVelocity()) / 2.0, gamepad1.right_bumper);

            telemetry.addData("Shooter L vel", shooter.getLeftVelocity());
            telemetry.addData("Shooter R vel", shooter.getRightVelocity());
            telemetry.addData("Target", shooter.getTargetVelocity());
            telemetry.update();
        }
    }
}
