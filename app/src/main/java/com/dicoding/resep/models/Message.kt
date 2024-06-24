package com.bangkit.resep.models

data class Message(
    val content: String,
    val isSent: Boolean // Menandakan apakah pesan dikirim oleh pengguna atau bukan
)
