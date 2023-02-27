package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MyClass {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

         double x_PLACE_PRELOAD = -27.5, y_PLACE_PRELOAD = -5.5, Angle_PLACE_PRELOAD = 225, backPreload=43;double x_GTS_FIRST_LT1 = -31, y_GTS_FIRST_LT1 = -7.5,
                x_GTS_FIRST_STS=-40, y_GTS_FIRST_STS=-12, Angle_GTS_FIRST=180,
                x_GTS_FIRST_LT2 = -65, y_GTS_FIRST_LT2 = -12;
         double x_PLACE_FIRST_LT1 = -40, y_PLACE_FIRST_LT1 =-12,
                x_PLACE_FIRST_STS = -29, y_PLACE_FIRST_STS = -7, angle_PLACE_FIRST_STS = 255;

        double x_PLACE_PRELOAD_RT = 27.5, y_PLACE_PRELOAD_RT = -5.5, Angle_PLACE_PRELOAD_RT = 315, backPreload_RT=43;
        double x_GTS_FIRST_LT1_RT = 31, y_GTS_FIRST_LT1_RT = -5.5,
                x_GTS_FIRST_STS_RT= 40, y_GTS_FIRST_STS_RT= -12, Angle_GTS_FIRST_RT = 0,
                x_GTS_FIRST_LT2_RT = 65, y_GTS_FIRST_LT2_RT = -12;
        double x_PLACE_FIRST_LT1_RT = 40, y_PLACE_FIRST_LT1_RT =-12,
                x_PLACE_FIRST_STS_RT = 28, y_PLACE_FIRST_STS_RT = -6, angle_PLACE_FIRST_STS_RT = 315;

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
                                .splineToSplineHeading(new Pose2d(x_PLACE_PRELOAD_RT,y_PLACE_PRELOAD_RT,Math.toRadians(Angle_PLACE_PRELOAD_RT)),Math.toRadians(135))


                                .lineTo(new Vector2d(x_GTS_FIRST_LT1_RT, y_GTS_FIRST_LT1_RT))
                                .splineToSplineHeading(new Pose2d(x_GTS_FIRST_STS_RT, y_GTS_FIRST_STS_RT, Math.toRadians(Angle_GTS_FIRST_RT)), Math.toRadians(Angle_GTS_FIRST_RT))
                                .lineTo(new Vector2d(x_GTS_FIRST_LT2_RT,y_GTS_FIRST_LT2_RT))


                                .lineTo(new Vector2d(x_PLACE_FIRST_LT1_RT, y_PLACE_FIRST_LT1_RT))
                                .splineToSplineHeading(new Pose2d(x_PLACE_FIRST_STS_RT, y_PLACE_FIRST_STS_RT, Math.toRadians(angle_PLACE_FIRST_STS_RT)), Math.toRadians(190))


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