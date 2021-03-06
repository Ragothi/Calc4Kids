package com.example.calc4kids

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.red
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat



class SettingsActivity : AppCompatActivity(){

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_settings)

        val easy: Button = findViewById(R.id.easy)
        val mid: Button = findViewById(R.id.advanced)
        val hard: Button = findViewById(R.id.hard)
        val numbers: Button = findViewById(R.id.numbers)
        val icons: Button = findViewById(R.id.icons)

        val list: ArrayList<Button> = ArrayList()
        list.add(easy)
        list.add(mid)
        list.add(hard)
        pref =  getSharedPreferences("my_shared_preferences", MODE_PRIVATE)


        fun setTint(drawable: Drawable, color: Int) :Drawable{
            val newDrawable: Drawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(newDrawable,color)
            return newDrawable
        }

        fun colorButton(red: Button){
            for (but in list){
                if (but.equals(red)){
                    setTint(but.background,Color.RED)
                } else {
                    setTint(but.background,Color.parseColor("#FF6D00"))
                }
            }
        }

        fun colorButton2(red: Button){
            if (red.equals(numbers)){
                setTint(numbers.background,Color.RED)
                setTint(icons.background,Color.parseColor("#FF6D00"))
            } else {
                setTint(icons.background,Color.RED)
                setTint(numbers.background,Color.parseColor("#FF6D00"))
            }
        }

        when (MyVariables.currentDifficulty){
            MyVariables.Difficulty.EASY ->  setTint(easy.background,Color.RED)
            MyVariables.Difficulty.MID -> setTint(mid.background,Color.RED)
            MyVariables.Difficulty.HARD ->  setTint(hard.background,Color.RED)
        }

        when (MyVariables.currentDisplayType){
            MyVariables.DisplayType.ICONS ->  setTint(icons.background,Color.RED)
            MyVariables.DisplayType.NUMBERS -> setTint(numbers.background,Color.RED)
        }

        easy.setOnClickListener {
            MyVariables.currentDifficulty = MyVariables.Difficulty.EASY
           colorButton(easy)
        }

        mid.setOnClickListener {
            MyVariables.currentDifficulty = MyVariables.Difficulty.MID
            colorButton(mid)
        }

        hard.setOnClickListener {
            MyVariables.currentDifficulty = MyVariables.Difficulty.HARD
            colorButton(hard)
        }

        numbers.setOnClickListener {
            MyVariables.currentDisplayType = MyVariables.DisplayType.NUMBERS
            colorButton2(numbers)
        }

        icons.setOnClickListener {
            MyVariables.currentDisplayType = MyVariables.DisplayType.ICONS
            colorButton2(icons)
        }

    }

    override fun onPause() {
        super.onPause()
        MyVariables.saveGame(pref)
    }

}