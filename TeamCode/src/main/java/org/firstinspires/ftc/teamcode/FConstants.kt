package org.firstinspires.ftc.teamcode

import com.pedropathing.follower.FollowerConstants
import com.pedropathing.localization.Localizers
import com.pedropathing.util.CustomFilteredPIDFCoefficients
import com.pedropathing.util.CustomPIDFCoefficients
import com.qualcomm.robotcore.hardware.DcMotorSimple


object FConstants {
    init {
        FollowerConstants.localizers = Localizers.TWO_WHEEL

        FollowerConstants.leftFrontMotorName = "LF"
        FollowerConstants.leftRearMotorName = "LB"
        FollowerConstants.rightFrontMotorName = "RF"
        FollowerConstants.rightRearMotorName = "RB"

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD

        FollowerConstants.mass = 12.88202

        FollowerConstants.xMovement = 75.0
        FollowerConstants.yMovement = 53.29521002371128

        FollowerConstants.forwardZeroPowerAcceleration = -27.643286510947192
        FollowerConstants.lateralZeroPowerAcceleration = -74.68689318834973

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.09, 0.0, 0.01, 0.0)

        FollowerConstants.useSecondaryTranslationalPID = false
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(
            0.1,
            0.0,
            0.01,
            0.0
        ) // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(1.5, 0.0, 0.1, 0.0)

        FollowerConstants.useSecondaryHeadingPID = false
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(
            2.0,
            0.0,
            0.1,
            0.0
        ) // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.015, 0.0, 0.002, 0.6, 0.0)

        FollowerConstants.useSecondaryDrivePID = true
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(
            0.005,
            0.0,
            0.0005,
            0.6,
            0.0
        ) // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 4.4
        FollowerConstants.centripetalScaling = 0.0015

        FollowerConstants.pathEndTimeoutConstraint = 200.0
        FollowerConstants.pathEndTValueConstraint = 0.95
        FollowerConstants.pathEndVelocityConstraint = 0.1
        FollowerConstants.pathEndTranslationalConstraint = 0.1
        FollowerConstants.pathEndHeadingConstraint = 0.007
        
        FollowerConstants.useBrakeModeInTeleOp = true
    }
}