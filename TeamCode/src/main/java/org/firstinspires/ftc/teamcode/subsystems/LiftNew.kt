package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.ForcedParallelCommand
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDFCoefficients
import com.rowanmcalpin.nextftc.core.control.controllers.PIDController
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsHoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleMotorsToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.ResetEncoder
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower
import kotlin.math.absoluteValue

@Config
object LiftNew: Subsystem() {
    lateinit var rightMotor: MotorEx // lift
    lateinit var leftMotor: MotorEx // lift2

    lateinit var motorGroup: MotorGroup

    @JvmField
    val controller = PIDFController(PIDFCoefficients(0.01, 0.0, 0.0, 0.13))

    @JvmField
    var intakePos = -10.0
    @JvmField
    var specimenPickupPos = 107.0
    @JvmField
    var highPos = 4000.0
    @JvmField
    var slightlyHighPos = 720.0
    @JvmField
    var specimenScorePos = 277.0
    @JvmField
    var specimenAutonomousScorePos = 277.0
    @JvmField
    var firstAutonomousSpecimenScorePos = 253.0
    @JvmField
    var hangPos = 720.0

    @JvmField
    var rightMotorName = "lift"
    @JvmField
    var leftMotorName = "lift2"

    val resetEncoders: Command
        get() = ResetEncoder(motorGroup.leader, this)

    val toIntake: Command
        get() = RunToPosition(motorGroup, intakePos, controller, this)

    val toSpecimenPickup: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, specimenPickupPos, controller, this)
        )

    val toHigh: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, highPos, controller, this),
            HoldPosition(motorGroup, controller, this)
        )

    val toSlightlyHigh: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, slightlyHighPos, controller, this)
        )

    val toSpecimenScore: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, specimenScorePos, controller, this)
        )

    val toAutonomousSpecScore: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, specimenAutonomousScorePos, controller, this)
        )

    val toFirstAutonomousSpecScore: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, firstAutonomousSpecimenScorePos, controller, this)
        )

    val toHang: Command
        get() = SequentialGroup(
            RunToPosition(motorGroup, hangPos, controller, this)
        )

    override fun initialize() {
        rightMotor = MotorEx(rightMotorName).reverse()
        leftMotor = MotorEx(leftMotorName)
        motorGroup = MotorGroup(rightMotor, leftMotor)

        controller.setPointTolerance = 20.0
    }
}