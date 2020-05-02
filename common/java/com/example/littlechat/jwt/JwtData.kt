package com.example.littlechat.jwt

import java.util.*

data class JwtData(
    val id: String,
    val issuer: String,
    val issuedAt: Date,
    val expiredAt: Date,
    val userId: String
)
