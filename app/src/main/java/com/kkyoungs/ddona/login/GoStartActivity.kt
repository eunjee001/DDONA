package com.kkyoungs.ddona.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kkyoungs.ddona.Appconf
import com.kkyoungs.ddona.IntentConst
import com.kkyoungs.ddona.MainActivity
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.ActivityWelcomeBinding
import com.kkyoungs.ddona.myCharacter.MyCharacterContent
import com.kkyoungs.ddona.question.GoInfoContent


class GoStartActivity : AppCompatActivity(){
        private val mBinding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnGoChat.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(IntentConst.Extras.EXTRA_POS, "1")
            startActivity(intent)
        }


    }


}

