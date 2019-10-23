//package org.firstinspires.ftc.teamcode.HydraSkyStone;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;
//
//import java.util.TreeMap;
//
//public class Drivetrain{
//
//    GripPipeline GripPipeline;
//    public DcMotor BR;
//    public DcMotor BL;
//    public DcMotor FR;
//    public DcMotor FL;
//    public DcMotor LI;
//    public DcMotor RI;
//    public DcMotor L2;
//    public DcMotor AM;
//    public Servo LS;
//    public Servo RS;
//    //public Servo CSS;
//
//    LinearOpMode opMode;
//
//    public Drivetrain(LinearOpMode opMode)throws InterruptedException {
//        this.opMode.telemetry.addLine("Init: started");
//        this.opMode.telemetry.update();
//
//        this.opMode = opMode;
//        BR = this.opMode.hardwareMap.dcMotor.get("BR");
//        BL = this.opMode.hardwareMap.dcMotor.get("BL");
//        FR = this.opMode.hardwareMap.dcMotor.get("FR");
//        FL = this.opMode.hardwareMap.dcMotor.get("FL");
//        LI = this.opMode.hardwareMap.dcMotor.get("LI");
//        RI = this.opMode.hardwareMap.dcMotor.get("RI");
//        L2 = this.opMode.hardwareMap.dcMotor.get("L2");
//        AM = this.opMode.hardwareMap.dcMotor.get("AM");
//
//        BL.setDirection(DcMotorSimple.Direction.REVERSE);
//        FL.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        LS = this.opMode.hardwareMap.servo.get("LS");
//        RS = this.opMode.hardwareMap.servo.get("RS");
//        //CSS = this.opMode.hardwareMap.servo.get("CSS");
//
//        LS.setPosition(0.7);
//        RS.setPosition(0.7);
//        //CSS.setPosition(0);
//
//        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        LI.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        RI.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        L2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        AM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//
//        this.opMode.telemetry.addLine("Init: FINISHED");
//        this.opMode.telemetry.update();
//    }
//
//    public void move() {
////        double FLP = gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
////        double FRP = gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x ;
////        double BLP = -gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x; // using gears; direction reversed
////        double BRP = -gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x; // using gears direction reversed
////        max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
////        // scales power if any motor power is greater than 1
////        if (max > 1) {
////            FLP /= max;
////            FRP /= max;
////            BLP /= max;
////            BRP /= max;
////        }
//    }
//
//}
