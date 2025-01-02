package org.firstinspires.ftc.teamcode.subsystems

import android.graphics.Color
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil
import com.rowanmcalpin.nextftc.ftc.OpModeData
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

@Config
object IntakeSensor: Subsystem() {
    
    lateinit var sensor: RevColorSensorV3
    
    @JvmField
    var sensorName = "intake_sensor"
    @JvmField
    var distanceThreshold = 3 // CM
    
    var detectedColor: OpModeData.Alliance? = null
    
    private var hsv: FloatArray = FloatArray(3)

    class WaitUntilSample: Command() {
        override val isDone: Boolean
            get() = sensor.getDistance(DistanceUnit.CM) < distanceThreshold // If we have a sample less than 3cm away, we're good to continue
    }

    class Detect: Command() {
        override val isDone = false

        override fun update() {
            if (sensor.getDistance(DistanceUnit.CM) < distanceThreshold) {
                Color.colorToHSV(sensor.normalizedColors.toColor(), hsv)
                detectedColor = if (hsv[0] <= 26) 
                    OpModeData.Alliance.RED else if (hsv[0] <= 85)
                        OpModeData.Alliance.NONE else
                            OpModeData.Alliance.BLUE
            } else {
                detectedColor = null
            }
        }
    }

    override fun initialize() {
        sensor = OpModeData.hardwareMap.get(RevColorSensorV3::class.java, sensorName)
        sensor.enableLed(true)
    }
}