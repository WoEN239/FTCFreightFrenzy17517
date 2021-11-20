package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.VariablesDashboard;
import org.firstinspires.ftc.teamcode.VariablesDashboard.MovementConfig.*;
import org.firstinspires.ftc.teamcode.robot.RobotModules;

@Autonomous
public class AutonomBlueTeam extends LinearOpMode {

    FtcDashboard dashboard;

    private final RobotModules robotModules = new RobotModules(this);
    private VariablesDashboard vb = new VariablesDashboard();

    Runnable actions[] = {
            //() -> {robotModules.movement.Move(1.0,2.0);},
            () -> {robotModules.init();}
    };
    public int queue = 0;
    @Override
    public void runOpMode() {

        dashboard = FtcDashboard.getInstance();

        robotModules.init();

        waitForStart();
        queue = 0;
        while(opModeIsActive() && queue < actions.length) {
            Runnable action = actions[queue];
            action.run();
            robotModules.update();
            if (robotModules.line()) {
                queue++;
            }
        }
        {
            robotModules.movement.Move(-37.5 ,-40);
            robotModules.movement.Move(-45,-30);
            robotModules.duck.setDirection(-1);
            robotModules.duck.DuckSpin(true);
            sleep(7000);
            robotModules.movement.Move(-60,-90);
        }
    }
}