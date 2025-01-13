package org.firstinspires.ftc.teamcode.routines

import com.qualcomm.hardware.ams.AMSColorSensor.Wait
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.ForcedParallelCommand
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.BlockingConditionalCommand
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.PassiveSwitchCommand
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil
import com.rowanmcalpin.nextftc.ftc.OpModeData
import org.firstinspires.ftc.teamcode.subsystems.Arm
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.subsystems.IntakePivot
import org.firstinspires.ftc.teamcode.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.subsystems.LiftNew

object MechanismRoutines {
    //region Intake & Transfer

    // Far intake with pivot down at end (for submersible)
    val farIntake: Command
        get() = ParallelGroup(
            IntakeExtension.toOut,
            IntakePivot.transfer,
            SequentialGroup(
                Delay(0.5),
                IntakePivot.down,
                autoIntakeRoutine
            )
        )

    // Far intake with pivot down as quickly as possible (for auto or other samples on the field not in the submersible)
    val farIntakeAuto: Command
        get() = ParallelGroup(
            IntakeExtension.toIntakeAuto,
            IntakePivot.transfer,
            SequentialGroup(
                Delay(0.5),
                IntakePivot.down,
                autoIntakeRoutine
            )
        )

    val nearIntake: Command
        get() = SequentialGroup(
            ParallelGroup(
                IntakeExtension.toSlightlyOut,
                IntakePivot.transfer
            ),
            Delay(0.1),
            ParallelGroup(
                IntakePivot.down,
                intakeRoutine
            )
        )

    val autoIntakeRoutine: Command
        get() = SequentialGroup(
            Intake.start,
            IntakeSensor.WaitUntilSample(),
            Intake.slowForward
        )

    val intakeRoutine: Command
        get() = SequentialGroup(
            Intake.start,
            IntakeSensor.WaitUntilSample(),
            Intake.slowForward
        )

    val transfer: Command
        get() = SequentialGroup(
            IntakePivot.transfer,
            Arm.toIntake,
            Claw.open,
            LiftNew.toSlightlyHigh,
            IntakeExtension.toTransfer,
            LiftNew.toIntake,
            Claw.close,
            Intake.stop
        )

    val eject: Command
        get() = SequentialGroup(
            Intake.reverse
        )
    //endregion

    val sampleHigh: Command
        get() = SequentialGroup(
            Claw.close,
            ForcedParallelCommand(LiftNew.toHigh),
            WaitUntil({ LiftNew.motorGroup.currentPosition >= 300 }),
            ForcedParallelCommand(IntakeExtension.toSlightlyOut),
            WaitUntil({ LiftNew.motorGroup.currentPosition >= 600}),
            IntakeExtension.toTransfer,
            WaitUntil({ LiftNew.motorGroup.currentPosition >= 2000 }),
            Arm.toBasketScore,
            WaitUntil({ LiftNew.controller.atTarget(LiftNew.motorGroup.currentPosition) })
        )

    @Deprecated("Have not implemented low bucket yet")
    val sampleLow: Command
        get() = ParallelGroup(
            SequentialGroup(
                IntakeExtension.toSlightlyOut,
                IntakeExtension.toTransfer
            ),
            SequentialGroup(
                Delay(1.0),
                Arm.toBasketScore
            ),
            LiftNew.toHigh,
            IntakePivot.transfer
        )

    val bucketDropAndReset: Command
        get() = SequentialGroup(
            Claw.open,
            Delay(0.5),
            Arm.toIntake,
            Delay(0.5),
            LiftNew.toIntake
        )

    val bucketDropAndLevel1: Command
        get() = SequentialGroup(
            Claw.open,
            Delay(0.2),
            ParallelGroup(
                Arm.toBasketScore,
                LiftNew.toIntake
            )
        )

    val autoTransfer: Command
        get() = SequentialGroup(
            IntakePivot.transfer,
            Arm.toIntake,
            Claw.open,
            LiftNew.toSlightlyHigh,
            IntakeExtension.toAutoTransfer,
            LiftNew.toAutoTransferPos,
            Claw.close,
            Intake.stop,
            Delay(0.3)
        )
    val toHang: Command
        get() = ParallelGroup(
            Arm.toSpecimenScore,
            LiftNew.toHang
        )

    val specimenPickup: Command
        get() = SequentialGroup(
            ForcedParallelCommand(LiftNew.toSpecimenPickup),
            WaitUntil { LiftNew.motorGroup.currentPosition >= 40 },
            Arm.toSpecimenPickup,
            Delay(0.1),
            Claw.specimenOpen
        )

    val specimenScore: Command
        get() = SequentialGroup(
            Claw.close,
            Delay(0.3),
            ForcedParallelCommand(LiftNew.toSpecimenScore),
            WaitUntil { LiftNew.motorGroup.currentPosition >= 100 },
            Arm.toSpecimenScore
        )
}