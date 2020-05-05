package com.example.littlechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey val id: String,
    val body: String
)
