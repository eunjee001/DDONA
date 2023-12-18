package com.kkyoungs.ddona.chatting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkyoungs.ddona.IntentConst
import com.kkyoungs.ddona.PreferenceUtil
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.ActivityChattingBinding
import com.kkyoungs.ddona.question.MakeCharacterContent
import com.kkyoungs.ddona.retrofit.ChatRequest
import com.kkyoungs.ddona.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class ChatRoomActivity: AppCompatActivity() {
    internal lateinit var preferences: SharedPreferences
    private val mBinding by lazy { ActivityChattingBinding.inflate(layoutInflater) }
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    //리사이클러뷰
    var arrayList = ArrayList<ChatModel>()
    val mAdapter = ChatAdapter(arrayList, this)
    var profilType :String ?= null
    var nickName : String?= null
    var editor : SharedPreferences.Editor ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        preferences = getSharedPreferences("조은경", Context.MODE_PRIVATE)
        editor = preferences.edit()
        val contentData = intent.extras

        profilType = contentData!!.getString(IntentConst.Extras.EXTRA_PROFIL).toString()
        nickName = contentData!!.getString(IntentConst.Extras.EXTRA_NICKNAME).toString()

        //버튼을 클릭하면 입력한 이름을 쉐어드프리퍼런스에 내이름을 저장한다.
        //또한 그 이름을 가지고 채팅방으로 이동한다.
        editor!!.putString("name", "조은경")
        prefs = PreferenceUtil(this)
        //어댑터 선언
        mBinding.chatRecyclerview.adapter = mAdapter
        //레이아웃 매니저 선언
        val lm = LinearLayoutManager(this)
        mBinding.chatRecyclerview.layoutManager = lm
        mBinding.chatRecyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌
        mBinding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    mBinding.button.isClickable = true
                    mBinding.button.setBackgroundResource(R.drawable.icon_send_click)
                } else {
                    mBinding.button.isClickable = false
                    mBinding.button.setBackgroundResource(R.drawable.icon_send)
                }

            }
        })
        mBinding.button.setOnClickListener {
            //아이템 추가 부분
            sendMessage()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(mBinding.editText.windowToken, 0)

        }
        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

        mBinding.tvNickname.text = nickName

    }


    private fun sendMessage() {

        preferences = getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)
        val message = mBinding.editText.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(message)) {
            return
        }
        val sendData = ChatModel("조은경", mBinding.editText.text.toString(), "")
        prefs.send = mBinding.editText.text.toString()
        mAdapter.addItem(sendData)
        mAdapter.notifyDataSetChanged()
        chatResponse()
        mBinding.editText.setText("")

        preferences.getString("name", "")?.let { Log.e("sendmmm", it) }

    }
    private fun chatResponse(){
        val chatRequest = ChatRequest(mBinding.editText.text.toString())
        val authToken = prefs.token

        val callPost = RetrofitClient.apiService.postChat("Bearer $authToken",chatRequest)
        callPost.enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val chattingResponse = response.body()
                    val message = chattingResponse!!.message
                    prefs.receive = message

                    val chatListData = ChatModel(nickName!!, message, profilType!!)
                    mAdapter.addItem(chatListData)
                    mAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(applicationContext,"서버 에러", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"네트워크 에러", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
