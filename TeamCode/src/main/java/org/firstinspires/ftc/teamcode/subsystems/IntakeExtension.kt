package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.NullCommand
import com.rowanmcalpin.nextftc.core.control.coefficients.PIDCoefficients
import com.rowanmcalpin.nextftc.core.control.controllers.PIDController
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MotorHoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.MotorToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.ResetEncoder
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition

@Config
object IntakeExtension: Subsystem() {
    lateinit var motor: MotorEx
    
    val controller = PIDFController(PIDCoefficients(0.005, 0.0, 0.0))

    override val defaultCommand
        get() = HoldPosition(motor, controller, this)

    @JvmField
    var transferPos = 200.0
    @JvmField
    var outPos = 1200.0 // TODO
    @JvmField
    var slightlyOutPos = 700.0 // TODO
    @JvmField
    var middlePos = 600.0
    
    @JvmField
    var motorName = "intake_extension"
    
    val toTransfer: Command
        get() = RunToPosition(motor, transferPos, controller, this)
    
    val toOut: Command
        get() = RunToPosition(motor, outPos, controller, this)
    
    val toSlightlyOut: Command
        get() = RunToPosition(motor, slightlyOutPos, controller, this)
    
    val toMiddlePos: Command
        get() = RunToPosition(motor, middlePos, controller, this)

    val resetEncoder: Command
        get() = ResetEncoder(motor, this)

    override fun initialize() {
        motor = MotorEx(motorName)
        motor.direction = DcMotorSimple.Direction.REVERSE
        controller.setPointTolerance = 30.0
    }
}