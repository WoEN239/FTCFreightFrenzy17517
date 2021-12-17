package org.firstinspires.ftc.teamcode.robot;

import static java.lang.Math.abs;
import static java.lang.Math.signum;


import static org.firstinspires.ftc.teamcode.VariablesDashboard.Elevator.*;
import static org.firstinspires.ftc.teamcode.VariablesDashboard.ManipulatorConfig.positionServoDown;
import static org.firstinspires.ftc.teamcode.VariablesDashboard.ManipulatorConfig.positionServoUp;
import static org.firstinspires.ftc.teamcode.VariablesDashboard.ManipulatorConfig.positonServoForElevator;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Elevator implements RobotModule {

    private Servo servoElevator = null;
    private LinearOpMode linearOpMode = null;

    public Elevator(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
    }
    private double target = 0;
    private boolean upDown = false;
    public DcMotorEx motorLift = null;
    private Servo servoLift = null;
    private DistanceSensor distance = null;
    private DigitalChannel limitSwitch = null;

    public void init() {
        limitSwitch = linearOpMode.hardwareMap.get(DigitalChannel.class, "limitSwitch");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        motorLift = linearOpMode.hardwareMap.get(DcMotorEx.class, "E1");
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        servoElevator = linearOpMode.hardwareMap.get(Servo.class, "UpDown");
        distance = linearOpMode.hardwareMap.get(DistanceSensor.class, "distance");
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLift.setDirection(DcMotorEx.Direction.REVERSE);
    }
    public void resetEncoderElevator(){
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public boolean queuebool = true;
    public boolean line(){
        return queuebool;
    }
    public boolean ejectMinerals = false;
    public double moveServo = positionServoDown;
    private ElapsedTime servoTimer = new ElapsedTime();

    public void MoveServoForElevator(boolean ejectMinerals){ //manipulatorUp //manipulatorDown //positonServoForElevator
        if(this.ejectMinerals!=ejectMinerals)
            servoTimer.reset();
        this.ejectMinerals = ejectMinerals;
    }

    public enum ElevatorPosition{
        DOWN, MIDDLE, UP
    }
    public void ElevatorPosition(ElevatorPosition direction) {
        switch (direction) {
            case DOWN:
                target = downTargetElevator;
            break;
            case MIDDLE:
                target = middleTargetElevator;
            break;
            case UP:
                target = upTargetElevator;
            break;
        }
        queuebool = false;  //7
    }
    public void update(){
       servoElevator.setPosition(ejectMinerals?
                positionServoDown:(target == downTargetElevator ?positionServoUp:positonServoForElevator));
        double error = target - motorLift.getCurrentPosition();
        double KP = 0.01;
        Telemetry currentTelemetry;
        currentTelemetry = linearOpMode.telemetry;
        if(limitSwitch.getState()){
            motorLift.setPower(0);
            resetEncoderElevator();
        }
        //currentTelemetry = FtcDashboard.getInstance().getTelemetry();
        currentTelemetry.addData("con", limitSwitch.getState());
        //currentTelemetry.addData("sec", servoTimer.seconds());
        currentTelemetry.update();

        if(abs(error) > 20){
            motorLift.setPower(error * KP);
            queuebool = false;
        } else {
            motorLift.setPower(0);
            queuebool = (servoTimer.seconds() > bucketServoDelay);
        }
    }
}
