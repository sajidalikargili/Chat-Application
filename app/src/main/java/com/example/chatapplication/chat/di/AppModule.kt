package com.example.chatapplication.chat.di

import com.example.chatapplication.chat.core.Constant
import com.example.chatapplication.chat.data.GeminiApiService
import com.example.chatapplication.chat.data.GeminiRepositoryImp
import com.example.chatapplication.chat.domain.GeminiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun retrofitProvider(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(
                GsonConverterFactory.
            create()).build()
    }
    @Provides
    @Singleton
    fun geminiApiServiceProvider(retrofit: Retrofit): GeminiApiService {
     return  retrofit.create(GeminiApiService::class.java)
    }
    @Provides
    @Singleton
   fun repositoryProvider(geminiApiService: GeminiApiService): GeminiRepository {
        return GeminiRepositoryImp(geminiApiService)
    }

}