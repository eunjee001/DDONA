package com.kkyoungs.ddona

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2

import com.google.android.material.tabs.TabLayout
import com.kkyoungs.ddona.databinding.CustomTabBinding

/**
 * 메인 탭 메뉴 Layout
 *
 * @author Beple Pay Team
 **/
class MainTabLayout : TabLayout, TabLayout.OnTabSelectedListener {

    private var mCurrPosition = 0

    private var mVpMain: ViewPager2? = null
    private var mBinding : CustomTabBinding? = null
    private var mOnTabReselected: ((Int) -> Unit)? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    /**
     * 뷰 초기화
     */
    private fun initView(context: Context) {
//        setTabTextColors(
//            ContextCompat.getColor(context, R.color.color_000000),
//            ContextCompat.getColor(context, R.color.color_000000)
//        )
    }

    /**
     * 탭 메뉴 초기화
     */
    fun initTab(viewPager2: ViewPager2) {

        kotlin.runCatching {
            // 초기화
            removeAllTabs()
            removeOnTabSelectedListener(this)

            mVpMain = viewPager2

//            setTabTextColors(
//                ContextCompat.getColor(context, R.color.color_main_text_tab),
//                ContextCompat.getColor(context, R.color.color_main_text_tab_select)
//            )

            addTabView( 0)

            addTabView( 1)


            addOnTabSelectedListener(this)

            moveTab(0)

        }.onFailure {
            it.printStackTrace()
        }
    }

    /**
     * 탭 뷰 추가
     */
    private fun addTabView( menuId: Int) {
        createTabView(menuId.toString())?.let {
            val tabItem = newTab()
            tabItem.customView = it
            addTab(tabItem)
        }
    }

    /**
     * 탭 뷰 만들
     */
    private fun createTabView( menuId: String): View? {
        return runCatching {
            // inflate the custom view using data binding
            mBinding = CustomTabBinding.inflate(LayoutInflater.from(context))

            var imgResource = 0
            when (menuId) {
                "0" -> {
                    imgResource = R.drawable.menu_tab_selector_create
                    mBinding?.clWithTitle?.setOnClickListener {
                        selectTab(menuId)
                    }
                }
                "1" ->{
                    imgResource = R.drawable.menu_tab_selector_my
                    mBinding?.clWithTitle?.setOnClickListener {
                        selectTab(menuId)
                    }
                }


            }

            mBinding?.clWithTitle?.visibility = VISIBLE

            mBinding?.ivWithTitle?.setImageDrawable(AppCompatResources.getDrawable(context, imgResource))

            // return the root view of the custom layout
            mBinding?.root
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }

    /**
     * 탭 선택
     */
    private fun selectTab(menuId: String) {
        if (mCurrPosition == menuId.toInt()) {
            mOnTabReselected?.invoke(menuId.toInt())
        } else {
            getTabAt(menuId.toInt())?.select()
        }
    }

    /**
     * 탭 이동
     */
    fun moveTab(pos: Int) {
        val moveTab = getTabAt(pos) ?: return
        onTabSelected(moveTab)
        moveTab.select()
    }

    /**
     * 탭 메뉴 선택
     */
    override fun onTabSelected(tab: Tab) {

        mCurrPosition = tab.position
        mVpMain?.setCurrentItem(mCurrPosition, false)

    }

    /**
     * 탭 메뉴 해제
     */
    override fun onTabUnselected(tab: Tab) {
        // 선택 해제된 Content 삭제
    }

    /**
     * 탭 메뉴 재선택
     */
    override fun onTabReselected(tab: Tab) {
    }

    /**
     * 메뉴 Icon 설정
     */
    @Suppress("unused")
    private fun setMenuIcon(tab: Tab, iconResId: Int) {
        tab.setIcon(iconResId)
    }

    /**
     * 탭 메뉴 선택
     */
    fun setOnTabReselected(onTabReselected: (Int) -> Unit) {
        mOnTabReselected = onTabReselected
    }

}