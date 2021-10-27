package dev.dmanluc.freenowchallenge.rule

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import pl.droidsonroids.testing.mockwebserver.FixtureDispatcher

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a mock web server setup rule
 *
 */
class MockWebServerRule(
    private val context: Context,
) : TestRule {

    lateinit var server: MockWebServer
    lateinit var fixtureDispatcher: FixtureDispatcher

    override fun apply(
        base: Statement,
        description: Description,
    ): Statement = object : Statement() {
        override fun evaluate() {
            with(EntryPoints.get(context.applicationContext, TestEntryPoint::class.java)) {
                server = mockWebServer.also { it.start(8080) }
                fixtureDispatcher = dispatcher
            }

            base.evaluate()

            server.shutdown()
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface TestEntryPoint {
        val mockWebServer: MockWebServer
        val dispatcher: FixtureDispatcher
    }
}
