package com.bangkit.resep.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.bangkit.resep.api.RetrofitInstance
import com.bangkit.resep.models.ChatResponse
import com.bangkit.resep.models.Message
import com.bangkit.resep.models.UserInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    init {
        _messages.value = mutableListOf()
    }

    fun sendMessage(content: String) {
        val newMessages = _messages.value?.toMutableList() ?: mutableListOf()
        newMessages.add(Message(content, true))
        _messages.value = newMessages

        fetchResponseFromAPI(content)
    }

    private fun fetchResponseFromAPI(userMessage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitInstance.chatbotApi.sendMessage(UserInput(userMessage))
            call.enqueue(object : Callback<ChatResponse> {
                override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val newMessages = _messages.value?.toMutableList() ?: mutableListOf()
                            newMessages.add(Message(it.response, false))
                            _messages.postValue(newMessages)
                        }
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}
