package com.kkyoungs.ddona

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kkyoungs.ddona.databinding.ActivityMainBinding
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding


class MakeCharacterContent : Fragment() {
    private var binding :FragmentMakeCharacterBinding ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_make_character, container, false)
        init()
        stepBar()

        return binding!!.root
    }
    fun init(){
    }
    private fun stepBar(){

        val stepLayout = context?.let { StepBlock(it) }

        if (stepLayout!!.parent !=null) {
            ((stepLayout!!.parent)as ViewGroup).removeView(stepLayout)
        }

        for (i in 0..15) {
            val params: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(
                    32,
                    10
                )

            stepLayout.layoutParams = params

            binding!!.llStepBar.addView(stepLayout)


        }



    }
}

