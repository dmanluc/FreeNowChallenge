package dev.dmanluc.freenowchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.dmanluc.freenowchallenge.data.di.BaseUrlModule
import dev.dmanluc.freenowchallenge.data.di.BaseUrlModule.BASE_URL
import dev.dmanluc.freenowchallenge.di.MockWebServerModule.MOCK_WEB_SERVER_URL
import javax.inject.Named

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Test module to provide URL in UI testing
 *
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BaseUrlModule::class]
)
object BaseUrlTestModule {

    @Named(BASE_URL)
    @Provides
    fun provideTestBaseUrl(@Named(MOCK_WEB_SERVER_URL) url: String) = url

}
