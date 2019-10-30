package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode.HydraSkyStone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain{

    //GripPipelineGOD GripPipelineGOD;
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
    public Servo grabber;
    public Servo rotate;
    public Servo capStone;

    ElapsedTime times;
    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode)throws InterruptedException {
        this.opMode.telemetry.addLine("Init: started");
        this.opMode.telemetry.update();

        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        intakeLeft = this.opMode.hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = this.opMode.hardwareMap.dcMotor.get("intakeRight");
        lift = this.opMode.hardwareMap.dcMotor.get("lift");
        arm = this.opMode.hardwareMap.dcMotor.get("arm");

        leftFoundation = this.opMode.hardwareMap.servo.get("leftFoundation");
        rightFoundation = this.opMode.hardwareMap.servo.get("rightFoundation");
        grabber = this.opMode.hardwareMap.servo.get("grabber");
        rotate = this.opMode.hardwareMap.servo.get("rotate");
        capStone = this.opMode.hardwareMap.servo.get("capStone");

        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
        grabber.setPosition(0.85);
        rotate.setPosition(0.17);
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

        this.opMode.telemetry.addLine("Init: FINISHED");
        this.opMode.telemetry.update();

    }

    public void startMotors(double FLP, double FRP, double BLP, double BRP) {
        BL.setPower(BLP);
        BR.setPower(BRP);
        FL.setPower(FLP);
        FR.setPower(FRP);
    }

    public void turnPI(double p, double i, double timeout) {
        times.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currentTime = times.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = 0;// = sensor.getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > 1 && times.seconds() < timeout) {
            pastTime = currentTime;
            currentTime = times.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = 0;//sensor.getTrueDiff(angle);
            P = angleDiff * kP;
            I += dT * angleDiff * kI;
            changePID = P;
            changePID += I;
            opMode.telemetry.addData("PID: ", changePID);
            opMode.telemetry.addData("diff", angleDiff);
            opMode.telemetry.addData("P", P);
            opMode.telemetry.addData("I", I);
            opMode.telemetry.update();
            if (changePID < 0) {
                //startMotors(changePID - .10, -changePID + .10);
            } else {
                //startMotors(changePID + .10, -changePID - .10);
            }
        }
        startMotors(0,0,0,0);
    }

}
