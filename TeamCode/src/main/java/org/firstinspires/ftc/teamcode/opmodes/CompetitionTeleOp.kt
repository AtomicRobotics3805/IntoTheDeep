package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.FollowerConstants
import com.pedropathing.localization.Pose
import com.pedropathing.localization.PoseUpdater
import com.pedropathing.localization.constants.TwoWheelConstants
import com.pedropathing.util.Constants
import com.pedropathing.util.DashboardPoseTracker
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.IMU
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand
import com.rowanmcalpin.nextftc.core.toRadians
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode
import com.rowanmcalpin.nextftc.ftc.OpModeData
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.teamcode.FConstants
import org.firstinspires.ftc.teamcode.LConstants
import org.firstinspires.ftc.teamcode.MecanumDriverControlledFixed
import org.firstinspires.ftc.teamcode.routines.MechanismRoutines
import org.firstinspires.ftc.teamcode.subsystems.Arm
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.IntakeExtension
import org.firstinspires.ftc.teamcode.subsystems.IntakePivot
import org.firstinspires.ftc.teamcode.subsystems.IntakeSensor
import org.firstinspires.ftc.teamcode.subsystems.LiftNew

@TeleOp(name = "Competition TeleOp")
class CompetitionTeleOp: NextFTCOpMode() {

    val fConstants: FConstants = FConstants
    val lConstants: LConstants = LConstants

    private var poseUpdater: PoseUpdater? = null
    private var dashboardPoseTracker: DashboardPoseTracker? = null

    private val startPose = Pose(0.0, 0.0, 90.0.toRadians)

    private lateinit var leftFront: MotorEx
    private lateinit var leftRear: MotorEx
    private lateinit var rightFront: MotorEx
    private lateinit var rightRear: MotorEx

    lateinit var motors: Array<MotorEx>

    lateinit var imu: IMU

    lateinit var driverControlled: MecanumDriverControlledFixed


    override fun onInit() {
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        poseUpdater = PoseUpdater(hardwareMap)

        dashboardPoseTracker = DashboardPoseTracker(poseUpdater)

        leftFront = MotorEx(FollowerConstants.leftFrontMotorName)
        leftRear = MotorEx(FollowerConstants.leftRearMotorName)
        rightRear = MotorEx(FollowerConstants.rightRearMotorName)
        rightFront = MotorEx(FollowerConstants.rightFrontMotorName)

        motors = arrayOf(leftFront, rightFront, leftRear, rightRear)

        leftFront.direction = FollowerConstants.leftFrontMotorDirection
        leftRear.direction = FollowerConstants.leftRearMotorDirection
        rightFront.direction = FollowerConstants.rightFrontMotorDirection
        rightRear.direction = FollowerConstants.rightRearMotorDirection

        imu = hardwareMap.get(IMU::class.java, "imu")
        imu.initialize(IMU.Parameters(TwoWheelConstants.IMU_Orientation))
        imu.resetYaw()

        motors.forEach {
            it.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }

        IntakeSensor.Detect()()
//        Lights.DisplayColor()()

        IntakeExtension.resetEncoder()
        LiftNew.resetEncoders()
        Arm.toIntake()
        IntakePivot.transfer()

        OpModeData.telemetry = telemetry
    }

    override fun onStartButtonPressed() {
        driverControlled = MecanumDriverControlledFixed(motors, gamepadManager.gamepad1, false, imu)
        driverControlled()
        driverControlled.orientation = 90.0.toRadians
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
        gamepadManager.gamepad1.dpadUp.pressedCommand = { IntakeExtension.toSlightlyOut }
        // COMPETITION CONTROLS
        gamepadManager.gamepad1.a.pressedCommand = { MechanismRoutines.farIntake }
        gamepadManager.gamepad1.leftBumper.pressedCommand = { MechanismRoutines.nearIntake }
        gamepadManager.gamepad2.rightTrigger.pressedCommand = { MechanismRoutines.bucketDropAndReset }
        gamepadManager.gamepad1.dpadRight.pressedCommand = { Intake.reverse }
        gamepadManager.gamepad1.rightBumper.pressedCommand = { InstantCommand({ driverControlled.scalar = 0.5 })}
        gamepadManager.gamepad1.rightBumper.releasedCommand = { InstantCommand({ driverControlled.scalar = 1.0 })}
        gamepadManager.gamepad1.x.pressedCommand = { InstantCommand({ driverControlled.orientation = 0.0 })}

        gamepadManager.gamepad2.x.pressedCommand = { MechanismRoutines.sampleHigh }
        gamepadManager.gamepad2.b.pressedCommand = { MechanismRoutines.transfer }
        gamepadManager.gamepad2.a.pressedCommand = { Claw.open }

        gamepadManager.gamepad2.dpadDown.pressedCommand = { LiftNew.toIntake }
    }
}