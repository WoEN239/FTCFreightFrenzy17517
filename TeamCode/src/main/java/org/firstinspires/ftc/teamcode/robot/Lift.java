package org.firstinspires.ftc.teamcode.robot;

import static org.firstinspires.ftc.teamcode.robot.Lift.Elevator.downTargetElevator;
import static org.firstinspires.ftc.teamcode.robot.Lift.Elevator.middleTargetElevator;
import static org.firstinspires.ftc.teamcode.robot.Lift.Elevator.upTargetElevator;
import static java.lang.Math.abs;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Lift implements RobotModule {

    public boolean queuebool = true;
    public DigitalChannel limitSwitch = null;
    private double encoderTarget = 0;
    private DcMotorEx motorLift = null;
    private WoENRobot robot = null;
    private ElevatorPosition elevatorTarget = ElevatorPosition.DOWN;
    private int liftEncoderOffset = 0;

    public Lift(WoENRobot robot) {
        this.robot = robot;
    }

    public void initialize() {
        limitSwitch = robot.getLinearOpMode().hardwareMap.get(DigitalChannel.class, "limitSwitch");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        motorLift = robot.getLinearOpMode().hardwareMap.get(DcMotorEx.class, "E1");
        motorLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLift.setDirection(DcMotorEx.Direction.REVERSE);
    }

    public boolean actionIsCompleted() {
        return queuebool;
    }

    public ElevatorPosition getElevatorPosition() {
        if (elevatorTarget == ElevatorPosition.DOWN && queuebool) return ElevatorPosition.DOWN;
        int encoderPosition = getLiftEncoderPosition();
        return abs(encoderPosition - middleTargetElevator) <
                abs(encoderPosition - upTargetElevator) ? ElevatorPosition.MIDDLE :
                ElevatorPosition.UP;
    }

    public void setElevatorTarget(ElevatorPosition elevatorPosition) {
        this.elevatorTarget = elevatorPosition;

        switch (elevatorPosition) {
            case DOWN:
                encoderTarget = downTargetElevator;
                break;
            case MIDDLE:
                encoderTarget = middleTargetElevator;
                break;
            case UP:
                encoderTarget = upTargetElevator;
                break;
        }

        queuebool = false;
    }

    public int getLiftEncoderPosition() {
        return motorLift.getCurrentPosition() - liftEncoderOffset;
    }

    public void update() {
        if (elevatorTarget == ElevatorPosition.DOWN) {
            if (!limitSwitch.getState()) {
                motorLift.setPower(-1);
                queuebool = false;
            } else {
                motorLift.setPower(0);
                liftEncoderOffset = motorLift.getCurrentPosition();
                queuebool = true;
            }
        } else {
            double error = encoderTarget - getLiftEncoderPosition();
            double kP = 0.005;

            if (abs(error) > 50) {
                motorLift.setPower(error * kP);
                queuebool = false;
            } else {
                motorLift.setPower(0);
                queuebool = true;
            }
        }
    }

    public enum ElevatorPosition {
        DOWN, MIDDLE, UP
    }

    @Config
    public static class Elevator {
        public static double downTargetElevator = 0;
        public static double middleTargetElevator = 650;
        public static double upTargetElevator = 1300;
    }
}
