package com.example.littlechat.repository.db

import androidx.room.*
import com.example.littlechat.model.Token

@Dao
abstract class TokenDao {
    @Query("SELECT * FROM token LIMIT 1")
    abstract suspend fun get(): Token?

    @Query("SELECT * FROM token WHERE id = :tokenId")
    abstract suspend fun getById(tokenId: String): Token?

    @Insert
    abstract suspend fun insert(token: Token)

    @Delete
    abstract suspend fun delete(token: Token)

    @Query("DELETE FROM token WHERE id = :id")
    abstract suspend fun deleteById(id: String)
}
