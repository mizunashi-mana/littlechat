package com.example.littlechat.repository.db

import androidx.room.Dao
import androidx.room.Query
import com.example.littlechat.model.User

@Dao
interface SessionDao {
    @Query("""
        SELECT user.* FROM user INNER JOIN token
        WHERE token.id = :tokenId AND user.id = token.userId
    """)
    suspend fun getUserByTokenId(tokenId: String): User?
}
