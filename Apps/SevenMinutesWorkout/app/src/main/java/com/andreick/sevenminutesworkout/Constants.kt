package com.andreick.sevenminutesworkout

object Constants {

    fun defaultExerciseList() :ArrayList<Exercise> {
        val exerciseList = ArrayList<Exercise>()

        val jumpingJacks = Exercise(
            1,
            "Jumping jacks",
            R.drawable.ic_jumping_jacks,
        )
        exerciseList.add(jumpingJacks)

        val wallSit = Exercise(
            2,
            "Wall Sit",
            R.drawable.ic_wall_sit,
        )
        exerciseList.add(wallSit)

        val pushUp = Exercise(
            3,
            "Push Up",
            R.drawable.ic_push_up,
        )
        exerciseList.add(pushUp)

        val abdominalCrunch = Exercise(
            4,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
        )
        exerciseList.add(abdominalCrunch)

        val stepUpIntoChair = Exercise(
            5,
            "Step Up into Chair",
            R.drawable.ic_step_up_onto_chair,
        )
        exerciseList.add(stepUpIntoChair)

        val squat = Exercise(
            6,
            "Squat",
            R.drawable.ic_squat,
        )
        exerciseList.add(squat)

        val tricepsDipOnChair = Exercise(
            7,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
        )
        exerciseList.add(tricepsDipOnChair)

        val plank = Exercise(
            8,
        "Plank",
        R.drawable.ic_plank,
        )
        exerciseList.add(plank)

        val highKnessRunningInPlace = Exercise(
            9,
            "High Kness Running In Place",
            R.drawable.ic_high_knees_running_in_place,
        )
        exerciseList.add(highKnessRunningInPlace)

        val lunge = Exercise(
            10,
            "Lunge",
            R.drawable.ic_lunge,
        )
        exerciseList.add(lunge)

        val pushUpAndRotation = Exercise(
            11,
            "Push Up and Rotation",
            R.drawable.ic_push_up_and_rotation,
        )
        exerciseList.add(pushUpAndRotation)

        return exerciseList
    }
}