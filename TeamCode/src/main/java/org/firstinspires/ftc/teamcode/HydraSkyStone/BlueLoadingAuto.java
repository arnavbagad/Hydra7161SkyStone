package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Loading", group = "LinearOpMode")
public class BlueLoadingAuto extends LinearOpMode {

    Drivetrain drivetrain;
    SkystoneDetector skystoneDetector;
    private String skyStonePosition;
    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(this);
        skystoneDetector = new SkystoneDetector(this);
        time = new ElapsedTime();
//        while(!isStarted()){
//            skyStonePosition = skystoneDetector.getSkystonePos();
//            telemetry.addData("skyStonePosition: ", skyStonePosition);
//            telemetry.update();
//        }

        skyStonePosition = "left";
        waitForStart();

        if(skyStonePosition.equals("left")) {
            drivetrain.moveEncoder(-.7, 15);
            time.reset();

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
