package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.Follower
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.pedro.PedroOpMode
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
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

    val fConstants: FConstants = FConstants
    val lConstants: LConstants = LConstants

    override fun onInit() {
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap)
        follower.resetIMU()
        follower.setStartingPose(TrajectoryBuilder.startPose)

        IntakeSensor.Detect()()

        IntakeExtension.resetEncoder()
        LiftNew.resetEncoders()
        Arm.toIntake()
        IntakePivot.transfer()
        Claw.close()

        OpModeData.telemetry = telemetry
    }

    override fun onUpdate() {
//        telemetry.addData("Color", IntakeSensor.detectedColor)
//        telemetry.addData("Distance", IntakeSensor.sensor.getDistance(DistanceUnit.CM))
//        telemetry.addData("Lift power left", LiftNew.leftMotor.power)
//        telemetry.addData("Lift power right", LiftNew.rightMotor.power)
//        telemetry.addData("Lift target position", LiftNew.controller.target)
//        telemetry.addData("Lift current position", LiftNew.motorGroup.currentPosition)
//        telemetry.update()

    }

    override fun onStartButtonPressed() {

        TrajectoryBuilder.buildBucketPaths()

        CommandManager.scheduleCommand(
            SequentialGroup(
                BucketRoutines.firstSample,
                BucketRoutines.secondSample,
                BucketRoutines.thirdSample,
                BucketRoutines.fourthSample,
                Delay(1.0),
                BucketRoutines.park
            )
        )
    }
}