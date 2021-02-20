package com.example.calc4kids

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.graphics.drawable.DrawableCompat
import java.util.function.Function
import kotlin.random.Random.Default.nextInt

class VoiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_voice)

        val LU: Button = findViewById(R.id.LU)
        val LD: Button = findViewById(R.id.LD)
        val RU: Button = findViewById(R.id.RU)
        val RD: Button = findViewById(R.id.RD)
        val questionView: TextView = findViewById(R.id.question)

        var question: Int

        val answers: ArrayList<Int> = ArrayList()
        for (i in 1..10){
            answers.add(i)
        }

        val list: ArrayList<Button> = ArrayList()
        list.add(LU)
        list.add(LD)
        list.add(RU)
        list.add(RD)

        fun displayIcons(but: Button,answer: Int){
            but.text = "$answer"
        }

        fun rollQuestion(){
            answers.shuffle()
            question=answers[8]
            questionView.text = "$question"
            val correctButton: Button = list.random()
            displayIcons(correctButton,question)
            for (i in 0..3){
                if (list[i] != correctButton){
                    displayIcons(list[i],answers[i] )
                }
            }
        }

        rollQuestion()

        questionView.setOnClickListener(){
            rollQuestion()
        }

        val generatedContainer: ConstraintLayout = findViewById(R.id.generatedContainer)



        manageDisplay(4,R.drawable.tulip,generatedContainer)

    }

    fun manageDisplay(num:Int,skillImage: Int,generatedContainer: ConstraintLayout){

        val cs = ConstraintSet()
        val list: ArrayList<ImageView> = ArrayList()

        for (i in 0..(num-1)){
            val temp = ImageView(this)
            temp.setImageResource(skillImage)
            temp.id = View.generateViewId()
            generatedContainer.addView(temp)
            list.add(temp)
            temp.bringToFront()
        }

        when(num){
            1->{
                cs.connect(list[0].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[0].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[0].id, ConstraintSet.START,generatedContainer.id, ConstraintSet.START,0)
                cs.connect(list[0].id, ConstraintSet.END,generatedContainer.id, ConstraintSet.END,0)
            }
            2-> {
                cs.connect(list[0].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[0].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[0].id, ConstraintSet.START,generatedContainer.id, ConstraintSet.START,0)
                cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                cs.connect(list[1].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[1].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                cs.connect(list[1].id, ConstraintSet.END,generatedContainer.id, ConstraintSet.END,0)
            }
            3 -> {
                cs.connect(list[0].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                cs.connect(list[0].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[0].id, ConstraintSet.START,generatedContainer.id, ConstraintSet.START,0)
                cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                cs.connect(list[1].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                cs.connect(list[1].id, ConstraintSet.BOTTOM,list[0].id, ConstraintSet.TOP,0)
                cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                cs.connect(list[1].id, ConstraintSet.END,list[2].id, ConstraintSet.START,0)

                cs.connect(list[2].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[2].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.TOP,0)
                cs.connect(list[2].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                cs.connect(list[2].id, ConstraintSet.END,generatedContainer.id, ConstraintSet.END,0)
            }
            4 -> {
                cs.connect(list[0].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[0].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                cs.connect(list[0].id, ConstraintSet.START,generatedContainer.id, ConstraintSet.START,0)
                cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                cs.connect(list[1].id, ConstraintSet.TOP,generatedContainer.id, ConstraintSet.TOP,0)
                cs.connect(list[1].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                cs.connect(list[1].id, ConstraintSet.END,generatedContainer.id, ConstraintSet.END,0)

                cs.connect(list[2].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                cs.connect(list[2].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[2].id, ConstraintSet.START,generatedContainer.id, ConstraintSet.START,0)
                cs.connect(list[2].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                cs.connect(list[3].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                cs.connect(list[3].id, ConstraintSet.BOTTOM,generatedContainer.id, ConstraintSet.BOTTOM,0)
                cs.connect(list[3].id, ConstraintSet.START,list[2].id, ConstraintSet.END,0)
                cs.connect(list[3].id, ConstraintSet.END,generatedContainer.id, ConstraintSet.END,0)
            }
        }

        cs.applyTo(generatedContainer)
        generatedContainer.bringToFront()

    }

}