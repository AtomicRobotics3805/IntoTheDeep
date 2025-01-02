package org.firstinspires.ftc.teamcode.subsystems

import android.graphics.Color
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.ftc.OpModeData
import org.firstinspires.ftc.teamcode.DotStarLEDNew

object Lights: Subsystem() {
    lateinit var ledsLeft: DotStarLEDNew
    lateinit var ledsRight: DotStarLEDNew

    var color = 0

    override fun initialize() {
        ledsLeft = DotStarLEDNew((OpModeData.opMode as LinearOpMode))
        ledsRight = DotStarLEDNew((OpModeData.opMode as LinearOpMode))
        ledsLeft.setDataPin("dataLeft")
        ledsRight.setDataPin("dataRight")
        ledsLeft.setClockPin("clockLeft")
        ledsRight.setClockPin("clockRight")
        ledsLeft.init(14)
        ledsRight.init(14)

        color = Color.rgb(177, 223, 107)
        for (i in ledsLeft.pixels.indices) {
                // Update individual pixels with their new color.
                ledsLeft.setPixel(i, color)
                ledsRight.setPixel(i, color)
            }
        ledsRight.update()
        ledsLeft.update()

        ledsLeft.maxOutputAmps = 0.8
        ledsRight.maxOutputAmps = 0.8
    }

    class DisplayColor: Command() {
        override val isDone = false

        override fun update() {
            color = determineDisplayColor()
//            for (i in ledsLeft.pixels.indices) {
//                // Update individual pixels with their new color.
//                ledsLeft.setPixel(i, color)
//                ledsRight.setPixel(i, color)
//            }
//            ledsRight.update()
            ledsLeft.update()
        }

        override fun stop(interrupted: Boolean) {
            ledsLeft.clear()
            ledsRight.clear()
            ledsLeft.update()
            ledsRight.update()
        }
    }

    fun determineDisplayColor(): Int {
        return when(IntakeSensor.detectedColor) {
            OpModeData.Alliance.RED -> Color.rgb(255, 0, 0)
            OpModeData.Alliance.BLUE -> Color.rgb(0, 0, 255)
            OpModeData.Alliance.NONE -> Color.rgb(255, 255, 0)
            else -> Color.rgb(177, 223, 107)
        }
    }
}