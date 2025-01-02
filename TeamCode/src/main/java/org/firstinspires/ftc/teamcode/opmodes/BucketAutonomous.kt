package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.Follower
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.pedro.PedroOpMode
import org.firstinspires.ftc.teamcode.FConstants
import org.firstinspires.ftc.teamcode.LConstants
import org.firstinspires.ftc.teamcode.TrajectoryBuilder
import org.firstinspires.ftc.teamcode.routines.BucketRoutines
import org.firstinspires.ftc.teamcode.subsystems.Arm
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.subsystems.IntakePivot
import org.firstinspires.ftc.teamcode.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.subsystems.LiftNew

@Autonomous(name = "Bucket Auto")
class BucketAutonomous: PedroOpMode(Arm, Claw, Intake, IntakeExtension, IntakePivot, IntakeSensor, LiftNew) {


    override fun onInit() {
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap)
        follower.setStartingPose(TrajectoryBuilder.startPose)

        OpModeData.telemetry = telemetry
    }

    override fun onStartButtonPressed() {
        CommandManager.scheduleCommand(
            SequentialGroup(
                BucketRoutines.firstSample,
                BucketRoutines.secondSample,
                BucketRoutines.thirdSample,
                BucketRoutines.fourthSample,
                BucketRoutines.park
            )
        )
    }
}