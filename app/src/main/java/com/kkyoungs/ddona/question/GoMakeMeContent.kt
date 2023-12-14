package com.kkyoungs.ddona.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.FragmentGoMakeBinding


class GoMakeMeContent : Fragment() {
    private var binding :FragmentGoMakeBinding ?= null
    var content: Fragment? = null
    var contentData = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_go_make, container, false)

        binding?.btnCharacter?.setOnClickListener {

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, GoInfoContent())
            transaction.disallowAddToBackStack()
            transaction.commit()
            binding!!.btnCharacter.visibility = View.GONE

        }

        return binding!!.root
    }

}

