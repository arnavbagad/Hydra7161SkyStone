package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Loading", group = "LinearOpMode")
public class BlueLoadingAuto extends LinearOpMode {

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
//        while(!isStarted()){
//            skyStonePosition = skystoneDetector.getSkystonePos();
//            telemetry.addData("skyStonePosition: ", skyStonePosition);
//            telemetry.update();
//        }

        skyStonePosition = "left";
        waitForStart();

        if(skyStonePosition.equals("left")) {
            drivetrain.moveEncoder(-.6, 30);
            sleep(2000);
            drivetrain.turnPID(-90, .7/90, 0, 0, 10);
            sleep(2000);
            // drive forwards or backwards to lineup with skystone
            drivetrain.strafeLeftEncoder(.5, 10);
            intake.intakeIn(1);
            drivetrain.moveEncoder(.35, 4);
            intake.stop();
//            arm.grab();
//            time.reset();

            //goes to skystone
            //grabs skystone
            //goes to foundation
            //places skystone
            //moves foundation
            //parks
        }
        if(skyStonePosition.equals("center")) {
            //goes to skystone
            //grabs skystone
            //goes to foundation
            //places skystone
            //moves foundation
            //parks
        }
        if(skyStonePosition.equals("right")) {
            //goes to skystone
            //grabs skystone
            //goes to foundation
            //places skystone
            //moves foundation
            //parks
        }

    }
}
