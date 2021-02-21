package com.example.calc4kids

import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isVisible
import java.util.*
import kotlin.collections.ArrayList

class VoiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_voice)


        val questionView: TextView = findViewById(R.id.question)

        questionView.isGone = when (MyVariables.currentDisplayType){
            MyVariables.DisplayType.ICONS   ->  true
            MyVariables.DisplayType.NUMBERS -> false
        }

        var question: Int

        val answers: ArrayList<Int> = ArrayList()
        for (i in 1..10){
            answers.add(i)
        }

        val list: ArrayList<ContainerWithButton> = ArrayList()
        list.add(ContainerWithButton(findViewById(R.id.LU),findViewById(R.id.leftUpContainer)))
        list.add(ContainerWithButton(findViewById(R.id.LD),findViewById(R.id.leftDownContainer)))
        list.add(ContainerWithButton(findViewById(R.id.RU),findViewById(R.id.rightUpContainer)))
        list.add(ContainerWithButton(findViewById(R.id.RD),findViewById(R.id.rightDownContainer)))

        val graphics: ArrayList<Int> = ArrayList()
        graphics.add(R.drawable.tulip)
        graphics.add(R.drawable.heart)
        graphics.add(R.drawable.sunflower)
        graphics.add(R.drawable.daisy)
        graphics.add(R.drawable.chabr)

        fun displayIcons(but: Button,answer: Int){
            if (MyVariables.currentDisplayType.equals(MyVariables.DisplayType.NUMBERS)){
                but.text = "$answer"
            } else{
                but.text = ""
            }
        }

        fun rollQuestion(){
            answers.shuffle()
            question=answers[8]
            if (MyVariables.currentDisplayType.equals(MyVariables.DisplayType.ICONS)){
                playSound(question)
            }
            questionView.text = "$question"
            val correctButton: Button = list.random().button
            var index: Int = -1
            for (but in list){
                if (correctButton.equals(but.button)){
                    index=list.indexOf(but)
                    but.ans = question
                }
            }
            displayIcons(correctButton,question)
            manageDisplay(question,graphics.random(),list[index].container)
            for (i in 0..3){
                if (list[i].button != correctButton){
                    displayIcons(list[i].button,answers[i])
                    list[i].ans=answers[i]
                    manageDisplay(answers[i],graphics.random(),list[i].container)
                }
            }
        }

        rollQuestion()

        questionView.setOnClickListener(){
            rollQuestion()
        }

        val reaction: ImageView = findViewById(R.id.wonVoice)
        val next: Button = findViewById(R.id.nextVoiceBut)


        fun win(won: Boolean){
            for (but in list){
                but.container.isVisible = false
            }
            if (won){
                reaction.setImageResource(R.drawable.happy)
            } else {
                reaction.setImageResource(R.drawable.angry)
            }

            reaction.isVisible = true
            next.isVisible = true
            if  (MyVariables.currentDisplayType.equals(MyVariables.DisplayType.ICONS)){
                emptyContainers(list)
            }
        }

        for (but in list){
            but.button.setOnClickListener(){
                if (but.ans == answers[8]){
                    win(true)
                } else {
                    win(false)
                }
            }
        }

        next.setOnClickListener(){
            for (but in list){
                but.container.isVisible = true
            }
            reaction.isVisible = false
            next.isVisible = false
            rollQuestion()
        }


    }

    fun emptyContainers(list: ArrayList<ContainerWithButton>){
        for (l in list){
            l.container.removeAllViews()
            l.container.addView(l.button)
            val cs = ConstraintSet()
            cs.connect(l.button.id, ConstraintSet.TOP,l.container.id, ConstraintSet.TOP,0)
            cs.connect(l.button.id, ConstraintSet.BOTTOM,l.container.id, ConstraintSet.BOTTOM,0)
            cs.connect(l.button.id, ConstraintSet.START,l.container.id, ConstraintSet.START,0)
            cs.connect(l.button.id, ConstraintSet.END,l.container.id, ConstraintSet.END,0)
            cs.applyTo(l.container)
        }
    }


    fun manageDisplay(num:Int, skillImage: Int, container: ConstraintLayout){
        if (MyVariables.currentDisplayType.equals(MyVariables.DisplayType.ICONS)){
            val cs = ConstraintSet()
            val list: ArrayList<ImageView> = ArrayList()

            for (i in 0..(num-1)){
                val temp = ImageView(this)
                temp.setImageResource(skillImage)
                temp.id = View.generateViewId()
                container.addView(temp)
                list.add(temp)
                temp.bringToFront()
            }

            when(num){
                1 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                2 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                3 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[0].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,list[2].id, ConstraintSet.START,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                    cs.connect(list[2].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                4 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.START,list[2].id, ConstraintSet.END,0)
                    cs.connect(list[3].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                5 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[2].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[2].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[3].id, ConstraintSet.END,list[2].id, ConstraintSet.START,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.START,list[2].id, ConstraintSet.END,0)
                    cs.connect(list[4].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                6 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,list[4].id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,list[5].id, ConstraintSet.TOP,0)
                    cs.connect(list[3].id, ConstraintSet.START,list[2].id, ConstraintSet.END,0)
                    cs.connect(list[3].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[4].id, ConstraintSet.END,list[5].id, ConstraintSet.START,0)

                    cs.connect(list[5].id, ConstraintSet.TOP,list[3].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.START,list[4].id, ConstraintSet.END,0)
                    cs.connect(list[5].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                7 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[1].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[1].id, ConstraintSet.TOP,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                    cs.connect(list[3].id, ConstraintSet.END,list[5].id, ConstraintSet.START,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,list[5].id, ConstraintSet.TOP,0)
                    cs.connect(list[4].id, ConstraintSet.START,list[3].id, ConstraintSet.END,0)
                    cs.connect(list[4].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[5].id, ConstraintSet.TOP,list[4].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.BOTTOM,list[6].id, ConstraintSet.TOP,0)
                    cs.connect(list[5].id, ConstraintSet.START,list[3].id, ConstraintSet.END,0)
                    cs.connect(list[5].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[6].id, ConstraintSet.TOP,list[5].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[6].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[6].id, ConstraintSet.START,list[3].id, ConstraintSet.END,0)
                    cs.connect(list[6].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                8 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[7].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[1].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[7].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[1].id, ConstraintSet.END,list[2].id, ConstraintSet.START,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,list[7].id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                    cs.connect(list[2].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,list[4].id, ConstraintSet.TOP,0)
                    cs.connect(list[3].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                    cs.connect(list[3].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,list[3].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.START,list[1].id, ConstraintSet.END,0)
                    cs.connect(list[4].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[5].id, ConstraintSet.TOP,list[3].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.START,list[6].id, ConstraintSet.END,0)
                    cs.connect(list[5].id, ConstraintSet.END,list[4].id, ConstraintSet.START,0)

                    cs.connect(list[6].id, ConstraintSet.TOP,list[3].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[6].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[6].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[6].id, ConstraintSet.END,list[5].id, ConstraintSet.START,0)

                    cs.connect(list[7].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[7].id, ConstraintSet.BOTTOM,list[6].id, ConstraintSet.TOP,0)
                    cs.connect(list[7].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[7].id, ConstraintSet.END,list[0].id, ConstraintSet.END,0)
                }
                9 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[1].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[3].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,list[4].id, ConstraintSet.TOP,0)
                    cs.connect(list[3].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[3].id, ConstraintSet.END,list[6].id, ConstraintSet.START,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,list[3].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,list[5].id, ConstraintSet.TOP,0)
                    cs.connect(list[4].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[4].id, ConstraintSet.END,list[6].id, ConstraintSet.START,0)

                    cs.connect(list[5].id, ConstraintSet.TOP,list[4].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[5].id, ConstraintSet.END,list[6].id, ConstraintSet.START,0)

                    cs.connect(list[6].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[6].id, ConstraintSet.BOTTOM,list[7].id, ConstraintSet.TOP,0)
                    cs.connect(list[6].id, ConstraintSet.START,list[5].id, ConstraintSet.END,0)
                    cs.connect(list[6].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[7].id, ConstraintSet.TOP,list[6].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[7].id, ConstraintSet.BOTTOM,list[8].id, ConstraintSet.TOP,0)
                    cs.connect(list[7].id, ConstraintSet.START,list[5].id, ConstraintSet.END,0)
                    cs.connect(list[7].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[8].id, ConstraintSet.TOP,list[7].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[8].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[8].id, ConstraintSet.START,list[5].id, ConstraintSet.END,0)
                    cs.connect(list[8].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
                10 -> {
                    cs.connect(list[0].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.BOTTOM,list[1].id, ConstraintSet.TOP,0)
                    cs.connect(list[0].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[0].id, ConstraintSet.END,list[4].id, ConstraintSet.START,0)

                    cs.connect(list[1].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[1].id, ConstraintSet.BOTTOM,list[2].id, ConstraintSet.TOP,0)
                    cs.connect(list[1].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[1].id, ConstraintSet.END,list[4].id, ConstraintSet.START,0)

                    cs.connect(list[2].id, ConstraintSet.TOP,list[1].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[2].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                    cs.connect(list[2].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[2].id, ConstraintSet.END,list[4].id, ConstraintSet.START,0)

                    cs.connect(list[3].id, ConstraintSet.TOP,list[2].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[3].id, ConstraintSet.START,container.id, ConstraintSet.START,0)
                    cs.connect(list[3].id, ConstraintSet.END,list[4].id, ConstraintSet.START,0)

                    cs.connect(list[4].id, ConstraintSet.TOP,list[0].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[4].id, ConstraintSet.BOTTOM,list[5].id, ConstraintSet.TOP,0)
                    cs.connect(list[4].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[4].id, ConstraintSet.END,list[6].id, ConstraintSet.START,0)

                    cs.connect(list[5].id, ConstraintSet.TOP,list[4].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[5].id, ConstraintSet.BOTTOM,list[3].id, ConstraintSet.TOP,0)
                    cs.connect(list[5].id, ConstraintSet.START,list[0].id, ConstraintSet.END,0)
                    cs.connect(list[5].id, ConstraintSet.END,list[6].id, ConstraintSet.START,0)

                    cs.connect(list[6].id, ConstraintSet.TOP,container.id, ConstraintSet.TOP,0)
                    cs.connect(list[6].id, ConstraintSet.BOTTOM,list[7].id, ConstraintSet.TOP,0)
                    cs.connect(list[6].id, ConstraintSet.START,list[4].id, ConstraintSet.END,0)
                    cs.connect(list[6].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[7].id, ConstraintSet.TOP,list[6].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[7].id, ConstraintSet.BOTTOM,list[8].id, ConstraintSet.TOP  ,0)
                    cs.connect(list[7].id, ConstraintSet.START,list[4].id, ConstraintSet.END,0)
                    cs.connect(list[7].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[8].id, ConstraintSet.TOP,list[7].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[8].id, ConstraintSet.BOTTOM,list[9].id, ConstraintSet.TOP,0)
                    cs.connect(list[8].id, ConstraintSet.START,list[4].id, ConstraintSet.END,0)
                    cs.connect(list[8].id, ConstraintSet.END,container.id, ConstraintSet.END,0)

                    cs.connect(list[9].id, ConstraintSet.TOP,list[8].id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[9].id, ConstraintSet.BOTTOM,container.id, ConstraintSet.BOTTOM,0)
                    cs.connect(list[9].id, ConstraintSet.START,list[4].id, ConstraintSet.END,0)
                    cs.connect(list[9].id, ConstraintSet.END,container.id, ConstraintSet.END,0)
                }
            }

            cs.applyTo(container)
            container.bringToFront()
        }


    }

    fun playSound(num: Int){
        val catalog: Int = when(Locale.getDefault().language){
            "en" -> 1
            "pl" -> 2
            else -> -1
        }

        val sound = when(num){
            1 -> R.raw.jeden
            2 -> R.raw.dwa
            3 -> R.raw.trzy
            4 -> R.raw.cztery
            5 -> R.raw.piec
            6 -> R.raw.szesc
            7 -> R.raw.siedem
            8 -> R.raw.osiem
            9 -> R.raw.dziewiec
            10 -> R.raw.dziesiec

            else -> -1
        }

        val read: MediaPlayer = MediaPlayer.create(this,sound)
        read.seekTo(0)
        read.start()
    }

}