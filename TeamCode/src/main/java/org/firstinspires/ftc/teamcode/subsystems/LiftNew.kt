package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.ForcedParallelCommand
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.ResetEncoder
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition

@Config
object LiftNew: Subsystem() {

    override val defaultCommand: Command
        get() = HoldPosition(motorGroup, controller, this)

    lateinit var rightMotor: MotorEx // lift
    lateinit var leftMotor: MotorEx // lift2

    lateinit var motorGroup: MotorGroup

    @JvmField
    val coefficients = PIDCoefficients(0.007, 0.0, 0.0)

    @JvmField
    var kF: Double = 0.13

    val controller = PIDFController(coefficients, { kF }, setPointTolerance = 20.0)

    @JvmField
    var attempted = false

    @JvmField
    var intakePos = -10.0
    @JvmField
    var specimenPickupPos = 107.0
    @JvmField
    var highPos = 4000.0
    @JvmField
    var slightlyHighPos = 400.0
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
        get() = RunToPosition(motorGroup, specimenPickupPos, controller, this)

    val toHigh: Command
        get() = RunToPosition(motorGroup, highPos, controller, this)

    val toSlightlyHigh: Command
        get() = RunToPosition(motorGroup, slightlyHighPos, controller, this)


    val toSpecimenScore: Command
        get() =
            RunToPosition(motorGroup, specimenScorePos, controller, this)


    val toAutonomousSpecScore: Command
        get() =
            RunToPosition(motorGroup, specimenAutonomousScorePos, controller, this)

    val toFirstAutonomousSpecScore: Command
        get() =
            RunToPosition(motorGroup, firstAutonomousSpecimenScorePos, controller, this)


    val toHang: Command
        get() = RunToPosition(motorGroup, hangPos, controller, this)

    override fun initialize() {
        rightMotor = MotorEx(rightMotorName).reverse()
        leftMotor = MotorEx(leftMotorName)
        motorGroup = MotorGroup(rightMotor, leftMotor)
    }
}