package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

object IntakePivot: Subsystem() {
    lateinit var servo: Servo

    @JvmField
    var name = "intake_pivot"

    @JvmField
    var transferPos = 0.6
    @JvmField
    var downPos = 0.9

    val transfer: Command
        get() = ServoToPosition(servo, transferPos, this)

    val down: Command
        get() = ServoToPosition(servo, downPos, this)

    override fun initialize() {
        servo = OpModeData.hardwareMap.get(Servo::class.java, name)
    }
}