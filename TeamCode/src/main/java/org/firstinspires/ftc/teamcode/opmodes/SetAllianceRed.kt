package org.firstinspires.ftc.teamcode.competition.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.ftc.OpModeData

@Autonomous(name = "Set Alliance Color RED")
class SetAllianceRed: LinearOpMode() {
    override fun runOpMode() {
        telemetry.addLine("Press start to set OpMode color to Red.")
        waitForStart()
        if (!isStopRequested) {
            OpModeData.alliance = OpModeData.Alliance.RED
            telemetry.addLine("Alliance Color was set to Red.")
        }
    }
}