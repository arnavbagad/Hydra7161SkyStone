package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "RedLoadingFoundation", group = "LinearOpMode")
public class RedLoadingAutoFoundation extends LinearOpMode {

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
//        while (!isStarted()) {
//            skyStonePosition = skystoneDetector.getSkystoneRed();
//            telemetry.addData("skyStonePosition: ", skyStonePosition);
//            telemetry.update();
//        }

        waitForStart();

        // move to foundation
        drivetrain.strafeLeftEncoder(.5, 10);
        drivetrain.moveInches(-0.3, 37);

        // grab foundation
        drivetrain.foundationGrab();
        sleep(2000);

        // move towards construction site and release foundation
        drivetrain.moveInches(.4, 35);
        sleep(500);
        drivetrain.moveInches(-.4, 2.5);
        drivetrain.turnPID(90, 3.0 / 90, 0, .7, 4.5);
        drivetrain.foundationRelease();
        drivetrain.moveInches(-.7, 40);
        drivetrain.foundationRelease();

        // park
        drivetrain.strafeRightEncoder(.6, 8);
        drivetrain.moveInches(.7, 50);

    }
}