package com.kkyoungs.ddona.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.FragmentGoInfoBinding



class GoInfoContent : Fragment() {
    private var binding :FragmentGoInfoBinding ?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_go_info, container, false)

        binding?.btnNext?.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, MakeCharacterContent())
            transaction.disallowAddToBackStack()
            transaction.commit()
            binding!!.btnNext.visibility = View.GONE

        }
        binding!!.llBack.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, GoMakeMeContent())
            transaction.disallowAddToBackStack()
            transaction.commit()
            binding!!.btnNext.visibility = View.GONE
        }

        return binding!!.root
    }

}

