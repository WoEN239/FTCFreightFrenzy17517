package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


public class Brush implements RobotModule {

    public boolean enableIntake = false;
    private DcMotorEx brushMotor = null;
    private final WoENRobot robot;

    public Brush(WoENRobot robot) {
        this.robot = robot;
    }

    public void initialize() {

        brushMotor = robot.getLinearOpMode().hardwareMap.get(DcMotorEx.class, "BrushMotor");
        brushMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        brushMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brushMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brushMotor.setDirection(DcMotorEx.Direction.REVERSE);

    }

    public void enableIntake(boolean intake) {
        this.enableIntake = intake;
    }

    public void update() {
        double brushPower;
        if (enableIntake)
            if (robot.bucket.isFreightDetected() || robot.lift.getElevatorTarget() != Lift.ElevatorPosition.DOWN)
                brushPower = -1;
            else
                brushPower = 1;
        else
            brushPower = 0;
        brushMotor.setPower(brushPower);
    }

    public boolean actionIsCompleted() {
        return true;
    }
}
