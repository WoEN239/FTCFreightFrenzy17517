package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.VariablesDashboard.TeleOpConfig.robotSpeed;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.misc.ButtonOperations.SmartButtonSwitch;
import org.firstinspires.ftc.teamcode.Tele;
import org.firstinspires.ftc.teamcode.misc.ButtonActivatedModes.ButtonActivated;
import org.firstinspires.ftc.teamcode.misc.ButtonOperations.ButtonSwitch;
import org.firstinspires.ftc.teamcode.robot.Duck;
import org.firstinspires.ftc.teamcode.robot.RobotModules;

@TeleOp
public class TeleOpOneGamepad extends LinearOpMode {
    private final RobotModules robotModules = new RobotModules(this);
    private Duck duck = new Duck(this);
    private SmartButtonSwitch duck_function = new SmartButtonSwitch(() -> gamepad1.square,(Boolean duckb) -> robotModules.duck.DuckSpin(duckb));
    private ButtonSwitch buttonSwitch = new ButtonSwitch();
    private Tele telemetry_function = new Tele(this);
    public ButtonActivated BA;

    public double x = 0.0;
    public double y = 0.0;
    public double OX = 0.0;
    public double OY = 0.0;

    @Override
    public void runOpMode() {

        robotModules.init();

        waitForStart();
        while (opModeIsActive()) {
            robotModules.movement.setMotorPowers(-gamepad1.left_stick_y * robotSpeed, gamepad1.right_stick_x * robotSpeed);
            duck_function.activate();
            if(gamepad1.triangle)robotModules.manipulator.MoveServo(true);
            if(gamepad1.cross)robotModules.manipulator.MoveServo(false);
            telemetry_function.activate();
            Drawing();
        }
    }


    public void Drawing() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("x", x);
        packet.put("y", y);
        packet.fieldOverlay()
                .setFill("red")
                .fillRect(OX / 1000, OY / 1000, 40, 40);
        dashboard.sendTelemetryPacket(packet);
    }
}
