package com.example.littlechat.repository.db

import androidx.room.*
import com.example.littlechat.model.Token

@Dao
abstract class TokenDao {
    @Query("SELECT * FROM token LIMIT 1")
    abstract suspend fun getToken(): Token?

    @Query("SELECT * FROM token WHERE id = :tokenId")
    abstract suspend fun getTokenById(tokenId: String): Token?

    @Transaction
    open suspend fun updateToken(token: Token) {
        deleteAll()
        insert(token)
    }

    @Insert
    abstract suspend fun insert(token: Token)

    @Delete
    abstract suspend fun delete(token: Token)

    @Query("DELETE FROM token")
    abstract suspend fun deleteAll()
}
