package com.kkyoungs.ddona

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding

class StepBlock(context: Context) : LinearLayout(context) {

    init {
        init(context)
    }
        private fun init(context: Context?){
            val inflater:LayoutInflater  = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.layout_step,this, false)


        }

}
