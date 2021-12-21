package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Timer implements RobotModule {

    private ElapsedTime elapsedTime = new ElapsedTime();

    private double delaySeconds = 0;

    public void delay(double delaySeconds) {
        this.delaySeconds = delaySeconds;
        elapsedTime.reset();
    }

    public double getTimeLeft(){
        return delaySeconds-elapsedTime.seconds();
    }

    @Override
    public void initialize() {
        elapsedTime.reset();
    }

    @Override
    public void update() {
    }

    @Override
    public boolean actionIsCompleted() {
        return elapsedTime.seconds() > delaySeconds;
    }
}
