package com.example.calc4kids

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.example.calc4kids.MyVariables.Difficulty
import com.example.calc4kids.MyVariables.CalculationType
import com.example.calc4kids.MyVariables.CalculationType.*
import com.example.calc4kids.MyVariables.currentActivity
import kotlin.math.roundToInt
import kotlin.random.Random

class CountingActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val operation = when (currentActivity){
            ADDITION -> '+'
            SUBTRACTION -> '-'
            MULTIPLICATION -> 'x'
            DIVISION -> ':'
        }

        setContentView(R.layout.activity_counting)

        val max = when (MyVariables.currentDifficulty){
            Difficulty.EASY ->  100
            Difficulty.MID -> 225
            Difficulty.HARD ->  999
        }

        val generatedContainer: ConstraintLayout = findViewById(R.id.generatedContainer)

        var first = -1
        var second = -1
        var answer = -1
        var wrong1 = -1
        var wrong2 = -1
        var correctButton = -1

        val left: Button = findViewById(R.id.left)
        val mid: Button = findViewById(R.id.mid)
        val right: Button = findViewById(R.id.right)
        val next: Button = findViewById(R.id.nextQuestonBut)
        val ans: TextView = findViewById(R.id.ans)
        val reaction: ImageView = findViewById(R.id.reaction)
        val list: ArrayList<Button> = ArrayList()
        list.add(left)
        list.add(mid)
        list.add(right)

        fun viewButtons(view: Boolean) {
        for (but in list) {
            but.isVisible = view
        }
    }


        fun answerAnimation(but: Button, correct: Boolean) {
        but.setOnClickListener {
            if (correct) {
                reaction.setImageResource(R.drawable.happy)
            } else {
                reaction.setImageResource(R.drawable.angry)
            }
            reaction.isVisible = true
            next.isVisible = true
            viewButtons(false)
            ans.text = "${ans.text} = $answer"
        }
    }



        fun rollNewDivision(type: CalculationType) {

            correctButton = Random.nextInt(0, 3)
            var range = -1;
            when (currentActivity){
                ADDITION -> {
                    first = Random.nextInt(1, max)
                    second = Random.nextInt(1, max)
                    answer = first+second;
                    range=15
                }
                SUBTRACTION -> {
                    first = Random.nextInt(1, max)
                    second = Random.nextInt(1, first)
                    answer = first-second;
                    range=10
                }
                MULTIPLICATION -> {
                    first = Random.nextInt(1, Math.sqrt(max.toDouble()).roundToInt())
                    second = Random.nextInt(2, Math.sqrt(max.toDouble()).roundToInt())
                    answer = first*second;
                    range=15
                }
                DIVISION -> {
                    do {
                        first = Random.nextInt(4, max)
                    } while (isNumberPrime(first))
                    do {
                        second = Random.nextInt(2, Math.sqrt(max.toDouble()).roundToInt() )
                    } while (first%second != 0)
                    answer = first / second;
                    range=10
                }
            }

            ans.text = "$first $operation $second"
            do {
                wrong1 = Math.abs(Random.nextInt(answer - Random.nextInt(1, range), answer + Random.nextInt(1, range)))
                wrong2 = Math.abs(Random.nextInt(answer - Random.nextInt(1, range), answer + Random.nextInt(1, range)))
            } while (wrong1 == answer || wrong2 == answer || wrong1 == wrong2 || wrong1==0 || wrong2==0)

            var firstHit = true;

            for (but in list) {
                if (list[correctButton] == but) {
                    but.text = "$answer"
                    answerAnimation(but, true)
                } else {
                    if (firstHit) {
                        but.text = "$wrong1"
                        firstHit = false
                    } else {
                        but.text = "$wrong2"
                    }
                    answerAnimation(but, false)
                }
            }

        }
        rollNewDivision(currentActivity)

        next.setOnClickListener {
        rollNewDivision(currentActivity)
        reaction.isVisible = false
        next.isVisible = false
        viewButtons(true)
    }

        fun animateAttack(button: ImageView,skillImage: Int){
            val image: ImageView = ImageView(this)
            image.setImageResource(skillImage)
            image.id = View.generateViewId()
            image.scaleX = 2f
            image.scaleY = 2f

            generatedContainer.addView(image)

            val cs: ConstraintSet = ConstraintSet();
            var action: Int = 0


            cs.connect(image.id, ConstraintSet.TOP,action, ConstraintSet.TOP,0)
            cs.connect(image.id, ConstraintSet.BOTTOM,action, ConstraintSet.BOTTOM,0)
            cs.connect(image.id, ConstraintSet.START,action, ConstraintSet.START,0)
            cs.connect(image.id, ConstraintSet.END,action, ConstraintSet.END,0)
            cs.applyTo(generatedContainer)


        }


}

fun isNumberPrime(number: Int) :Boolean{

    if (number==1 || number==0){
        return false
    }

    for (i in 2..number/2){
        if (number%i==0){
            return false
        }
    }

    return true
}
}