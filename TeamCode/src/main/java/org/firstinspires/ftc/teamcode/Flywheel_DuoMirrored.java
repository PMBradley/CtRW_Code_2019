package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Flywheel_DuoMirrored {

    private Robot2019 robot;

    public Flywheel_DuoMirrored (Robot2019 robot)
    {
        this.robot = robot;
    }


    boolean isInitilized = false;


    public void init_motors(){
        // robot.init(hardwareMap);
        isInitilized = true;
        robot.motorIntakeR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.motorIntakeL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void set_Power(boolean intakeDirectionIn, boolean intakeDirectionOut){

        int power = 0;
        if(intakeDirectionIn)
            power = 1;
        else if(intakeDirectionOut)
            power = -1;
        robot.motorIntakeL.setPower(power);
        robot.motorIntakeR.setPower(-power);
    }

}