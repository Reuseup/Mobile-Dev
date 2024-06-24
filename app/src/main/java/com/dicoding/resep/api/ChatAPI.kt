package com.bangkit.resep.api
import com.bangkit.resep.models.ChatResponse
import com.bangkit.resep.models.UserInput
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatAPI {
    @Headers("Content-Type: application/json")
    @POST("/respond")
    fun sendMessage(@Body userInput: UserInput): Call<ChatResponse>
}
