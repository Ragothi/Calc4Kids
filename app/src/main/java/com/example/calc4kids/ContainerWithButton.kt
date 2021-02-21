package com.example.calc4kids

import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class ContainerWithButton (but: Button, con: ConstraintLayout){

    val button:Button = but
    val container: ConstraintLayout = con
    var ans: Int = -1

}