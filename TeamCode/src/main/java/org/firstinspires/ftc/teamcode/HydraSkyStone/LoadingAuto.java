package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Loading", group = "LinearOpMode")
public class LoadingAuto extends LinearOpMode {

    Drivetrain drivetrain;
    SkystoneDetector skystoneDetector;
    private String skyStonePosition;

    @Override
    public void runOpMode() throws InterruptedException {
        //GripPipelineGOD = new GripPipelineGOD(this);
        drivetrain = new Drivetrain(this);
        skystoneDetector = new SkystoneDetector(this);
        while(!isStarted()){
            skyStonePosition = skystoneDetector.getSkystonePos();
            telemetry.addData("skyStonePosition: ", skyStonePosition);
            telemetry.update();
        }

        waitForStart();

        if(skyStonePosition.equals("left")) {
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
