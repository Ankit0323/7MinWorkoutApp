package com.example.a7minuteworkout

class constants {
    companion object{
        fun exerciseList():ArrayList<exercise>{
           val list=ArrayList<exercise>()
           val runInPlace=exercise(1,"run In Place",
                   R.drawable.run_in_place,false,false)
               list.add(runInPlace)
            val pushups=exercise(2,"pushups",
                    R.drawable.pushups,false,false)
            list.add(pushups)
            val triceps=exercise(3,"triceps",
                    R.drawable.triceps,false,false)
            list.add(triceps)
            val lunges=exercise(4,"walking lunges",
                    R.drawable.lunges,false,false)
            list.add(lunges)
            val twistKnee=exercise(5,"twist Knee plank",
                    R.drawable.knee_plank,false,false)
            list.add(twistKnee)
            val tricepDips=exercise(6,"tricep Dips",
                    R.drawable.tricep_dips,false,false)
            list.add(tricepDips)
            val toeTouch=exercise(7,"toe Touch",
                    R.drawable.toe_touch,false,false)
            list.add(toeTouch)
            val russianTwist=exercise(8,"russian Twist",
                    R.drawable.russian_twist,false,false)
            list.add(russianTwist)
            val deadBug=exercise(9,"dead bug",
                    R.drawable.dead_bug,false,false)
            list.add(deadBug)
            val crossJack=exercise(10,"cross Jack",
                    R.drawable.cross_jacks,false,false)
            list.add(crossJack)
            val sideBend=exercise(11,"side Bend",
                    R.drawable.dumbell_side_bend,false,false)
            list.add(sideBend)
            val cossackSquat=exercise(12,"cossack Squat",
                    R.drawable.cossack_squat,false,false)
            list.add(cossackSquat)





          return list
        }
    }
}