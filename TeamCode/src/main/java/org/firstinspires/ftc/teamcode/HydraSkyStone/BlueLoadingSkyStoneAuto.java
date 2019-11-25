package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "BlueLoadingSkyStone", group = "LinearOpMode")
public class BlueLoadingSkyStoneAuto extends LinearOpMode {

    Drivetrain drivetrain;
    Arm arm;
    SkystoneDetector skystoneDetector;
    Intake intake;
    private String skyStonePosition;
    ElapsedTime time;
    public static double gyroOffset = 0;

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
        if (skyStonePosition.equals("right"))
            drivetrain.moveInches(-.4, 17.25);
        else if (skyStonePosition.equals("center"))
            drivetrain.moveInches(-.4, 19);
        else
            drivetrain.moveInches(-.4, 18.0);

        // amount needed to strafe left to line up with center skystone
        final int CENTER_OFFSET = 5;

        // line up with correct skystone
        if (skyStonePosition.equals("left")){
            drivetrain.strafeRightEncoder(.3, 16 - CENTER_OFFSET);
        }
        else if (skyStonePosition.equals("center")){
            drivetrain.strafeLeftEncoder(.3, CENTER_OFFSET + 3);
        }
        else {
            drivetrain.strafeLeftEncoder(.3, CENTER_OFFSET + 13);
        }
        drivetrain.turnPID(-135, 1.0/90, 0, .7, 3);
        arm.moveDegrees(0.5, 10);

        // intake skystone
        intake.intakeIn(1);
        drivetrain.moveInches(.4, 10);
        sleep(1500);
        intake.stop();

        // grab skystone
        arm.moveDegrees(-0.5, 10);
        arm.grab();
        sleep(1000);
        drivetrain.moveInches(-.4, 20);

        // turn so back to face build zone, strafe to avoid hitting partners
        drivetrain.turnPID(-90, 1.0 / 90, 0,.5, 4);
        drivetrain.strafeLeftEncoder(.6, 22);

        drivetrain.turnPID(-90, 1.0/90, 0, .7, 3);

        // amount needed to drive to foundation if pos = center
        int CENTER_DRIVE_DISTANCE = 88;

        // move to foundation
        if (skyStonePosition.equals("left")){
            drivetrain.moveInches(-1, CENTER_DRIVE_DISTANCE - 8, -90);
        }
        else if (skyStonePosition.equals("center")){
            drivetrain.moveInches(-1, CENTER_DRIVE_DISTANCE, -90);
        }
        else {
            drivetrain.moveInches(-1, CENTER_DRIVE_DISTANCE + 8, -90);
        }

        arm.setPower(0);
        // move arm up to avoid hitting foundation
        arm.moveDegrees(.5, 25);

        // move and turn for foundation so back faces foundation
        drivetrain.turnPID(0, .6 / 90, 0, .5, 5);
        drivetrain.moveInches(-.7, 20);

        // grab foundation
        drivetrain.foundationGrab();
        arm.reset();
        arm.moveDegrees(1, 160);
        arm.release();

        // moves arm back to original position
        arm.moveDegrees(-1, 160);


        // move towards construction site and release foundation
        drivetrain.moveInches(.4, 35);
        sleep(500);
        drivetrain.moveInches(-.4, 2.5);
        drivetrain.turnPID(-90, 2.45 / 90, 0, .5, 4.5);
        drivetrain.foundationRelease();

        // park
        drivetrain.strafeLeftEncoder(.6, 5);
        drivetrain.moveInches(.7, 30);

        gyroOffset = drivetrain.getYaw();
    }
}
