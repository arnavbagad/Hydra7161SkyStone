package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode.HydraSkyStone;

import com.qualcomm.ftccommon.configuration.EditLegacyServoControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain{

    //GripPipelineGOD GripPipelineGOD;
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor FL;
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public DcMotor lift;
    public Servo leftFoundation;
    public Servo rightFoundation;
    public Servo capStone;
    Sensors sensor;
    ElapsedTime time;
    LinearOpMode opMode;

    public Drivetrain(LinearOpMode opMode)throws InterruptedException {
        this.opMode = opMode;
        this.opMode.telemetry.addLine("Init: started");
        this.opMode.telemetry.update();
        time = new ElapsedTime();
        sensor = new Sensors(opMode);
        BR = this.opMode.hardwareMap.dcMotor.get("BR");
        BL = this.opMode.hardwareMap.dcMotor.get("BL");
        FR = this.opMode.hardwareMap.dcMotor.get("FR");
        FL = this.opMode.hardwareMap.dcMotor.get("FL");
        intakeLeft = this.opMode.hardwareMap.dcMotor.get("intakeLeft");
        intakeRight = this.opMode.hardwareMap.dcMotor.get("intakeRight");
        lift = this.opMode.hardwareMap.dcMotor.get("lift");

        leftFoundation = this.opMode.hardwareMap.servo.get("leftFoundation");
        rightFoundation = this.opMode.hardwareMap.servo.get("rightFoundation");
        capStone = this.opMode.hardwareMap.servo.get("capStone");

        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
        //capStone.setPosition(0);

        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        intakeRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.opMode.telemetry.addLine("Init: FINISHED");
        this.opMode.telemetry.update();
    }


    public void startMotors(double FLP, double BLP, double FRP, double BRP) {
        FL.setPower(FLP);
        BL.setPower(BLP);
        FR.setPower(FRP);
        BR.setPower(BRP);
    }

    public void moveInches(double power, double inches, double heading) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches * 35 && !opMode.isStopRequested()) {
            if (Math.abs(sensor.getGyroYaw() - heading) > 1){
                if (sensor.getGyroYaw() > heading){
                    startMotors(power, power, power * .9, power * .9);
                }
                else if (sensor.getGyroYaw() < heading) {
                    startMotors(power * .9, power * .9, power, power);
                }
                else {
                    startMotors(power, power, power, power);
                }
            }
        }
        stopMotors();
    }

    public void moveInchesRed(double power, double inches, double heading) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches * 35 && !opMode.isStopRequested()) {
            if (Math.abs(sensor.getGyroYaw() - heading) > 1){
                if (sensor.getGyroYaw() > heading){
                    startMotors(power, power, power * .9, power * .9);
                }
                else if (sensor.getGyroYaw() < heading) {
                    startMotors(power * .9, power * .9, power, power);
                }
                else {
                    startMotors(power, power, power, power);
                }
            }
        }
        stopMotors();
    }

    public void turnPI(double p, double i, double timeout) {
        time.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currentTime = time.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = 0;// = sensor.getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > 1 && time.seconds() < timeout) {
            pastTime = currentTime;
            currentTime = time.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = 0;//sensor.getTrueDiff(angle);
            P = angleDiff * kP;
            I += dT * angleDiff * kI;
            changePID = P;
            changePID += I;
            opMode.telemetry.addData("PID: ", changePID);
            opMode.telemetry.addData("diff", angleDiff);
            opMode.telemetry.addData("P", P);
            opMode.telemetry.addData("I", I);
            opMode.telemetry.update();
            if (changePID < 0) {
                //startMotors(changePID - .10, -changePID + .10);
            } else {
                //startMotors(changePID + .10, -changePID - .10);
            }
        }
        stopMotors();
    }

    public void turnPID(double angle, double p, double i, double d, double timeout){
        time.reset();
        double kP = p;
        double kD = d;
        double kI = i;
        double integral = 0;
        double currentTime = time.milliseconds();
        double pastTime = 0;
        double prevError = sensor.getTrueDiff(angle);
        double error = prevError;
        double power = 0;
        while ((Math.abs(error) > .5 && time.seconds() < timeout && !opMode.isStopRequested())) {
            pastTime = currentTime;
            currentTime = time.milliseconds();
            double dT = currentTime - pastTime;
            error = sensor.getTrueDiff(angle);
            integral += dT * (error - prevError);
            power = (error * kP) + integral * kI + ((error - prevError) / dT * kD);
            if (power < 0) {
                turn(power - .2);
            } else {
                turn(power + .2);
            }
            opMode.telemetry.addData("angle: ", sensor.getGyroYaw());
            opMode.telemetry.addData("P", (error * kP));
            opMode.telemetry.addData("I", (integral * kI));
            opMode.telemetry.addData("D", ((Math.abs(error) - Math.abs(prevError)) / dT * kD));
            opMode.telemetry.update();
            prevError = error;
        }
        stopMotors();
    }

    public void turn(double power){
        startMotors(power, power, -power, -power);
    }


    public void stopMotors(){
        startMotors(0,0,0,0);
    }

    public void moveInches(double power, double inches) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches * 35 && !opMode.isStopRequested()) {
            startMotors(power, power, power, power);
        }
        stopMotors();
    }

    public void strafeRightEncoder(double power, double inches) throws InterruptedException{
        resetEncoders();
        while(getEncoderAvg() < inches * 41 && !opMode.isStopRequested()) {
            startMotors(power, -power, -power, power);
        }
        stopMotors();
    }

    public void strafeLeftEncoder(double power, double inches) throws InterruptedException{
        strafeRightEncoder(-power, inches);
    }

    public void resetEncoders() throws InterruptedException {
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }

    public int getEncoderAvg() {
        int count = 4;
        if ((FR.getCurrentPosition()) == 0){
            count--;
        }
        if ((FL.getCurrentPosition()) == 0){
            count--;
        }
        if ((BR.getCurrentPosition()) == 0){
            count--;
        }
        if ((BL.getCurrentPosition()) == 0){
            count--;
        }

        count = count == 0 ? 1 : count;

        return (Math.abs(FR.getCurrentPosition()) +  Math.abs(FL.getCurrentPosition())
                + Math.abs(BR.getCurrentPosition())
                + Math.abs(BL.getCurrentPosition())) / count;
    }

    public void foundationGrab() {
        leftFoundation.setPosition(0.88);
        rightFoundation.setPosition(0.026);
    }

    public void foundationRelease() {
        leftFoundation.setPosition(0.3);
        rightFoundation.setPosition(0.6);
    }

    public double getYaw(){
        return sensor.getGyroYaw();
    }

}
