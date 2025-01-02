package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.Servo
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition

@Config
object Intake: Subsystem() {
    lateinit var leftServo: Servo
    lateinit var rightServo: Servo

    @JvmField
    var leftName = "intake_left"
    @JvmField
    var rightName = "intake_right"

    val start: Command
        get() = MultipleServosToPosition(listOf(leftServo, rightServo), 1.0, this)

    val reverse: Command
        get() = MultipleServosToPosition(listOf(leftServo, rightServo), 0.0, this)

    val stop: Command
        get() = MultipleServosToPosition(listOf(leftServo, rightServo), 0.5, this)

    val slowForward: Command
        get() = MultipleServosToPosition(listOf(leftServo, rightServo), 0.6, this)

    override fun initialize() {
        leftServo = OpModeData.hardwareMap.get(Servo::class.java, leftName)
        rightServo = OpModeData.hardwareMap.get(Servo::class.java, rightName)
        rightServo.direction = Servo.Direction.REVERSE
    }
}