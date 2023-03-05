package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        double x_PLACE_PRELOAD = -27.5, y_PLACE_PRELOAD = -5, Angle_PLACE_PRELOAD = 200, backPreload = 45;
        double x_GTS_FIRST_LT1 = -31, y_GTS_FIRST_LT1 = -13,
                x_GTS_FIRST_STS = -40, y_GTS_FIRST_STS = -13, Angle_GTS_FIRST = 180,
                x_GTS_FIRST_LT2 = -65, y_GTS_FIRST_LT2 = -10;
        double x_PLACE_FIRST_LT1 = -45, y_PLACE_FIRST_LT1 = -13,
                x_PLACE_FIRST_STS = -30, y_PLACE_FIRST_STS = -5, angle_PLACE_FIRST_STS = 240;






        double x_PLACE_PRELOAD_RT = 30, y_PLACE_PRELOAD_RT = 0, Angle_PLACE_PRELOAD_RT = 340, backPreload_RT = 35;
        double x_GTS_FIRST_LT1_RT = 31, y_GTS_FIRST_LT1_RT = -14,
                x_GTS_FIRST_STS_RT = 40, y_GTS_FIRST_STS_RT = -13, Angle_GTS_FIRST_RT = 0,
                x_GTS_FIRST_LT2_RT = 57, y_GTS_FIRST_LT2_RT = -13;
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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -63, Math.toRadians(270)))
                                .setReversed(true)

                                .setReversed(true)
                                .back(backPreload)
                                .splineTo(new Vector2d(x_PLACE_PRELOAD, y_PLACE_PRELOAD), Math.toRadians(50))
                                // preload

                                .forward(0.1)
                                .splineTo(new Vector2d(x_GTS_FIRST_LT2, y_GTS_FIRST_LT2), Math.toRadians(180))
                                // gts1

                                .back(5)
                                .splineTo(new Vector2d(x_PLACE_FIRST_STS, y_PLACE_FIRST_STS), Math.toRadians(45))
                                // place first

                                .forward(1)
                                .splineTo(new Vector2d(x_GTS_FIRST_LT2, y_GTS_FIRST_LT2), Math.toRadians(180))
                                // gts2

                                .back(5)
                                .splineTo(new Vector2d(x_PLACE_FIRST_STS, y_PLACE_FIRST_STS), Math.toRadians(45))
                                // place first

                                .build()
                );
        RoadRunnerBotEntity traiectoriiDreapta = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(35, -63, Math.toRadians(270)))
                                .setReversed(true)

                                .back(backPreload_RT)
                                .splineToSplineHeading(new Pose2d(x_PLACE_PRELOAD_RT,y_PLACE_PRELOAD_RT,Math.toRadians(Angle_PLACE_PRELOAD_RT)),Math.toRadians(135))


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
//                .addEntity(traiectoriiDreapta)
                .start();
    }
}