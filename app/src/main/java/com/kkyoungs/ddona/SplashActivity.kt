package com.kkyoungs.ddona

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kkyoungs.ddona.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(){
    private val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
//        finishIntro(true)
        Handler().postDelayed(Runnable {
            Appconf.IS_COMPLETE_MAIN_LOAD = true
            // 앱의 MainActivity로 넘어가기
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)            // 현재 액티비티 닫기
        }, 3000) // 3초
    }
    /**
     * 인트로 종료 처리
     *
     * @param isCmplIntro 앱 초기화 완료 여부 (true : 완료 / false : 취소 또는 실패, 앱 사용불가)
     */
    private fun finishIntro(isCmplIntro: Boolean) {
        if (isCmplIntro) {
            setResult(Activity.RESULT_OK)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }
}