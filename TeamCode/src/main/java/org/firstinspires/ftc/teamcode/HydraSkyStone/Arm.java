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

        release();
        //rotate();

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

    public void moveDegrees(double power, double degrees) throws InterruptedException{
        resetEncoder();
        while(getEncoder() < degrees * 30 && !opMode.isStopRequested()) {
            setPower(power);
        }
        stop();
    }

    public void firstGrab(double power, double degrees, int degreesToRotate) throws InterruptedException{
        resetEncoder();
        while(getEncoder() < degrees * 30 && !opMode.isStopRequested()) {
            if (getEncoder() > degreesToRotate * 30) {
                 rotate();
            }
            setPower(power);
        }
        stop();
    }

    public void resetEncoder() throws InterruptedException {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public int getEncoder() {
        return Math.abs(arm.getCurrentPosition());
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

    public void rotate() {
        rotate.setPosition(0.17);
    }

    public void grab() {
        grabber.setPosition(.78);
    }

    public void release() {
        grabber.setPosition(1);
    }

}
