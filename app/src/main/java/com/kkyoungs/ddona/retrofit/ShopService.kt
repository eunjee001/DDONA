package com.kkyoungs.ddona.retrofit

import com.kkyoungs.ddona.MbtiCalculationResponse
import com.kkyoungs.ddona.QuestionData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShopService {
    @GET("/api/mbti/question/{no}")
    fun getMbtiQuestion(@Path("no") questionNumber: Int): Call<QuestionData>


    @POST("/api/mbti/calculate")
    fun postMbtiCalculation(@Body mbtiCalculationRequest: MbtiCalculationRequest): Call<MbtiCalculationResponse>

}
data class MbtiCalculationRequest(
    val mbtiScores: Map<String, Int>
)


