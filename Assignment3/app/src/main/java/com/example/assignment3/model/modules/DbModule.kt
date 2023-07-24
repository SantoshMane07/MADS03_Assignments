package com.example.assignment3.model.modules

import android.content.Context
import androidx.room.Room
import com.example.assignment3.model.room_db.UserDatabase
import com.example.assignment3.model.room_db.daos.UserDao
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
    fun provideAppDatabase(@ApplicationContext context:Context):UserDatabase{
        return Room.databaseBuilder(
            context.applicationContext,UserDatabase::class.java,"Assignment3"
        ).allowMainThreadQueries().build()
    }
    @Singleton
    @Provides
    fun ProvideUserDao(userDatabase:UserDatabase): UserDao = userDatabase.userDao
}
