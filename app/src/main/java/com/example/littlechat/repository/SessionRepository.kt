package com.example.littlechat.repository

import androidx.lifecycle.LiveData
import com.example.littlechat.jwt.JwtData
import com.example.littlechat.jwt.parseJwtData
import com.example.littlechat.model.Session
import com.example.littlechat.model.Token
import com.example.littlechat.repository.db.SessionDao
import com.example.littlechat.repository.db.TokenDao
import com.example.littlechat.repository.db.UserDao
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.RequiredTypeException
import java.lang.RuntimeException
import javax.inject.Inject

interface SessionRepository {
    fun loadCurrentSession(): LiveData<Session?>

    suspend fun getCurrentSession(): Session?

    suspend fun setCurrentSession(tokenString: String): Session

    suspend fun clearSession()
}

class SessionRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val tokenDao: TokenDao,
    private val sessionDao: SessionDao,
    settingsRepository: SettingsRepository
): SessionRepository {
    private val jwtParser: JwtParser = Jwts
        .parserBuilder()
        .setSigningKey(settingsRepository.jwtSigningKey)
        .build()

    private var currentTokenId: String? = null

    private fun parseTokenString(tokenString: String): JwtData {
        try {
            return jwtParser.parseJwtData(tokenString)
        } catch (e: RequiredTypeException) {
            throw RuntimeException(e)
        }
    }

    override fun loadCurrentSession(): LiveData<Session?> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentSession(): Session? {
        return currentTokenId?.let {
            Session(
                tokenId = it,
                user = sessionDao.getUserByTokenId(it)!!
            )
        }
    }

    /**
     * Contract: insert user before call this method
     */
    override suspend fun setCurrentSession(tokenString: String): Session {
        val jwtData = parseTokenString(tokenString)
        val token = Token(
            id = jwtData.id,
            tokenString = tokenString,
            expiredAt = jwtData.expiredAt,
            userId = jwtData.userId
        )
        tokenDao.insert(token)

        return Session(
            token.id,
            userDao.getUser(token.userId)!!
        )
    }

    override suspend fun clearSession() {
        currentTokenId?.let {
            tokenDao.deleteById(it)
        }
    }
}
