package com.kkyoungs.ddona.myCharacter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kkyoungs.ddona.IntentConst
import com.kkyoungs.ddona.MainActivity
import com.kkyoungs.ddona.PreferenceUtil
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.chatting.ChatRoomActivity
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding
import com.kkyoungs.ddona.databinding.FragmentMyCharacterBinding
import com.kkyoungs.ddona.question.MakeCharacterContent


/**
 * 딜리버리 화면
 * @author BeplePay Team
 *
 */
class MyCharacterContent : Fragment() {
    private var binding: FragmentMyCharacterBinding? = null

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_character, container, false
        )
        init()
        card()
        binding!!.btnGoChat.setOnClickListener {
            val intent = Intent(activity, ChatRoomActivity::class.java)
            intent.putExtra(IntentConst.Extras.EXTRA_PROFIL, prefs.myCharImg)
            intent.putExtra(IntentConst.Extras.EXTRA_NICKNAME, prefs.myEditText)
            startActivity(intent)
        }
        return binding!!.root

    }
    private fun init(){
        prefs = PreferenceUtil(requireActivity())
    }

    private fun card(){
        binding!!.tvCharacterTitle.text = prefs.myCharNick
        binding!!.tvCharacterExplain.text = prefs.myCharInfo
        binding!!.tvNickname.text = prefs.myEditText
        when (prefs.myCharImg) {
            "ENFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_enfj)
            }
            "ENFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_enfp)
            }
            "ENTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_entj)

            }
            "ENTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_entp)

            }
            "ESFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_esfj)

            }
            "ESFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_esfp)


            }
            "ESTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_estj)

            }
            "ESTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_estp)

            }
            "INFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_infj)

            }
            "INFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_infp)

            }
            "INTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_intj)

            }
            "INTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_intp)

            }
            "ISFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_isfj)

            }
            "ISFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_isfp)

            }
            "ISTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_istj)

            }
            "ISTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_istp)

            }
        }
    }
}

