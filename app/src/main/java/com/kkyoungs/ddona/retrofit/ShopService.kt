package com.kkyoungs.ddona.retrofit

import com.kkyoungs.ddona.chatting.ChatModel
import com.kkyoungs.ddona.chatting.ChatResponse
import com.kkyoungs.ddona.login.LoginResponse
import com.kkyoungs.ddona.login.RegisterResponse
import com.kkyoungs.ddona.question.data.MbtiCalculationResponse
import com.kkyoungs.ddona.question.data.QuestionData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ShopService {
    @GET("/api/mbti/question/{no}")
    fun getMbtiQuestion(@Path("no") questionNumber: Int): Call<QuestionData>


    @POST("/api/mbti/calculate")
    fun postMbtiCalculation(@Body mbtiCalculationRequest: MbtiCalculationRequest): Call<MbtiCalculationResponse>

    @POST("/api/clova/chat")
    fun postChat(@Header("Authorization") authorization: String, @Body chatRequest: ChatRequest): Call<ChatResponse>

    @POST("/api/member/register")
    fun postRegister(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("/api/member/login")
    fun postLogin(@Body LoginRequest: LoginRequest): Call<LoginResponse>

}
data class MbtiCalculationRequest(
    val mbtiScores: Map<String, Int>
)

data class ChatRequest(
    val message: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val personaName: String,
    val mbtiType: String
    )

data class LoginRequest(
    val email: String,
    val password: String,

)


