package org.firstinspires.ftc.teamcode.drive.OGCode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

@TeleOp(name="SampleOpModeHulk", group="Linear Opmode")

public class SampleOpModeHulk extends  LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime() , timeGetVoltage = new ElapsedTime();

    double  PrecisionDenominator=1, PrecisionDenominator2=1.25;
    double Kp4Bar = 0.08;
    double Ki4Bar = 0;
    double Kd4Bar = 0.0055;
    public void robotCentricDrive(DcMotor leftFront,DcMotor leftBack,DcMotor rightFront,DcMotor rightBack, double  lim, boolean StrafesOn , double LeftTrigger,  double RightTrigger)
    {
        double y = gamepad1.right_stick_y; // Remember, this is reversed!
        double x = gamepad1.right_stick_x*1.1;
        if (StrafesOn == false)
        {
            x=0;
        }
        double rx = gamepad1.left_stick_x*1 - LeftTrigger + RightTrigger;

        rx/=PrecisionDenominator2;
        x/=PrecisionDenominator;
        y/=PrecisionDenominator;
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftPower = Clip(frontLeftPower,lim);
        backLeftPower = Clip(backLeftPower,lim);
        frontRightPower = Clip(frontRightPower,lim);
        backRightPower = Clip(backRightPower,lim);

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }
    public void fieldCentricDrive(BNO055IMU imu,DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack , double LeftTrigger , double RightTrigger)
    {
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x - LeftTrigger + RightTrigger;

        // Read inverse IMU heading, as the IMU heading is CW positive
        double botHeading = -imu.getAngularOrientation().firstAngle;

        double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        leftFront.setPower(frontLeftPower);
        leftBack.setPower(backLeftPower);
        rightFront.setPower(frontRightPower);
        rightBack.setPower(backRightPower);
    }

    double Clip(double Speed,double lim)
    {
        return Math.max(Math.min(Speed,lim),-lim);
    }
    @Override
    public void runOpMode() {

        RobotMap robot=new RobotMap(hardwareMap);

        double currentVoltage;
        VoltageSensor batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();
        currentVoltage = batteryVoltageSensor.getVoltage();
        double loopTime = 0;

        ClawController clawController = new ClawController();
        clawController.CurrentStatus = ClawController.closeClawStatus.OPEN;
        clawController.update(robot);
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        DcMotor rightFront = null;
        DcMotor rightBack = null;
        DcMotor leftFront = null;
        DcMotor leftBack = null;
        SimplePIDController motor4BarPID = new SimplePIDController(0.02,0,0);
        rightFront = hardwareMap.get(DcMotor.class,"leftFront");
        leftFront = hardwareMap.get(DcMotor.class,"rightFront");
        rightBack = hardwareMap.get(DcMotor.class,"leftBack");
        leftBack = hardwareMap.get(DcMotor.class,"rightBack");


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

       // leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();
        double lim = 1 ; /// limita vitezei la sasiu
        boolean StrafesOn = true;
        int pozHigh = 1700;
        String typeOfDrive = "RobotCentric";
        SimplePIDController hello = new SimplePIDController(Kp4Bar,Ki4Bar,Kd4Bar);
        while (opModeIsActive()) {
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);

            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);
            int Motor4BarPosition = robot.motor4Bar.getCurrentPosition();
            double powerMotor4Bar = hello.update(Motor4BarPosition);
            powerMotor4Bar = Math.max(-1,Math.min(powerMotor4Bar,1));
            robot.motor4Bar.setPower(powerMotor4Bar);
            robotCentricDrive(leftFront, leftBack, rightFront, rightBack, lim,StrafesOn , 0,0);

            if (!previousGamepad2.circle && currentGamepad2.circle)
            {
                robot.extensieOuttake.setTargetPosition(pozHigh);
                robot.extensieOuttake.setPower(1);
                robot.extensieOuttake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (!previousGamepad2.left_bumper && currentGamepad2.left_bumper)
            {
                if (clawController.CurrentStatus == ClawController.closeClawStatus.CLOSED)
                {
                    clawController.CurrentStatus = ClawController.closeClawStatus.OPEN;
                }
                else
                {
                    clawController.CurrentStatus = ClawController.closeClawStatus.CLOSED;
                }
            }
            if (!previousGamepad2.square && currentGamepad2.square)
            {
                hello.targetValue = 200;
            }
            if (!previousGamepad2.cross && currentGamepad2.cross)
            {
                hello.targetValue = 0;
            }
            clawController.update(robot);
            telemetry.addData("CurrentPositionOuttake", robot.extensieOuttake.getCurrentPosition());
            telemetry.addData("CurrentPosition4Bar",robot.motor4Bar.getCurrentPosition());
            telemetry.update();
        }
    }
}
