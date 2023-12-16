package com.kkyoungs.ddona.chatting

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkyoungs.ddona.PreferenceUtil
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.ItemMyChatBinding
import com.kkyoungs.ddona.databinding.ItemYourChatBinding
import java.net.URL
import java.util.ArrayList

class ChatAdapter(private val list: ArrayList<ChatModel>,    private val mContext: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal lateinit var preferences: SharedPreferences
        lateinit var prefs: PreferenceUtil

    private var bindingMy: ItemMyChatBinding? = null
    private var bindingYour: ItemYourChatBinding? = null


    fun addItem(item: ChatModel) {//아이템 추가
        if (list != null) {
            list.add(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //getItemViewType 에서 뷰타입 1을 리턴받았다면 내채팅레이아웃을 받은 Holder를 리턴
        return if(viewType == 1){
            bindingMy = ItemMyChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderMy(bindingMy!!.root)
        }
        //getItemViewType 에서 뷰타입 2을 리턴받았다면 상대채팅레이아웃을 받은 Holder2를 리턴
        else{
            bindingYour = ItemYourChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderYour(bindingYour!!.root)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
//onCreateViewHolder에서 리턴받은 뷰홀더가 Holder라면 내채팅, item_my_chat의 뷰들을 초기화 해줌
        if (viewHolder is ViewHolderMy) {
            bindingMy!!.chatText.text = list.get(i).script
        }
        //onCreateViewHolder에서 리턴받은 뷰홀더가 Holder2라면 상대의 채팅, item_your_chat의 뷰들을 초기화 해줌
        else if(viewHolder is ViewHolderYour) {
            when (list.get(i).profile_image) {
                "ENFJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_enfj)
                }

                "ENFP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_enfp)
                }

                "ENTJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_entj)

                }

                "ENTP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_entp)

                }

                "ESFJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_esfj)

                }

                "ESFP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_esfp)


                }

                "ESTJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_estj)

                }

                "ESTP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_estp)

                }

                "INFJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_infj)

                }

                "INFP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_infp)

                }

                "INTJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_intj)

                }

                "INTP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_intp)

                }

                "ISFJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_isfj)

                }

                "ISFP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_isfp)

                }

                "ISTJ" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_istj)

                }

                "ISTP" -> {
                    bindingYour!!.chatYouImage.setImageResource(R.drawable.property_1_istp)

                }
            }
            bindingYour!!.chatYouName.setText(list.get(i).name)
            bindingYour!!.chatText.setText(list.get(i).script)
        }
    }

    override fun getItemCount(): Int {
        return list.size

    }



    override fun getItemViewType(position: Int): Int {//여기서 뷰타입을 1, 2로 바꿔서 지정해줘야 내채팅 너채팅을 바꾸면서 쌓을 수 있음
        preferences = mContext.getSharedPreferences("USERSIGN", Context.MODE_PRIVATE)
        prefs = PreferenceUtil(mContext)
        //내 아이디와 arraylist의 name이 같다면 내꺼 아니면 상대꺼
        return if (list.get(position).script == prefs.send) {
            1
        } else {
            2
        }
    }

    class ViewHolderMy(view: View) : RecyclerView.ViewHolder(view)
    class ViewHolderYour(view: View) : RecyclerView.ViewHolder(view)



}
