package dev.dmanluc.freenowchallenge.rule

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Rule to handle a timeout when checking UI assertions
 *
 */
class ConditionWatcherTimeoutRule(
    private val timeout: Int = DEFAULT_WATCHER_TIMEOUT,
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                ConditionWatcher.setTimeoutLimit(timeout)

                base.evaluate()

                ConditionWatcher.setTimeoutLimit(ConditionWatcher.DEFAULT_TIMEOUT_LIMIT)
            }
        }
    }

    companion object {
        private const val DEFAULT_WATCHER_TIMEOUT = 5_000
    }
}