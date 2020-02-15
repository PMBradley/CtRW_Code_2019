package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drive_Meccanum {


  //  Robot2019 robot = new Robot2019();

    private Robot2019 robot;

    private double turnDivisor = 0.5;
    private double speedDivisor = 0.40;


    public Drive_Meccanum(Robot2019 robot)
    {
        this.robot = robot;
    }



    //Variables
    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    private double teleTargetHeading = 0;

    boolean isInitilized = false;

    public void init_motors(){
        // robot.init(hardwareMap);
        isInitilized = true;


        robot.driveBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



    }

    public void Drive_Vector(double x, double y, double r, double heading) { // use with auto only
        //x = Math.sin(heading) * x;
        //y = Math.cos(heading) * y;

        y *= -1; // negate y to emulate the negative values given by controller y

        double sin = Math.sin(heading * 0.0174533);
        double cos = Math.cos(heading * 0.0174533);

        double forward = (x * sin) + (y * cos);
        double right = (x * cos) - (y * sin);

        robot.fl = forward + (r * turnDivisor) + right;
        robot.fr = (forward - (r * turnDivisor) - right);
        robot.bl = forward + (r * turnDivisor) - right;
        robot.br = (forward - (r * turnDivisor) + right);

        robot.driveFL.setPower(robot.fl);
        robot.driveFR.setPower(-robot.fr);
        robot.driveBL.setPower(robot.bl);
        robot.driveBR.setPower(-robot.br);
    }

    public void Drive_Vector(double x, double y, double r, double heading, boolean limiter, double Ltrigger) { // use with controller only
        //x = Math.sin(heading) * x;
        //y = Math.cos(heading) * y;
        double boostFactor = Ltrigger;

        if (boostFactor < .5 && limiter == true)
        {
            x = x * speedDivisor;
            y = y * speedDivisor;
        }

        double sin = Math.sin(heading * 0.0174533);
        double cos = Math.cos(heading * 0.0174533);

        double forward = (x * sin) + (y * cos);
        double right = (x * cos) - (y * sin);

        robot.fl = forward + (r * turnDivisor) + right;
        robot.fr = (forward - (r * turnDivisor) - right);
        robot.bl = forward + (r * turnDivisor) - right;
        robot.br = (forward - (r * turnDivisor) + right);

        robot.driveFL.setPower(robot.fl);
        robot.driveFR.setPower(-robot.fr);
        robot.driveBL.setPower(robot.bl);
        robot.driveBR.setPower(-robot.br);
    }

    public void Drive_Gyro_Vector(double x, double y, double r, double heading, boolean limiter, double boostFactor) { // use with auto only
        //x = Math.sin(heading) * x;
        //y = Math.cos(heading) * y;
        teleTargetHeading += r * 5;
        double minRotPower = 0.32;

        if (boostFactor < .5 && limiter == true)//hi
        {
            x = x * speedDivisor;
            y = y * speedDivisor;
        }

        if(teleTargetHeading > heading + 180){
            teleTargetHeading -= 360;
        }
        else if(teleTargetHeading < heading - 180){
            teleTargetHeading += 360;
        }

        r = r * (teleTargetHeading - heading)/20;

        if(r > 0 && r < minRotPower){
            r = minRotPower;
        }
        else if(r < 0 && r > -minRotPower){
            r = -minRotPower;
        }

        double sin = Math.sin(heading * 0.0174533);
        double cos = Math.cos(heading * 0.0174533);

        double forward = (x * sin) + (y * cos);
        double right = (x * cos) - (y * sin);

        robot.fl = forward + (r * turnDivisor) + right;
        robot.fr = (forward - (r * turnDivisor) - right);
        robot.bl = forward + (r * turnDivisor) - right;
        robot.br = (forward - (r * turnDivisor) + right);

        robot.driveFL.setPower(robot.fl);
        robot.driveFR.setPower(-robot.fr);
        robot.driveBL.setPower(robot.bl);
        robot.driveBR.setPower(-robot.br);
    }

    public void Drive_Gyro_Vector(double x, double y, double r, double heading, double targetHeading) { // use with auto only
        //x = Math.sin(heading) * x;
        //y = Math.cos(heading) * y;
        double minRotPower = 0.32;

        y *= -1; // negate y to emulate the negative values given by controller y


        if(targetHeading > heading + 180){
            targetHeading -= 360;
        }
        else if(targetHeading < heading - 180){
            targetHeading += 360;
        }

        r = r * (targetHeading - heading)/20;

        if(r > 0 && r < minRotPower){
            r = minRotPower;
        }
        else if(r < 0 && r > -minRotPower){
            r = -minRotPower;
        }

        double sin = Math.sin(heading * 0.0174533);
        double cos = Math.cos(heading * 0.0174533);

        double forward = (x * sin) + (y * cos);
        double right = (x * cos) - (y * sin);

        robot.fl = forward + (r * turnDivisor) + right;
        robot.fr = (forward - (r * turnDivisor) - right);
        robot.bl = forward + (r * turnDivisor) - right;
        robot.br = (forward - (r * turnDivisor) + right);

        robot.driveFL.setPower(robot.fl);
        robot.driveFR.setPower(-robot.fr);
        robot.driveBL.setPower(robot.bl);
        robot.driveBR.setPower(-robot.br);
    }

    public boolean gyroTurn(double targetHeading, double currentHeading, double turntolerance)
    {
        //Gyro turn code
        //double targetHeading  - 0 to 360
        //double currentHeading - 0 to 360
        //double turnDirection  - -1 to 1
        boolean turnComplete = false;

        if(Math.abs(currentHeading - targetHeading) <= turntolerance)
        {
            turnComplete = true;
        }
        return turnComplete;
    }
}
