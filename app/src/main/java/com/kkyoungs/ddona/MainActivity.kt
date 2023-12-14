package com.kkyoungs.ddona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.kkyoungs.ddona.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var mTabLayout: MainTabLayout? = null
    private var mViewPagerAdapter: MainViewPagerAdapter? = null
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        initView()
        initViewPager()

    }
    private fun initView() {
        mTabLayout = mBinding.tabMain
        mTabLayout?.setOnTabReselected {
            when (it) {
                0 -> {
                    MakeCharacterContent()
                }
                1 ->{
                    MyCharacterContent()
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
            mBinding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    kotlin.runCatching {
                        onInvokeVisible()

                        // prevent session timeout not dismissing loading

                    }
                }
            })

            // init Tab
            mTabLayout?.initTab(mBinding.vpMain)
        }

    open fun onInvokeVisible() {}

}