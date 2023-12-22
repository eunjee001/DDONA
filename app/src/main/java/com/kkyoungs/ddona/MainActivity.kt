package com.kkyoungs.ddona

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.kkyoungs.ddona.databinding.ActivityMainBinding
import com.kkyoungs.ddona.myCharacter.MyCharacterContent
import com.kkyoungs.ddona.question.GoMakeMeContent

class MainActivity : AppCompatActivity() {
    private var mTabLayout: MainTabLayout? = null
    private var mViewPagerAdapter: MainViewPagerAdapter? = null
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    var pos:String ="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        checkIntentData(intent)
        prefs = PreferenceUtil(this)

        if (!Appconf.IS_COMPLETE_MAIN_LOAD) {
            startActivityForResult(
                Intent(this, SplashActivity::class.java),
                0
            )
        }else {
            initView()



            initViewPager(pos)

        }

    }
    private fun checkIntentData(intent: Intent?) {
        if (intent != null && intent.hasExtra(IntentConst.Extras.EXTRA_POS)) {
            val contentData = intent.extras
                pos = contentData!!.getString(IntentConst.Extras.EXTRA_POS).toString()
            }
    }

        private fun initView() {
        mTabLayout = mBinding.tabMain
        mTabLayout?.setOnTabReselected {
            when (it) {
                0 -> {
                    GoMakeMeContent()
                }
                1 ->{
                    MyCharacterContent()
                }
            }
        }


    }
    private fun initViewPager(pos : String) {
            mViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                addFragment(GoMakeMeContent())
                addFragment(MyCharacterContent())

                mBinding.vpMain.adapter = this
                mBinding.vpMain.offscreenPageLimit = this.itemCount
            }

            // disable swipe
            mBinding.vpMain.isUserInputEnabled = false

            // ViewPager page scrolled
            mBinding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    kotlin.runCatching {
                        onInvokeVisible()

                        // prevent session timeout not dismissing loading

                    }
                }
            })
        if (pos == "1" ){
            mTabLayout!!.initTab(mBinding.vpMain, 1)
        }else{
            mTabLayout?.initTab(mBinding.vpMain,0)

        }

        }

    open fun onInvokeVisible() {}

}
