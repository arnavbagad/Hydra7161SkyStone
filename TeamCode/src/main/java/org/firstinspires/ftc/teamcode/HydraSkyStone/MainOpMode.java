package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import static org.firstinspires.ftc.teamcode.BlueLoadingAuto.gyroOffset;

public class MainOpMode extends OpMode {

    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor FL;
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public DcMotor lift;
    public DcMotor arm;
    public Servo leftFoundation;
    public Servo rightFoundation;

    public double armLength = 14.5;
    public double armAV = 151.3;
    public double liftLV = 14.8;
    public double time;
    public double height;
    public Servo grabber;
    public Servo rotate;
    public Servo capStone;

    public BNO055IMU gyro;
    LinearOpMode opMode;
    Orientation angles;
    Acceleration gravity;
    BNO055IMU.Parameters parameters;

    @Override
    public void init() {
        telemetry.addLine("Init: started");
        telemetry.update();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(parameters);

        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");
        FR = hardwareMap.dcMotor.get("FR");
        FL = hardwareMap.dcMotor.get("FL");
        intakeLeft = hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = hardwareMap.dcMotor.get("intakeRight");
        lift = hardwareMap.dcMotor.get("lift");
        arm = hardwareMap.dcMotor.get("arm");

        leftFoundation = hardwareMap.servo.get("leftFoundation");
        rightFoundation = hardwareMap.servo.get("rightFoundation");
        grabber = hardwareMap.servo.get("grabber");
        rotate = hardwareMap.servo.get("rotate");
        capStone = hardwareMap.servo.get("capStone");

        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
        grabber.setPosition(1);
        rotate.setPosition(0.52);
        //capStone.setPosition(0);

        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);

        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        telemetry.addLine("Init: FINISHED");
        telemetry.update();
    }

    public void loop() {
    }

    public void stopMotors() {
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    public void startMotors(double FLP, double FRP, double BLP, double BRP) {
        FL.setPower(FLP);
        FR.setPower(FRP);
        BL.setPower(BLP);
        BR.setPower(BRP);
    }

    public void runIntake(double left, double right) {
        intakeLeft.setPower(left);
        intakeRight.setPower(-right);
    }

    public void stopIntake() {
        runIntake(0, 0);
    }

    public void foundationDown() {
        leftFoundation.setPosition(0.88);
        rightFoundation.setPosition(0.026);
    }

    public void foundationUp() {
        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
    }

    public void armReset() {
        rotate.setPosition(0.52);
    }

    public void armRotation() {
        rotate.setPosition(0.17);
    }

    public void armGrab() {
        grabber.setPosition(0.80);
    }

    public void armRelease() {
        grabber.setPosition(1);
    }


    public void lift(double power) {
        lift.setPower(power);
    }

    public void stopLift() { lift.setPower(0); }

    public void capStoneDown() {
        capStone.setPosition(0.1);
    }

    public void fieldCentric(double x, double y, double turn){
        double angle = Math.atan2(y, x) - Math.toRadians(getGyroYaw());
        double speed = Math.hypot(y, x);
        double robotX = Math.sin(angle) * speed;
        double robotY = Math.cos(angle) * speed;
        robotCentric(robotX, robotY, turn);
    }

    public void robotCentric(double x, double y, double turn){
        double FLP = y - turn - x;
        double FRP = y + turn + x ;
        double BLP = -y + turn - x; // using gears; direction reversed
        double BRP = -y - turn + x; // using gears direction reversed
        double max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
        // scales power if any motor power is greater than 1
        if (max > 1) {
            FLP /= max;
            FRP /= max;
            BLP /= max;
            BRP /= max;
        }
        startMotors(FLP, FRP, BLP, BRP);
    }

    public double getGyroYaw() {
        updateValues();
        double yaw = angles.firstAngle * -1;
        if(angles.firstAngle + gyroOffset < -180)
            yaw -= 360;
        return yaw;
    }

    public void updateValues() {
        angles = gyro.getAngularOrientation();
    }
//    public void capStoneUp(){
//        capStone.setPosition(0);
//    }
//   public void armLift() {

//   }

//   public void scan() {

}