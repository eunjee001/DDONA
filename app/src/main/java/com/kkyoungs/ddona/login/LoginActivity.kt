package com.kkyoungs.ddona.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kkyoungs.ddona.IntentConst
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        checkIntentData(intent)
        mBinding.btnGoChat.setOnClickListener {
            val email = mBinding.etId.text.toString()
            val password = mBinding.etPw.text.toString()


            login(email, password)
        }
        mBinding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.putExtra(IntentConst.Extras.EXTRA_TYPE, type)
            intent.putExtra(IntentConst.Extras.EXTRA_NICKNAME, nickName)
            startActivity(intent)

        }
    }
    private fun checkIntentData(intent: Intent?) {
        val contentData = intent!!.extras

        if (intent != null && intent.hasExtra(IntentConst.Extras.EXTRA_TYPE)) {
            type = contentData!!.getString(IntentConst.Extras.EXTRA_TYPE).toString()
        }else if(intent != null && intent.hasExtra(IntentConst.Extras.EXTRA_NICKNAME)){
            nickName = contentData!!.getString(IntentConst.Extras.EXTRA_NICKNAME).toString()

        }

    }
    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        val callGet = RetrofitClient.apiService.postLogin(loginRequest)


        callGet.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    val intent = Intent(applicationContext, GoStartActivity::class.java)
                    startActivity(intent)
                    // 회원가입 성공 시 처리
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    // 추가적인 로직 구현 가능
                } else {
                    // 회원가입 실패 시 처리
                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 네트워크 오류 등 실패 시 처리
                Toast.makeText(this@LoginActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}