package org.firstinspires.ftc.teamcode.drive.OGCode.Autonomii;

import static org.firstinspires.ftc.teamcode.drive.OGCode.RobotController.RobotControllerStatus.PICK_UP_CONE;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.OGCode.ClawController;
import org.firstinspires.ftc.teamcode.drive.OGCode.FourBarController;
import org.firstinspires.ftc.teamcode.drive.OGCode.GhidajController;
import org.firstinspires.ftc.teamcode.drive.OGCode.LiftController;
import org.firstinspires.ftc.teamcode.drive.OGCode.RobotController;
import org.firstinspires.ftc.teamcode.drive.OGCode.RobotMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;


@Config
@Autonomous(group = "drive")

public class Dreapta5_1 extends LinearOpMode {
    enum STROBOT
    {
        START,
        PLACE_PRELOAD,
        PICK_UP_CONE,
        GO_TO_STACK,
        COLLECT,
        GO_PLACE_FROM_STACK,
        STOP_JOC,
        PLACE_STACK_CONE,
        PARK
    }
    public static double x_PLACE_PRELOAD = 27.5, y_PLACE_PRELOAD = -5.5, Angle_PLACE_PRELOAD = 315, backPreload = 43;
    public static double x_GTS_FIRST_LT1 = 35, y_GTS_FIRST_LT1 = -15,
            x_GTS_FIRST_STS= 35, y_GTS_FIRST_STS= -10, Angle_GTS_FIRST = 0,
            x_GTS_FIRST_LT2 = 65, y_GTS_FIRST_LT2 = -11;
    public static double x_PLACE_FIRST_LT1 = 40, y_PLACE_FIRST_LT1 = -11,
            x_PLACE_FIRST_STS = 28, y_PLACE_FIRST_STS = -10, angle_PLACE_FIRST_STS = 315;
    int junctionHeight = 0;
    ElapsedTime TIMERGLOBAL = new ElapsedTime(), timerRetract = new ElapsedTime(), timerLift =new ElapsedTime() , timeCollect = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        RobotMap robot = new RobotMap(hardwareMap);
        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        double currentVoltage;
        VoltageSensor batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();
        currentVoltage = batteryVoltageSensor.getVoltage();
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        ClawController clawController = new ClawController();
        FourBarController fourBarController = new FourBarController();
        LiftController liftController = new LiftController();
        GhidajController ghidajController = new GhidajController();
        RobotController robotController = new RobotController();
        fourBarController.CurrentStatus = FourBarController.fourBarStatus.COLLECT_DRIVE;
        clawController.CurrentStatus = ClawController.closeClawStatus.CLOSED;
        liftController.CurrentStatus = LiftController.liftStatus.GROUND;
        ghidajController.CurrentStatus = GhidajController.ghidajStatus.INTAKE;
        robot.servoGheara.setPosition(0.5);
        int nr=0,NRCON = 5, CAZ = 1;
        ElapsedTime timePLACE_PRELOAD = new ElapsedTime();
        Pose2d startPose = new Pose2d(35, -63, Math.toRadians(270));
        drive.setPoseEstimate(startPose);
        STROBOT status = STROBOT.START;
        TrajectorySequence PLACE_PRELOAD = drive.trajectorySequenceBuilder(startPose)
                .setReversed(true)
                .back(backPreload)
                .addTemporalMarker(1.5, ()->{
                    liftController.CurrentStatus = LiftController.liftStatus.POLE;
                    junctionHeight = 0;
                    robotController.CurrentStatus = PICK_UP_CONE;
                })
                .splineToSplineHeading(new Pose2d(x_PLACE_PRELOAD,y_PLACE_PRELOAD,Math.toRadians(Angle_PLACE_PRELOAD)),Math.toRadians(135))
                .build();
        TrajectorySequence GTS_FIRST = drive.trajectorySequenceBuilder(PLACE_PRELOAD.end())
                .lineTo(new Vector2d(x_GTS_FIRST_LT1, y_GTS_FIRST_LT1))
                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS, y_GTS_FIRST_STS, Math.toRadians(Angle_GTS_FIRST)), Math.toRadians(Angle_GTS_FIRST))
                .lineTo(new Vector2d(x_GTS_FIRST_LT2,y_GTS_FIRST_LT2))
                .build();
        TrajectorySequence PLACE_FIRST = drive.trajectorySequenceBuilder(GTS_FIRST.end())
                .lineTo(new Vector2d(x_PLACE_FIRST_LT1, y_PLACE_FIRST_LT1))
                .splineToSplineHeading(new Pose2d(x_PLACE_FIRST_STS, y_PLACE_FIRST_STS, Math.toRadians(angle_PLACE_FIRST_STS)), Math.toRadians(170))
                .build();
        TrajectorySequence GTS_FIRST_SECOND = drive.trajectorySequenceBuilder(PLACE_FIRST.end())
                .lineTo(new Vector2d(x_GTS_FIRST_LT1, y_GTS_FIRST_LT1))
                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS, y_GTS_FIRST_STS, Math.toRadians(Angle_GTS_FIRST)), Math.toRadians(Angle_GTS_FIRST))
                .lineTo(new Vector2d(x_GTS_FIRST_LT2,y_GTS_FIRST_LT2))
                .build();
        TrajectorySequence PARK_3 = drive.trajectorySequenceBuilder(PLACE_FIRST.end())
                .lineTo(new Vector2d(x_GTS_FIRST_LT1, y_GTS_FIRST_LT1))
                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS, y_GTS_FIRST_STS, Math.toRadians(Angle_GTS_FIRST)), Math.toRadians(Angle_GTS_FIRST))
                .lineTo(new Vector2d(x_GTS_FIRST_LT2,y_GTS_FIRST_LT2))
                .build();
        TrajectorySequence PARK_2 = drive.trajectorySequenceBuilder(PLACE_FIRST.end())
                .lineToLinearHeading(new Pose2d(38.5,-15,Math.toRadians(270)))
                .build();
        TrajectorySequence PARK_1 = drive.trajectorySequenceBuilder(PLACE_FIRST.end())
                .lineToLinearHeading(new Pose2d(10,-17.5,Math.toRadians(270)))
                .build();
        while (!isStarted()&&!isStopRequested())
        {
            telemetry.addLine("Init Complete");
            telemetry.update();
            sleep(50);
        }
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive() && !isStopRequested())
        {
            if (status == STROBOT.START)
            {
                drive.followTrajectorySequenceAsync(PLACE_PRELOAD);
                status = STROBOT.PLACE_PRELOAD;
            }
            else
            if (status == STROBOT.PLACE_PRELOAD)
            {
                if (!drive.isBusy())
                {
                    robotController.CurrentStatus = RobotController.RobotControllerStatus.PLACE;
                    TIMERGLOBAL.reset();
                    status = STROBOT.GO_TO_STACK;
                }
            }
            else
            if (status == STROBOT.GO_TO_STACK)
            {
                if (NRCON==1)
                {
                    status = STROBOT.PARK;
                }
                else
                {
                    if (TIMERGLOBAL.seconds()>0.75)
                    {
                        TIMERGLOBAL.reset();
                        if (NRCON==5)
                        {
                            drive.followTrajectorySequenceAsync(GTS_FIRST);
                        }
                        else
                        {
                            drive.followTrajectorySequenceAsync(GTS_FIRST_SECOND);
                        }
                        status = STROBOT.COLLECT;
                    }
                }
            }
            else
            if (status == STROBOT.COLLECT)
            {
                if (TIMERGLOBAL.seconds()>0.75)
                {
                    liftController.CurrentStatus = LiftController.liftStatus.POLE;
                    junctionHeight = NRCON+3;
                }
                if (!drive.isBusy())
                {
                    robotController.CurrentStatus = RobotController.RobotControllerStatus.PICK_UP_STACK;
                    status = STROBOT.GO_PLACE_FROM_STACK;
                }
            }
            else
            if (status == STROBOT.GO_PLACE_FROM_STACK)
            {
                if (robotController.CurrentStatus == RobotController.RobotControllerStatus.START)
                {
                    TIMERGLOBAL.reset();
                    drive.followTrajectorySequenceAsync(PLACE_FIRST);
                    status = STROBOT.PLACE_STACK_CONE;
                }
            }
            else
            if (status == STROBOT.PLACE_STACK_CONE)
            {
                if (TIMERGLOBAL.seconds()>1.2)
                {
                    liftController.CurrentStatus = LiftController.liftStatus.POLE;
                    junctionHeight = 0;
                }
                if (TIMERGLOBAL.seconds()>1.75)
                {
                    ghidajController.CurrentStatus = GhidajController.ghidajStatus.OUTTAKE;
                }
                if (!drive.isBusy() && TIMERGLOBAL.seconds() > 2.25)
                {
                    robotController.CurrentStatus = RobotController.RobotControllerStatus.PLACE;
                    TIMERGLOBAL.reset();
                    NRCON--;
                    status = STROBOT.GO_TO_STACK;
                }
            }
            else
            if (status == STROBOT.PARK)
            {
                if (CAZ == 1)
                {
                    drive.followTrajectorySequenceAsync(PARK_1);
                }
                else
                if (CAZ == 2)
                {
                    drive.followTrajectorySequenceAsync(PARK_2);
                }
                else
                if (CAZ == 3)
                {
                    drive.followTrajectorySequenceAsync(PARK_3);
                }
                status = STROBOT.STOP_JOC;
            }
            int fourBarPosition = robot.motor4Bar.getCurrentPosition();
            int liftPosition = robot.extensieOuttake.getCurrentPosition();
            fourBarController.update(robot , fourBarPosition,14 );
            clawController.update(robot);
            liftController.update(robot,junctionHeight, liftPosition, currentVoltage);
            ghidajController.update(robot);
            robotController.update(robot, fourBarController, ghidajController, liftController, clawController, junctionHeight, fourBarPosition);
            drive.update();
            telemetry.addData("Pozitie: ", drive.getPoseEstimate());
            // telemetry.addData("caz:", Case);
            telemetry.addData("Status",status);
            telemetry.update();
        }
    }

}