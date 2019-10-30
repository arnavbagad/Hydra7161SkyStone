package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.MainOpMode;

@TeleOp(name = "MainTeleOp", group = "opMode")
public class MainTeleOp extends MainOpMode {

    public double max = 0;

    @Override
    public void loop() {

        // ==================================== DRIVE ==============================================
        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.left_stick_x) > .1 || (Math.abs(gamepad1.right_stick_x)) > .1) {
            double FLP = gamepad1.left_stick_y - gamepad1.right_stick_x - gamepad1.left_stick_x;
            double FRP = gamepad1.left_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x ;
            double BLP = -gamepad1.left_stick_y + gamepad1.right_stick_x - gamepad1.left_stick_x; // using gears; direction reversed
            double BRP = -gamepad1.left_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x; // using gears direction reversed
            max = Math.max(Math.max(Math.abs(FLP), Math.abs(FRP)), Math.max(Math.abs(BLP), Math.abs(BRP)));
            // scales power if any motor power is greater than 1
            if (max > 1) {
                FLP /= max;
                FRP /= max;
                BLP /= max;
                BRP /= max;
            }
//            if (gamepad1.left_trigger > 0.1){
//                startMotors(FLP/2, FRP/2, BLP/2, BRP/2);
//            }
//            else {
                startMotors(FLP, FRP, BLP, BRP);
            //}
        }
        else {
            stopMotors();
        }

        // intake out
        if (gamepad1.right_bumper){
            runIntake(-1, -1);
        }
        else if (gamepad1.left_bumper) {
            runIntake(1, 1);
        }
        else {
            runIntake(0,0);
        }

        if (gamepad2.left_trigger > 0.1) {
            liftUp();
        }
        if (gamepad2.right_trigger > 0.1) {
            liftDown();
        }

        // grabs foundation
        if (Math.abs(gamepad2.left_stick_y) > .1) {
            arm.setPower(-gamepad2.left_stick_y);
        }
        else {
            arm.setPower(0);
        }

        // lowers capstone dropper
        if (gamepad2.right_bumper) {
            capStoneDown();
            //armRotation();
        }
        // lifts capstone dropper
        if (gamepad2.left_bumper) {
            capStoneUp();;
            //armRelease();
        }
        // rotates arm/block
        if (gamepad2.dpad_up) {
            armRotation();
        }
        // resets arm pivot
        if (gamepad2.dpad_down) {
            armReset();
        }
        // grabs block
        if (gamepad2.dpad_left) {
            armGrab();
        }
        // releases block
        if (gamepad2.dpad_right) {
            armRelease();
        }

        //else if(gamepad1.left_bumper) {
            //grabberUp();
        //}
    }
}