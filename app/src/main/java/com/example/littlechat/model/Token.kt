package com.example.littlechat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.littlechat.support.room.converter.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class Token(
    @PrimaryKey val id: String,
    val tokenString: String,
    val userId: String,
    val expiredAt: Date
)
