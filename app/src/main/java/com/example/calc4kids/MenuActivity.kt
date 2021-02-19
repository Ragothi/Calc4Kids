package com.example.calc4kids

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity(){

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu)

        pref =  getSharedPreferences("my_shared_preferences", MODE_PRIVATE)

        val multiplication: Button = findViewById(R.id.multiplication)
        val addition: Button = findViewById(R.id.addition)
        val subtraction: Button = findViewById(R.id.subtraction)
        val division: Button = findViewById(R.id.division)
        val list: ArrayList<Button> = ArrayList()
        list.add(addition)
        list.add(subtraction)
        list.add(multiplication)
        list.add(division)

        val settings: Button = findViewById(R.id.settings)

        for (but in list){
            but.setOnClickListener {
                startActivity(Intent(this,CountingActivity::class.java))
                when(but){
                    list[0] -> MyVariables.currentActivity = MyVariables.CalculationType.ADDITION
                    list[1] -> MyVariables.currentActivity = MyVariables.CalculationType.SUBTRACTION
                    list[2] -> MyVariables.currentActivity = MyVariables.CalculationType.MULTIPLICATION
                    list[3] -> MyVariables.currentActivity = MyVariables.CalculationType.DIVISION

                }
            }
        }



        settings.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        MyVariables.saveGame(pref)
    }


}