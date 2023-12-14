package com.kkyoungs.ddona.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding
import com.kkyoungs.ddona.question.data.MbtiCalculationResponse
import com.kkyoungs.ddona.question.data.QuestionData
import com.kkyoungs.ddona.retrofit.MbtiCalculationRequest
import com.kkyoungs.ddona.retrofit.RetrofitClient
import com.kkyoungs.ddona.retrofit.ShopService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MakeCharacterContent : Fragment() {
    private var binding :FragmentMakeCharacterBinding ?= null
    val mbtiScores = mapOf(
        "E" to 1,
        "I" to 5,
        "S" to 4,
        "N" to 2,
        "T" to 3,
        "F" to 4,
        "P" to 2,
        "J" to 4
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_make_character, container, false)
        init()
        stepBar()

        getService()
        calculator()
        return binding!!.root
    }
    fun init(){
    }
    private fun stepBar(){
        val qlist = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16)
        val gridBlockAdapter = GridBlockAdapter(requireContext(), qlist)
        binding!!.gridView.adapter = gridBlockAdapter
        val questionNumber = 1

        val callGet = RetrofitClient.apiService.getMbtiQuestion(questionNumber)
        callGet.enqueue(object : Callback<QuestionData> {
            override fun onResponse(call: Call<QuestionData>, response: Response<QuestionData>) {
                if (response.isSuccessful) {
                    val mbtiQuestionResponse = response.body()
                    println(">>>mbtiQuestionResponse" +mbtiQuestionResponse?.answers.toString())
                    // TODO: 서버 응답 처리
                } else {
                    // TODO: 서버 응답 실패 처리
                }
            }

            override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                // TODO: 네트워크 요청 실패 처리
            }
        })
    }

    private fun calculator(){
        val mbtiCalculationRequest = MbtiCalculationRequest(mbtiScores)
        val callPost = RetrofitClient.apiService.postMbtiCalculation(mbtiCalculationRequest)
        callPost.enqueue(object : Callback<MbtiCalculationResponse> {
            override fun onResponse(call: Call<MbtiCalculationResponse>, response: Response<MbtiCalculationResponse>) {
                if (response.isSuccessful) {
                    val mbtiCalculationResponse = response.body()
                    // TODO: 서버 응답 처리
                    println(">>>mbtiCalculationResponse: ${mbtiCalculationResponse?.name}")
                } else {
                    // TODO: 서버 응답 실패 처리
                }
            }

            override fun onFailure(call: Call<MbtiCalculationResponse>, t: Throwable) {
                // TODO: 네트워크 요청 실패 처리
            }
        })
    }
    fun getService() : ShopService{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.201.67.203:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ShopService::class.java)
    }

}
