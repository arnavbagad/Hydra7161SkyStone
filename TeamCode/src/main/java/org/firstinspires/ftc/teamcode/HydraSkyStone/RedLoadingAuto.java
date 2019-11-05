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
        while (!isStarted()){
            skyStonePosition = skystoneDetector.getSkystoneBlue();
            telemetry.addData("skyStonePosition: ", skyStonePosition);
            telemetry.update();
        }

        waitForStart();

        // move towards quarry
        drivetrain.moveInches(-.4, 18.5);

        // amount needed to strafe left to line up with center skystone
        final int CENTER_OFFSET = 4;

        // line up with correct skystone
        if (skyStonePosition.equals("left")){
            drivetrain.strafeRightEncoder(.3, 8 - CENTER_OFFSET);
        }
        else if (skyStonePosition.equals("center")){
            drivetrain.strafeLeftEncoder(.3, CENTER_OFFSET);
        }
        else {
            drivetrain.strafeLeftEncoder(.3, CENTER_OFFSET + 8);
        }

        // move arm to grab skystone
        arm.firstGrab(-.5, 215, 190);
        sleep(1000);
        arm.moveDegrees(.5, 10);

        // turn so back faces build zone, strafe to avoid hitting partners
        drivetrain.turnPID(90, .6 / 90, 0,.5, 5);
        drivetrain.strafeLeftEncoder(.4, 4);

        // amount needed to drive to foundation if pos = center
        int CENTER_DRIVE_DISTANCE = 80;

        // move to foundation
        if (skyStonePosition.equals("left")){
            drivetrain.moveInches(-.7, CENTER_DRIVE_DISTANCE + 8);
        }
        else if (skyStonePosition.equals("center")){
            drivetrain.moveInches(-.7, CENTER_DRIVE_DISTANCE);
        }
        else {
            drivetrain.moveInches(-.7, CENTER_DRIVE_DISTANCE - 8);
        }

        // move arm up to avoid hitting foundation
        arm.moveDegrees(.5, 20);

        // turn so back faces foundation
        drivetrain.turnPID(0, .6 / 90, 0, .5, 5);

        // grab foundation
        drivetrain.foundationGrab();

        // release skystone
        arm.moveDegrees(-.5, 20);
        arm.release();

        // move towards construction site and release foundation
        drivetrain.moveInches(.5, 45);
        drivetrain.foundationRelease();

        // park
        drivetrain.strafeRightEncoder(.7, 50);
        drivetrain.moveInches(-.5, 20);
        drivetrain.strafeRightEncoder(.7, 10);
    }
}
