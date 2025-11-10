package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.*;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

@TeleOp(name = "Red Color Detection Dashboard", group = "Vision")
public class EysyOpenCv extends LinearOpMode {

    OpenCvCamera camera;
    ColorDetectionPipeline pipeline;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance()
                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new ColorDetectionPipeline();
        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera error", errorCode);
                telemetry.update();
            }
        });

        // === Подключаем FTC Dashboard ===
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        dashboard.startCameraStream(camera, 30);

        telemetry.addLine("Camera initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Red %", "%.2f", pipeline.getRedFraction() * 100);
            telemetry.addData("Red detected", pipeline.isRedDetected());
            telemetry.update();
            sleep(100);
        }
    }

    // Пайплайн
    public static class ColorDetectionPipeline extends OpenCvPipeline {
        private double redFraction = 0.0;
        private boolean redDetected = false;

        private final Scalar lowerRed1 = new Scalar(0, 120, 70);
        private final Scalar upperRed1 = new Scalar(10, 255, 255);
        private final Scalar lowerRed2 = new Scalar(170, 120, 70);
        private final Scalar upperRed2 = new Scalar(180, 255, 255);

        @Override
        public Mat processFrame(Mat input) {
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Mat mask1 = new Mat();
            Mat mask2 = new Mat();
            Core.inRange(hsv, lowerRed1, upperRed1, mask1);
            Core.inRange(hsv, lowerRed2, upperRed2, mask2);

            Mat mask = new Mat();
            Core.add(mask1, mask2, mask);

            double redPixels = Core.countNonZero(mask);
            double total = input.cols() * input.rows();
            redFraction = redPixels / total;
            redDetected = redFraction > 0.1;

            Scalar rectColor = redDetected ? new Scalar(0, 0, 255) : new Scalar(0, 255, 0);
            Imgproc.rectangle(input, new Rect(0, 0, input.cols(), input.rows()), rectColor, 3);

            return input;
        }

        public double getRedFraction() {
            return redFraction;
        }

        public boolean isRedDetected() {
            return redDetected;
        }
    }
}
