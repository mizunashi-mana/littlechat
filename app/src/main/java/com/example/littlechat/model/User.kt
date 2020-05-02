package com.example.littlechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val displayName: String
)
