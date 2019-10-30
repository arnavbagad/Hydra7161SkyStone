package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

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

    @Override
    public void init() {
        telemetry.addLine("Init: started");
        telemetry.update();

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
        grabber.setPosition(0.85);
        rotate.setPosition(0.50);
        capStone.setPosition(0);

        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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
        intakeLeft.setPower(-left);
        intakeRight.setPower(right);
    }

    public void stopIntake() {
        runIntake(0, 0);
    }

    public void grabberDown() {
        leftFoundation.setPosition(0.88);
        rightFoundation.setPosition(0.026);
    }

    public void grabberUp() {
        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
    }

    public void armRotation() {
        rotate.setPosition(0.50);
    }

    public void armReset() {
        rotate.setPosition(0.16);
    }

    public void armGrab() {
        grabber.setPosition(0.8);
    }

    public void armRelease() {
        grabber.setPosition(1);
    }


    public void liftUp() {
        lift.setPower(1);
    }

    public void liftDown() {
        lift.setPower(0);
    }

    public void capStoneDown() {
        capStone.setPosition(0.1);
    }

    public void capStoneUp(){
        capStone.setPosition(0);
    }
//   public void armLift() {

//   }

//   public void scan() {

}