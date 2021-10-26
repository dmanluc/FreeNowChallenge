package dev.dmanluc.freenowchallenge.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmanluc.freenowchallenge.data.repository.PoiRepositoryImpl
import dev.dmanluc.freenowchallenge.domain.repository.PoiRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPoiRepository(repositoryImpl: PoiRepositoryImpl): PoiRepository

}