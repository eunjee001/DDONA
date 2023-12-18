package com.kkyoungs.ddona.login

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kkyoungs.ddona.IntentConst
import com.kkyoungs.ddona.PreferenceUtil
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.chatting.ChatRoomActivity
import com.kkyoungs.ddona.databinding.ActvityLoginBinding
import com.kkyoungs.ddona.myCharacter.MyCharacterContent
import com.kkyoungs.ddona.retrofit.LoginRequest
import com.kkyoungs.ddona.retrofit.RegisterRequest
import com.kkyoungs.ddona.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val mBinding by lazy { ActvityLoginBinding.inflate(layoutInflater) }
    var nickName : String?= null
    var type : String ?= null
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        checkIntentData(intent)
        buttonEnable()
        mBinding.btnGoChat.setOnClickListener {
            val email = mBinding.etId.text.toString()
            val password = mBinding.etPw.text.toString()


            login(email, password)
        }
        prefs = PreferenceUtil(this)

        mBinding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra(IntentConst.Extras.EXTRA_TYPE, type)
            intent.putExtra(IntentConst.Extras.EXTRA_NICKNAME, nickName)
            startActivity(intent)

        }
        mBinding.llBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun buttonEnable(){

        mBinding.etPw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //텍스트를 입력 후

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 전
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //텍스트 입력 중
                if(mBinding.etPw.length() < 4) { // 패스워드의 길이가 4미만이면
                    mBinding.btnGoChat.isClickable = false // 버튼 클릭할수 없게
                    mBinding.btnGoChat.setBackgroundResource(R.drawable.ic_question_btn)
                    mBinding.btnGoChat.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.color_868686))
                } else {
                    mBinding.btnGoChat.isClickable = true // 버튼 클릭할수 없게
                    mBinding.btnGoChat.setBackgroundResource(R.drawable.ic_question_btn_click)
                    mBinding.btnGoChat.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.white))
                }
            }
        })
    }
    private fun checkIntentData(intent: Intent?) {
        val contentData = intent!!.extras

        if (intent.hasExtra(IntentConst.Extras.EXTRA_TYPE)) {
            type = contentData!!.getString(IntentConst.Extras.EXTRA_TYPE).toString()
        }
        if(intent.hasExtra(IntentConst.Extras.EXTRA_NICKNAME)){
            nickName = contentData!!.getString(IntentConst.Extras.EXTRA_NICKNAME).toString()
            println(">>>>>>>nic" +nickName)


        }

    }
    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        val callGet = RetrofitClient.apiService.postLogin(loginRequest)


        callGet.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    prefs.token = loginResponse!!.accessToken
                    println(">>>>>>>loginResponse!!.accessToken" +loginResponse!!.accessToken)


                    val intent = Intent(applicationContext, GoStartActivity::class.java)
//                    intent.putExtra(IntentConst.Extras)
                    startActivity(intent)
                    // 회원가입 성공 시 처리
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    // 추가적인 로직 구현 가능
                } else {
                    // 회원가입 실패 시 처리
                    Toast.makeText(this@LoginActivity, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 네트워크 오류 등 실패 시 처리
                Toast.makeText(this@LoginActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}