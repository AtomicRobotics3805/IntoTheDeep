package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.pedro.DriverControlled
import com.rowanmcalpin.nextftc.pedro.PedroOpMode
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.FConstants
import org.firstinspires.ftc.teamcode.LConstants
import org.firstinspires.ftc.teamcode.routines.MechanismRoutines
import org.firstinspires.ftc.teamcode.subsystems.Arm
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.subsystems.IntakePivot
import org.firstinspires.ftc.teamcode.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.subsystems.LiftNew


@TeleOp(name = "Testing TeleOp")
class TestTeleOp: PedroOpMode(Claw, Intake, Arm, IntakeExtension, IntakePivot, IntakeSensor, LiftNew) {

    val fConstants: FConstants = FConstants
    val lConstants: LConstants = LConstants

    private val startPose = Pose(0.0, 0.0, 0.0)

    override fun onInit() {
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap)
        follower.setStartingPose(startPose)

        IntakeSensor.Detect()()
//        Lights.DisplayColor()()

        IntakeExtension.resetEncoders()
        LiftNew.resetEncoders()
        Arm.toIntake()
        IntakePivot.transfer()

        OpModeData.telemetry = telemetry
    }

    override fun onStartButtonPressed() {
        DriverControlled(gamepadManager.gamepad1, false, invertStrafe = true, invertTurn = true)()
        registerControls()
    }

    override fun onUpdate() {
        telemetry.addData("Color", IntakeSensor.detectedColor)
        telemetry.addData("Distance", IntakeSensor.sensor.getDistance(DistanceUnit.CM))
        telemetry.addData("Lift power left", LiftNew.leftMotor.power)
        telemetry.addData("Lift power right", LiftNew.rightMotor.power)
        telemetry.addData("Lift target position", LiftNew.controller.target)
        telemetry.addData("Lift current position", LiftNew.motorGroup.currentPosition)
        telemetry.update()
    }

    private fun registerControls() {
        gamepadManager.gamepad1.a.pressedCommand = { MechanismRoutines.farIntake }
        gamepadManager.gamepad1.dpadRight.pressedCommand = { MechanismRoutines.eject }

        gamepadManager.gamepad1.dpadUp.pressedCommand = { LiftNew.toHigh }
        gamepadManager.gamepad1.dpadDown.pressedCommand = { LiftNew.toIntake }
        gamepadManager.gamepad1.dpadLeft.pressedCommand = { LiftNew.toSlightlyHigh }

        gamepadManager.gamepad1.b.pressedCommand = { MechanismRoutines.transfer }

        var clawOpen: Boolean = false

        gamepadManager.gamepad1.a.pressedCommand = {
            if (clawOpen) {
                clawOpen = false
                Claw.close
            } else {
                clawOpen = true
                Claw.open
            }
        }
    }
}