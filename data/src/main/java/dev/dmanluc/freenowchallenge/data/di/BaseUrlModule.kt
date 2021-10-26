package dev.dmanluc.freenowchallenge.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmanluc.freenowchallenge.data.BuildConfig
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    const val BASE_URL = "BASE_URL"

    @Named(BASE_URL)
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL
}