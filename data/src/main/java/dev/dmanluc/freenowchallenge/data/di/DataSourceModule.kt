package dev.dmanluc.freenowchallenge.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmanluc.freenowchallenge.data.datasource.remote.VehiclesRemoteDataSource
import dev.dmanluc.freenowchallenge.data.datasource.remote.VehiclesRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule{

    @Binds
    abstract fun bindVehicleRemoteDataSource(dataSourceImpl: VehiclesRemoteDataSourceImpl): VehiclesRemoteDataSource

}