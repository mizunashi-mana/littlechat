package com.example.littlechat.module

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.littlechat.model.Token
import com.example.littlechat.model.User
import com.example.littlechat.repository.db.SessionDao
import com.example.littlechat.repository.db.TokenDao
import com.example.littlechat.repository.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideTokenDao(db: AppDatabase): TokenDao {
        return db.tokenDao()
    }

    @Singleton
    @Provides
    fun provideSessionDao(db: AppDatabase): SessionDao {
        return db.sessionDao()
    }
}

@Database(
    entities = [
        User::class,
        Token::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tokenDao(): TokenDao
    abstract fun sessionDao(): SessionDao
}
