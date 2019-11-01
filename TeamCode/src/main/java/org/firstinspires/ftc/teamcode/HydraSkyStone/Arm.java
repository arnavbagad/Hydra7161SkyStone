package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode.HydraSkyStone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Arm{

    public DcMotor arm;

    public Servo grabber;
    public Servo rotate;

    ElapsedTime time;
    LinearOpMode opMode;

    public Arm(LinearOpMode opMode)throws InterruptedException {
        this.opMode = opMode;
        arm = this.opMode.hardwareMap.dcMotor.get("arm");

        grabber = this.opMode.hardwareMap.servo.get("grabber");
        rotate = this.opMode.hardwareMap.servo.get("rotate");

        grabber.setPosition(0.85);
        rotate.setPosition(0.17);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        time = new ElapsedTime();
        this.opMode.telemetry.addLine("Init: FINISHED");
        this.opMode.telemetry.update();
    }


    public void setPower(double power){
        arm.setPower(power);
    }

    public void stop(){
        setPower(0);
    }

    public void moveTime(double power, double seconds){
        time.reset();
        while (time.seconds() < seconds && !opMode.isStopRequested()){
            arm.setPower(power);
        }
        stop();
    }

//    public void frontToBack(double power, double seconds){
//        times.reset();
//        while (times.seconds() < seconds && !opMode.isStopRequested()){
//            arm.setPower(power);
//            if (times.seconds() > 1.7) {
//                rotation();
//            }
//        }
//        stop();
//    }

    public void reset() {
        rotate.setPosition(0.50);
    }

    public void rotation() {
        rotate.setPosition(0.16);
    }

    public void grab() {
        grabber.setPosition(0.8);
    }

    public void release() {
        grabber.setPosition(1);
    }

}
