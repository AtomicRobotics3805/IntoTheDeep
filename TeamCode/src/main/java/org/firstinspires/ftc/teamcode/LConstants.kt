package org.firstinspires.ftc.teamcode

import com.pedropathing.localization.Encoder
import com.pedropathing.localization.constants.TwoWheelConstants
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot


object LConstants {
    init {
        TwoWheelConstants.forwardTicksToInches = 0.0029801454
        TwoWheelConstants.strafeTicksToInches = 0.003024153

        TwoWheelConstants.forwardY = 4.4469
        TwoWheelConstants.strafeX = -5.3917

        TwoWheelConstants.IMU_Orientation = RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
        )

        TwoWheelConstants.forwardEncoder_HardwareMapName = "RB"
        TwoWheelConstants.strafeEncoder_HardwareMapName = "LB"

        TwoWheelConstants.forwardEncoderDirection = Encoder.REVERSE
        TwoWheelConstants.strafeEncoderDirection = Encoder.FORWARD
    }
}