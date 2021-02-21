package com.example.calc4kids

import android.content.SharedPreferences

object MyVariables {

    var currentDifficulty: Difficulty = Difficulty.EASY
    var currentActivity: CalculationType = CalculationType.ADDITION
    var currentDisplayType: DisplayType = DisplayType.ICONS

    enum class Difficulty{
        EASY,MID,HARD
    }

    enum class CalculationType{
        ADDITION,SUBTRACTION,MULTIPLICATION,DIVISION
    }

    enum class DisplayType{
        NUMBERS,ICONS
    }



    fun saveGame(pref: SharedPreferences?){
        val editor =pref?.edit()

        editor?.putInt("currentDifficulty",1)
        editor?.putInt("currentActivity",1)
        editor?.putInt("currentDisplayType",1)

        editor?.apply()
    }

    fun loadGame(pref: SharedPreferences?){
        currentDifficulty = when(pref?.getInt("currentDifficulty",1)){
            1 -> Difficulty.EASY
            2 -> Difficulty.MID
            3 -> Difficulty.HARD
            else ->  Difficulty.EASY
        }

        currentActivity = when (pref?.getInt("currentActivity",1)){
            1 -> CalculationType.ADDITION
            2 -> CalculationType.SUBTRACTION
            3 -> CalculationType.MULTIPLICATION
            4 -> CalculationType.DIVISION
            else -> CalculationType.ADDITION
        }

        currentDisplayType = when (pref?.getInt("currentDisplayType",1)){
            1 -> DisplayType.ICONS
            2 -> DisplayType.NUMBERS
            else -> DisplayType.ICONS
        }

    }
}