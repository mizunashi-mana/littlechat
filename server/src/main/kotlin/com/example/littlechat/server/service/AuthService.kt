package com.example.littlechat.server.service

import com.example.littlechat.proto.Auth
import com.example.littlechat.proto.AuthServiceGrpcKt
import com.example.littlechat.server.support.proto.ResponseCommon
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.PrivateKey
import java.util.*

class AuthService(
    private val getSignKey: () -> PrivateKey
): AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {
    private val issuer: String = "sample server"

    private fun generateToken(userId: String): String {
        val signKey = getSignKey()

        val cal = Calendar.getInstance()
        val nowDate = cal.time

        cal.roll(Calendar.MONTH, 1)
        val expiredDate = cal.time

        return Jwts.builder()
            .setIssuer(issuer)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(nowDate)
            .setExpiration(expiredDate)
            .claim("userId", userId)
            .signWith(signKey, SignatureAlgorithm.RS512)
            .compact()
    }

    override suspend fun auth(request: Auth.ApiAuth.Request): Auth.ApiAuth.Response {
        if (request.userIdentifier == request.password) {
            val token = generateToken(request.userIdentifier)
            return Auth.ApiAuth.Response.newBuilder()
                .setCommon(ResponseCommon.success())
                .setOkBody(
                    Auth.ApiAuth.OkBody.newBuilder()
                        .setToken(token)
                        .build()
                )
                .build()
        } else {
            return Auth.ApiAuth.Response.newBuilder()
                .setCommon(ResponseCommon.success())
                .setErrorBody(
                    Auth.ApiAuth.ErrorBody.newBuilder()
                        .setMessage("Authentication failed")
                        .build()
                )
                .build()
        }
    }

    override suspend fun register(request: Auth.ApiRegister.Request): Auth.ApiRegister.Response {
        val token = generateToken(request.userId)
        return Auth.ApiRegister.Response.newBuilder()
            .setCommon(ResponseCommon.success())
            .setOkBody(
                Auth.ApiRegister.OkBody.newBuilder()
                    .setToken(token)
                    .build()
            )
            .build()
    }
}
