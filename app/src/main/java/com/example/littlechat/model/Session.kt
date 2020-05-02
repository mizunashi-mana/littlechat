package com.example.littlechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey val tokenId: String,
    val user: User
)
