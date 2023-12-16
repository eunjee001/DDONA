package com.kkyoungs.ddona.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kkyoungs.ddona.IntentConst
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.ActivityChattingBinding
import com.kkyoungs.ddona.databinding.ActvityRegisterBinding
import com.kkyoungs.ddona.retrofit.MbtiCalculationRequest
import com.kkyoungs.ddona.retrofit.RegisterRequest
import com.kkyoungs.ddona.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private val mBinding by lazy { ActvityRegisterBinding.inflate(layoutInflater) }
    var nickName : String?= null
    var type : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        val contentData = intent.extras

        type = contentData!!.getString(IntentConst.Extras.EXTRA_TYPE).toString()
        nickName = contentData!!.getString(IntentConst.Extras.EXTRA_NICKNAME).toString()
        signUpSetting()
        mBinding.idInfo.visibility= View.GONE

        mBinding.btnGoChat.setOnClickListener {
            val email = mBinding.etId.text.toString()
            val password = mBinding.etPw.text.toString()

            signUp(email, password, nickName!!, type!!)
        }
    }

    private fun signUpSetting(){
        mBinding.etPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 텍스트가 변경될 때마다 호출된다.
                // 비밀번호를 입력할 때 실시간으로 비밀번호 형식을 검사한다.
                isRegularPwd()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

    }
    private fun isRegularPwd(): Boolean {
        val pwd = mBinding.etPw.text.toString().trim()
        // 영어, 숫자, 특수문자, 글자 수는 8~16자인 비밀번호 패턴
        val pwdPattern =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$"
        val pattern = Pattern.matches(pwdPattern, pwd) // 패턴이 맞는지 확인

        if (pattern) {
            // 유효성 검사 결과 정해진 비밀번호 형식일 경우
            mBinding.etPw.setBackgroundResource(R.drawable.ic_name_write)
            return true
        } else {
            // 유효성 검사 결과 비밀번호 형식이 아니면 EditText 테두리를 빨간색으로 처리한다.
            mBinding.etPw.setBackgroundResource(R.drawable.ic_name_write_no)
            return false
        }
    }

    private fun signUp(email: String, password: String, personaName: String, mbtiType: String) {
        val mbtiCalculationRequest = RegisterRequest(email, password, personaName, mbtiType)

        val callGet = RetrofitClient.apiService.postRegister(mbtiCalculationRequest)


        callGet.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val signUpResponse = response.body()

                if (!isRegularPwd()) {
                    Toast.makeText(this@RegisterActivity, "비밀번호는 영문+숫자+특수문자 조합하여 8~16자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "공란이 존재합니다", Toast.LENGTH_SHORT).show()
                }
                if (response.isSuccessful) {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                    // 회원가입 성공 시 처리
                    Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    // 추가적인 로직 구현 가능
                } else {
                    // 회원가입 실패 시 처리
                    Toast.makeText(this@RegisterActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // 네트워크 오류 등 실패 시 처리
                Toast.makeText(this@RegisterActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
}