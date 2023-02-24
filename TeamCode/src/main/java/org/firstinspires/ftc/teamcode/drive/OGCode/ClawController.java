package org.firstinspires.ftc.teamcode.drive.OGCode;

import org.checkerframework.checker.units.qual.Current;

public class ClawController {

    public enum closeClawStatus
    {
        INIT,
        CLOSED,
        OPEN,
    }
    public static closeClawStatus CurrentStatus = closeClawStatus.INIT,  PreviousStatus = closeClawStatus.INIT;
    double pozOpenClaw = 0, pozCloseClaw = 1;

    public void update(RobotMap Robotel)
    {
        if (PreviousStatus != CurrentStatus)
        {
            switch (CurrentStatus)
            {
                case CLOSED:
                {
                    Robotel.servoGheara.setPosition(pozCloseClaw);
                    break;
                }
                case OPEN:
                {
                    Robotel.servoGheara.setPosition(pozOpenClaw);
                    break;
                }
            }
        }
        PreviousStatus = CurrentStatus;
    }
}
