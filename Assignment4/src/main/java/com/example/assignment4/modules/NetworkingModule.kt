package com.example.assignment4.modules

import com.example.assignment4.BuildConfig
import com.example.assignment4.networkingAPIs.PostsApi
import com.example.assignment4.networkingAPIs.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().retryOnConnectionFailure(true).also {

            if (BuildConfig.DEBUG) {
                it.addInterceptor(getLoggingInterceptor())
            }
        }.build()
    }

    @Singleton
    @Provides
    @Named("User-retrofit")
    fun provideUserRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://random-data-api.com/").client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    @Named("Posts-retrofit")
    fun providePostsRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://picsum.photos/").client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideUserApi(@Named("User-retrofit") retrofit: Retrofit) =
        retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun providePostsApi(@Named("Posts-retrofit") retrofit: Retrofit) =
        retrofit.create(PostsApi::class.java)
}