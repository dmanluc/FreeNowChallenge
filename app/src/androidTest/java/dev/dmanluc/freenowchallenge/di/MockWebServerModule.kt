package dev.dmanluc.freenowchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.dmanluc.freenowchallenge.fixture.defaults
import okhttp3.mockwebserver.MockWebServer
import pl.droidsonroids.testing.mockwebserver.FixtureDispatcher
import javax.inject.Named
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Module to provide mock web server related stuff in UI tests
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object MockWebServerModule {

    const val MOCK_WEB_SERVER_URL = "mock_web_server_url"

    @Provides
    @Singleton
    fun provideMockWebServerDispatcher(): FixtureDispatcher = FixtureDispatcher().apply {
        defaults()
    }

    @Provides
    @Singleton
    fun provideMockWebServer(dispatcher: FixtureDispatcher): MockWebServer = MockWebServer().apply {
        this.dispatcher = dispatcher
    }

    @Named(MOCK_WEB_SERVER_URL)
    @Provides
    @Singleton
    fun getMockWebServerUrl(mockWebServer: MockWebServer): String {
        var path: String? = null
        thread(start = true) {
            path = mockWebServer
                .url("/")
                .toString()
        }.join()

        return path!!
    }
}
