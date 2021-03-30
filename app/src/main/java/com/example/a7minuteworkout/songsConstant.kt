package com.example.a7minuteworkout

class songsConstant {
    companion object {
        fun songsList(): ArrayList<songs> {
            var workoutSongs = ArrayList<songs>()


            val first = songs(1, R.raw.first)
            workoutSongs.add(first)
            val second = songs(2, R.raw.second)
            workoutSongs.add(second)
            val third = songs(3, R.raw.third)
            workoutSongs.add(third)
            val fourth = songs(4, R.raw.fourth)
            workoutSongs.add(fourth)
            val fifth = songs(5, R.raw.fifth)
            workoutSongs.add(fifth)
            val sixth = songs(6, R.raw.sixth)
            workoutSongs.add(sixth)
            val seventh = songs(7, R.raw.seventh)
            workoutSongs.add(seventh)
            val eighth = songs(8, R.raw.eighth)
            workoutSongs.add(eighth)
            val ninth = songs(9, R.raw.ninth)
            workoutSongs.add(ninth)
            val tenth = songs(10, R.raw.tenth)
            workoutSongs.add(tenth)
            val eleventh = songs(11, R.raw.eleventh)
            workoutSongs.add(eleventh)
            val twelth = songs(12, R.raw.twelth)
            workoutSongs.add(twelth)



            return workoutSongs

        }
    }
}