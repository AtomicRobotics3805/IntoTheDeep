package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.rowanmcalpin.nextftc.core.control.controllers.SquidController
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode
import org.firstinspires.ftc.teamcode.subsystems.LiftNew

@Config
@TeleOp(name="PID Debug")
class PIDDebug: NextFTCOpMode(LiftNew) {

    companion object {
        @JvmField
        var kP: Double = 0.0
        @JvmField
        var kI: Double = 0.0
        @JvmField
        var kD: Double = 0.0
        @JvmField
        var kF: Double = 0.0

        @JvmField
        var targetPosition = 0.0
    }

    override fun onInit() {
        LiftNew.resetEncoders()
    }

    override fun onUpdate() {
        LiftNew.controller.coefficients.kP = kP
        LiftNew.controller.coefficients.kI = kI
        LiftNew.controller.coefficients.kD = kD
        LiftNew.controller.coefficients.kF = kF

        LiftNew.controller.target = targetPosition

        LiftNew.motorGroup.power = LiftNew.controller.calculate(LiftNew.motorGroup.currentPosition)

        telemetry.addData("Lift pos", LiftNew.motorGroup.currentPosition)
        telemetry.addData("Lift power", LiftNew.motorGroup.power)
        telemetry.addData("Lift target", LiftNew.controller.target)
        telemetry.update()
    }
}