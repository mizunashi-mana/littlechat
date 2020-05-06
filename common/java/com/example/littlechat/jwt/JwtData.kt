package com.example.littlechat.jwt

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.RequiredTypeException
import java.util.*
import kotlin.reflect.KClass

data class JwtData(
    val id: String,
    val issuer: String,
    val issuedAt: Date,
    val expiredAt: Date,
    val userId: String
)

internal fun requiredNonNull(
    key: String,
    expectedType: KClass<*>
): Throwable {
    return RequiredTypeException(
        "$key is expected ${expectedType.qualifiedName}, but actual null"
    )
}

/**
 * @throws RequiredTypeException
 */
fun JwtParser.parseJwtData(tokenString: String): JwtData {
    val body = parseClaimsJws(tokenString).body

    return JwtData(
        id = body.id
            ?: throw requiredNonNull("id", String::class),
        issuer = body.issuer
            ?: throw requiredNonNull("issuer", String::class),
        issuedAt = body.issuedAt
            ?: throw requiredNonNull("issuedAt", String::class),
        expiredAt = body.expiration
            ?: throw requiredNonNull("expiredAt", String::class),
        userId = body.get("userId", String::class.java)
    )
}
