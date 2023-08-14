package com.example.assignment5.modules

import android.content.Context
import androidx.room.Room
import com.example.assignment5.room_db.daos.UserDao
import com.example.assignment5.room_db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext, UserDatabase::class.java,"Assignment5"
        ).allowMainThreadQueries().build()
    }
    @Singleton
    @Provides
    fun ProvideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.userDao
}
