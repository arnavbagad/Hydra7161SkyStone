package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode.HydraSkyStone;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake{

    public DcMotor intakeRight;
    public DcMotor intakeLeft;

    ElapsedTime time;
    LinearOpMode opMode;

    public Intake(LinearOpMode opMode)throws InterruptedException {
        this.opMode = opMode;
        intakeLeft = this.opMode.hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = this.opMode.hardwareMap.dcMotor.get("intakeRight");

        intakeLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        time = new ElapsedTime();
        this.opMode.telemetry.addLine("Init: FINISHED");
        this.opMode.telemetry.update();
    }


    public void intakeIn(double power){
        intakeLeft.setPower(-power);
        intakeRight.setPower(power);
    }

    public void intakeOut(double power){
        intakeIn(-power);
    }

    public void stop(){
        intakeIn(0);
    }

    public void intakeTime(double power, double seconds){
        time.reset();
        while (time.seconds() < seconds && !opMode.isStopRequested()){
            intakeIn(power);
        }
        stop();
    }

}
