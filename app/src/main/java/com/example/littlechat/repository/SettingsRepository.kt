package com.example.littlechat.repository

import com.example.littlechat.support.security.KeyReader
import java.security.Key
import javax.inject.Inject

interface SettingsRepository {
    val jwtSigningKey: Key
}

class SettingsRepositoryImpl @Inject constructor(
): SettingsRepository {
    override val jwtSigningKey: Key =
        KeyReader.readPublicKey("".toByteArray())!!
}
