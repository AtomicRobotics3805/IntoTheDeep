package org.firstinspires.ftc.teamcode.competition.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.ftc.OpModeData

@Autonomous(name = "Set Alliance Color BLUE")
class SetAllianceBlue: LinearOpMode() {
    override fun runOpMode() {
        telemetry.addLine("Press start to set OpMode color to Blue.")
        waitForStart()
        if (!isStopRequested) {
            OpModeData.alliance = OpModeData.Alliance.BLUE
            telemetry.addLine("Alliance Color was set to Blue.")
        }
    }
}