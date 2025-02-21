package org.firstinspires.ftc.teamcode

import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierCurve
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Path
import com.pedropathing.pathgen.PathChain
import com.pedropathing.pathgen.Point
import com.rowanmcalpin.nextftc.core.toRadians
import com.rowanmcalpin.nextftc.pedro.FollowerNotInitializedException
import com.rowanmcalpin.nextftc.pedro.PedroData.follower


object TrajectoryBuilder {
    //region PEDRO PATHING
    val startPose = Pose(
        8.5,
        112.5,
        0.0.toRadians
    )
    val scorePose = Pose(
        25.0,
        127.0,
        (-45.0).toRadians
    )
    val sample1 = Pose(
        25.0,
        122.0,
        0.0.toRadians
    )
    val sample2 = Pose(
        25.0,
        133.0,
        0.0.toRadians
    )
    val sample3 = Pose(
        28.0,
        125.5,
        45.0.toRadians
    )
    val parkPose = Pose(
        60.0,
        86.0,
        90.0.toRadians
    )

    lateinit var startToBucket: PathChain

    lateinit var bucketToFirstSample: PathChain
    lateinit var pickupFirstSample: PathChain
    lateinit var firstSampleToBucket: PathChain

    lateinit var bucketToSecondSample: PathChain
    lateinit var pickupSecondSample: PathChain
    lateinit var secondSampleToBucket: PathChain

    lateinit var bucketToThirdSample: PathChain
    lateinit var pickupThirdSample: PathChain
    lateinit var thirdSampleToBucket: PathChain

    lateinit var bucketToPark: PathChain

    fun buildBucketPaths() {
        if (follower == null) {
            throw FollowerNotInitializedException()
        }

        startToBucket = follower!!.pathBuilder()
            .addPath(
                BezierCurve(
                    Point(
                        startPose
                    ),
                    Point(
                        23.971,
                        118.804,
                        Point.CARTESIAN
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setLinearHeadingInterpolation(startPose.heading, scorePose.heading)
            .build()

        bucketToFirstSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        scorePose
                    ),
                    Point(
                        sample1
                    )
                )
            )
            .setLinearHeadingInterpolation(scorePose.heading, sample1.heading)
            .build()

        pickupFirstSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample1
                    ),
                    Point(
                        sample1.x+3, sample1.y
                    )
                )
            )
            .setLinearHeadingInterpolation(scorePose.heading, sample1.heading)
            .build()

        firstSampleToBucket = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample1.x+3, sample1.y
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setLinearHeadingInterpolation(sample1.heading, scorePose.heading)
            .build()

        bucketToSecondSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        scorePose
                    ),
                    Point(
                        sample2
                    )
                )
            )
            .setLinearHeadingInterpolation(scorePose.heading, sample2.heading)
            .build()

        pickupSecondSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample2.x, sample2.y+3
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setConstantHeadingInterpolation(sample2.heading)
            .build()

        secondSampleToBucket = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample2.x, sample2.y+3
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setLinearHeadingInterpolation(sample2.heading, scorePose.heading)
            .build()

        bucketToThirdSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        scorePose
                    ),
                    Point(
                        sample3
                    )
                )
            )
            .setLinearHeadingInterpolation(scorePose.heading, sample3.heading)
            .build()

        pickupThirdSample = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample3.x+3, sample3.y
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setConstantHeadingInterpolation(sample3.heading)
            .build()

        thirdSampleToBucket = follower!!.pathBuilder()
            .addPath(
                BezierLine(
                    Point(
                        sample3.x+3, sample3.y
                    ),
                    Point(
                        scorePose
                    )
                )
            )
            .setLinearHeadingInterpolation(sample3.heading, scorePose.heading)
            .build()

        bucketToPark = follower!!.pathBuilder()
            .addPath(
                BezierCurve(
                    Point(
                        scorePose
                    ),
                    Point(
                        63.689,
                        115.655,
                        Point.CARTESIAN
                    ),
                    Point(
                        58.790,
                        119.679,
                        Point.CARTESIAN
                    ),
                    Point(
                        parkPose
                    )
                )
            )
            .setLinearHeadingInterpolation(scorePose.heading, parkPose.heading)
            .build()
    }
    //endregion
}