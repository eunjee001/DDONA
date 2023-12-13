package com.kkyoungs.ddona

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding

class StepBlock : LinearLayout {
        constructor(context: Context, attrs: AttributeSet?) : super(
            context,
            attrs
        ) {
            init(context)
        }

        constructor(context: Context) : super(context) {
            init(context)
        }
        private fun init(context: Context?){
            val inflater:LayoutInflater  = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.layout_step,this, true)

            val image = findViewById<ImageView>(R.id.iv_step)
            // 이미지 로드 라이브러리 사용 ImageUrl to Image
            Glide.with(this)
                    .load(R.drawable.stepbar_component_fill)
                    .into(image);
        }

}
