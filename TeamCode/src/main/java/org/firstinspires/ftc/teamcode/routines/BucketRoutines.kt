package org.firstinspires.ftc.teamcode.routines

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup
import com.rowanmcalpin.nextftc.core.command.groups.ParallelRaceGroup
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay
import com.rowanmcalpin.nextftc.pedro.FollowPath
import org.firstinspires.ftc.teamcode.TrajectoryBuilder

object BucketRoutines {
    val firstSample: Command
        get() = ParallelGroup(
                FollowPath(TrajectoryBuilder.startToBucket, true),
                MechanismRoutines.sampleHigh
            )

    val secondSample: Command
        get() = SequentialGroup(
            ParallelGroup(
                MechanismRoutines.bucketDropAndReset,
                SequentialGroup(
                    Delay(0.5),
                    ParallelGroup(
                        ParallelRaceGroup(
                            MechanismRoutines.farIntakeAuto,
                            Delay(3.0)
                        ),
                        FollowPath(TrajectoryBuilder.bucketToFirstSample, true)
                    )
                )
            ),
            ParallelGroup(
                SequentialGroup(
                    MechanismRoutines.transfer,
                    MechanismRoutines.sampleHigh
                ),
                FollowPath(TrajectoryBuilder.firstSampleToBucket, true)
            )
        )

    val thirdSample: Command
        get() = SequentialGroup(
            ParallelGroup(
                MechanismRoutines.bucketDropAndReset,
                SequentialGroup(
                    Delay(0.5),
                    ParallelGroup(
                        ParallelRaceGroup(
                            MechanismRoutines.farIntakeAuto,
                            Delay(3.0)
                        ),
                        FollowPath(TrajectoryBuilder.bucketToSecondSample, true)
                    )
                )
            ),
            ParallelGroup(
                SequentialGroup(
                    MechanismRoutines.transfer,
                    MechanismRoutines.sampleHigh
                ),
                FollowPath(TrajectoryBuilder.secondSampleToBucket, true)
            )
        )

    val fourthSample: Command
        get() = SequentialGroup(
            ParallelGroup(
                MechanismRoutines.bucketDropAndReset,
                SequentialGroup(
                    Delay(0.5),
                    ParallelGroup(
                        ParallelRaceGroup(
                            MechanismRoutines.farIntakeAuto,
                            Delay(3.0)
                        ),
                        FollowPath(TrajectoryBuilder.bucketToThirdSample, true)
                    )
                )
            ),
            ParallelGroup(
                SequentialGroup(
                    MechanismRoutines.transfer,
                    MechanismRoutines.sampleHigh
                ),
                FollowPath(TrajectoryBuilder.thirdSampleToBucket, true)
            )
        )

    val park: Command
        get() = ParallelGroup(
            MechanismRoutines.bucketDropAndLevel1,
            SequentialGroup(
                Delay(0.5),
                FollowPath(TrajectoryBuilder.bucketToPark, true)
            )
        )
}