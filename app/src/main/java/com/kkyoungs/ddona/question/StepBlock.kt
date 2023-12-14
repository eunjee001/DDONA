package com.kkyoungs.ddona.question

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kkyoungs.ddona.R

class StepBlock(context: Context) : LinearLayout(context) {

    init {
        init(context)
    }
        private fun init(context: Context?){
            val inflater:LayoutInflater  = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.layout_step,this, false)


        }

}
