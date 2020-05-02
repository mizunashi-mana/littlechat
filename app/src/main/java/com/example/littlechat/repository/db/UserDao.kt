package com.example.littlechat.repository.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.littlechat.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUser(userId: String): User?

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}
