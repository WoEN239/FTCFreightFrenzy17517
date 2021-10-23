package org.firstinspires.ftc.teamcode.opmodes.ButtonActivatedModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Manipulator {
    private LinearOpMode linearOpMode = null;
    public Manipulator(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
    }

    private DcMotorEx liftMotor = null;

    public void init(){
        liftMotor = linearOpMode.hardwareMap.get(DcMotorEx.class, "UpDown");

        for(DcMotorEx dcMotorEx: linearOpMode.hardwareMap.getAll(DcMotorEx.class)) {
            dcMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcMotorEx.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
}
