package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "RedLoading", group = "LinearOpMode")
public class RedLoadingAuto extends LinearOpMode {

    Drivetrain drivetrain;
    Arm arm;
    SkystoneDetector skystoneDetector;
    Intake intake;
    private String skyStonePosition;
    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(this);
        arm = new Arm(this);
        skystoneDetector = new SkystoneDetector(this);
        intake = new Intake(this);
        time = new ElapsedTime();
        skyStonePosition = "right";

        // updating skystone pos
        while (!isStarted()) {
            skyStonePosition = skystoneDetector.getSkystoneBlue();
            telemetry.addData("skyStonePosition: ", skyStonePosition);
            telemetry.update();
        }

        waitForStart();

        if (skyStonePosition.equals("right"))
            drivetrain.moveInches(-.4, 17.25);
        else if (skyStonePosition.equals("center"))
            drivetrain.moveInches(-.4, 18);
        else
            drivetrain.moveInches(-.4, 18.0);

        // amount needed to strafe left to line up with center skystone
        final int CENTER_OFFSET = 2;

        // line up with correct skystone
        if (skyStonePosition.equals("left")) {
            drivetrain.strafeRightEncoder(.3, 10);
        } else if (skyStonePosition.equals("center")) {
            drivetrain.strafeRightEncoder(.3,  3);
        } else {
            drivetrain.strafeLeftEncoder(.3, 8);
        }
        drivetrain.turnPID(0, 1.0 / 90, 0, .7, 3);

        // move arm to grab skystone
        arm.reset();
        arm.firstGrab(-.8, 172, 160);
        sleep(1000);
        arm.grab();
        sleep(1000);
        arm.moveDegrees(.5, 18);
        drivetrain.moveInches(.4, 12);

        // turn so back to face build zone, strafe to avoid hitting partners
        drivetrain.turnPID(90, 1.0 / 90, 0, .5, 4);
        drivetrain.strafeRightEncoder(.4, 22);

        drivetrain.turnPID(90, 1.0 / 90, 0, .7, 3);
        arm.setPower(-.3);
        arm.reset();

        // amount needed to drive to foundation if pos = center
        int CENTER_DRIVE_DISTANCE = 88;

        // move to foundation
        if (skyStonePosition.equals("left")) {
            drivetrain.moveInchesRed(-1, CENTER_DRIVE_DISTANCE - 8, 90);
        } else if (skyStonePosition.equals("center")) {
            drivetrain.moveInchesRed(-1, CENTER_DRIVE_DISTANCE, 90);
        } else {
            drivetrain.moveInchesRed(-1, CENTER_DRIVE_DISTANCE + 8, 90);
        }

        // move arm up to avoid hitting foundation
        arm.moveDegrees(.5, 25);

        // move and turn for foundation so back faces foundation
        drivetrain.turnPID(0, .6 / 90, 0, .5, 5);
        drivetrain.moveInches(-.7, 25);

        // grab foundation
        drivetrain.foundationGrab();

        // release skystone
        arm.moveDegrees(-.5, 20);
        arm.release();
        arm.moveDegrees(.5, 120);

        // move towards construction site and release foundation
        drivetrain.moveInches(.4, 35);
        sleep(500);
        drivetrain.moveInches(-.4, 2.5);
        drivetrain.turnPID(90, 3.0 / 90, 0, .5, 4.5);
        drivetrain.foundationRelease();
        arm.moveDegrees(0.5, 20);

        // park
        drivetrain.strafeRightEncoder(.6, 8.5);
        drivetrain.moveInches(.7, 25);

    }
}