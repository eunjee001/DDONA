package com.kkyoungs.ddona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kkyoungs.ddona.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mTabLayout: MainTabLayout? = null
    private var mViewPagerAdapter: MainViewPagerAdapter? = null
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initViewPager()
    }
    private fun initView() {
        mTabLayout = findViewById(R.id.tab_main)
        mTabLayout?.setOnTabReselected {
            when (it) {
                0 -> {

                }
                1 ->{

                }
            }
        }


    }
    private fun initViewPager() {
            mViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                addFragment(MakeCharacterContent())
                addFragment(MyCharacterContent())

                mBinding.vpMain.adapter = this
                mBinding.vpMain.offscreenPageLimit = this.itemCount
            }

            // disable swipe
            mBinding.vpMain.isUserInputEnabled = false

            // ViewPager page scrolled
//            mBinding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    kotlin.runCatching {
//                        getCurrentFragment()?.onInvokeVisible()
//
//                        // prevent session timeout not dismissing loading
//                        mCommLoading?.let {
//                            if (it.isShowing) {
//                                it.dismissLoading()
//                            }
//                        }
//                    }
//                }
//            })

            // init Tab
            mTabLayout?.initTab(mBinding.vpMain)
        }


}