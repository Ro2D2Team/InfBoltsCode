package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        double x_PLACE_PRELOAD = -30, y_PLACE_PRELOAD = 0, Angle_PLACE_PRELOAD = 200, backPreload = 35;
        double x_GTS_FIRST_LT1 = -31, y_GTS_FIRST_LT1 = -12,
                x_GTS_FIRST_STS = -40, y_GTS_FIRST_STS = -14, Angle_GTS_FIRST = 180,
                x_GTS_FIRST_LT2 = -57, y_GTS_FIRST_LT2 = -14;
        double x_PLACE_FIRST_LT1 = -50, y_PLACE_FIRST_LT1 = -12,
                x_PLACE_FIRST_STS = -32.5, y_PLACE_FIRST_STS = 0, angle_PLACE_FIRST_STS = 220;

        double x_PLACE_PRELOAD_RT = 30, y_PLACE_PRELOAD_RT = 0, Angle_PLACE_PRELOAD_RT = 340, backPreload_RT = 35;
        double x_GTS_FIRST_LT1_RT = 31, y_GTS_FIRST_LT1_RT = -12,
                x_GTS_FIRST_STS_RT = 40, y_GTS_FIRST_STS_RT = -14, Angle_GTS_FIRST_RT = 0,
                x_GTS_FIRST_LT2_RT = 57, y_GTS_FIRST_LT2_RT = -14;
        double x_PLACE_FIRST_LT1_RT = 50, y_PLACE_FIRST_LT1_RT = -12,
                x_PLACE_FIRST_STS_RT = 32.5, y_PLACE_FIRST_STS_RT = 0, angle_PLACE_FIRST_STS_RT = 320;

         RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .build()
                );
         RoadRunnerBotEntity traiectoriiStanga = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -63, Math.toRadians(270)))
                                .setReversed(true)

                                .back(backPreload)
                                .splineToSplineHeading(new Pose2d(x_PLACE_PRELOAD,y_PLACE_PRELOAD,Math.toRadians(Angle_PLACE_PRELOAD)),Math.toRadians(45))


                                .lineTo(new Vector2d(x_GTS_FIRST_LT1, y_GTS_FIRST_LT1))
                                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS, y_GTS_FIRST_STS, Math.toRadians(Angle_GTS_FIRST)), Math.toRadians(Angle_GTS_FIRST))
                                .lineTo(new Vector2d(x_GTS_FIRST_LT2,y_GTS_FIRST_LT2))


                                .lineTo(new Vector2d(x_PLACE_FIRST_LT1, y_PLACE_FIRST_LT1))
                                .splineToSplineHeading(new Pose2d(x_PLACE_FIRST_STS, y_PLACE_FIRST_STS, Math.toRadians(angle_PLACE_FIRST_STS)), Math.toRadians(35))


                                .build()
                );
        RoadRunnerBotEntity traiectoriiDreapta = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, -63, Math.toRadians(270)))
                                .setReversed(true)

                                .back(backPreload_RT)
                                .splineToSplineHeading(new Pose2d(x_PLACE_PRELOAD_RT,y_PLACE_PRELOAD_RT,Math.toRadians(Angle_PLACE_PRELOAD_RT)),Math.toRadians(155))


                                .lineTo(new Vector2d(x_GTS_FIRST_LT1_RT, y_GTS_FIRST_LT1_RT))
                                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS_RT, y_GTS_FIRST_STS_RT, Math.toRadians(Angle_GTS_FIRST_RT)), Math.toRadians(Angle_GTS_FIRST_RT))
                                .lineTo(new Vector2d(x_GTS_FIRST_LT2_RT,y_GTS_FIRST_LT2_RT))


                                .lineTo(new Vector2d(x_PLACE_FIRST_LT1_RT, y_PLACE_FIRST_LT1_RT))
                                .splineToSplineHeading(new Pose2d(x_PLACE_FIRST_STS_RT, y_PLACE_FIRST_STS_RT, Math.toRadians(angle_PLACE_FIRST_STS_RT)), Math.toRadians(70))


                                .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(traiectoriiStanga)
                .addEntity(traiectoriiDreapta)
                .start();
    }
}