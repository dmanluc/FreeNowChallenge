package dev.dmanluc.freenowchallenge.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmanluc.freenowchallenge.data.datasource.api.PoiApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun providePoiApiService(retrofit: Retrofit) = retrofit.create(PoiApi::class.java)

}