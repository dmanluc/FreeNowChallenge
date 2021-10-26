package dev.dmanluc.freenowchallenge.sync

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import dev.dmanluc.freenowchallenge.rule.Instruction
import org.hamcrest.Matcher
import java.lang.reflect.Field

class ViewInstruction(
    private val viewMatcher: Matcher<View>,
    private val idleMatcher: Matcher<View>,
    private val rootMatcher: Matcher<Root> = RootMatchers.DEFAULT
) : Instruction() {
    override fun getDescription(): String {
        printViewHierarchy()
        return "$this $viewMatcher"
    }

    override fun checkCondition(): Boolean =
        idleMatcher.matches(
            getView(viewMatcher)
        )

    private fun getView(viewMatcher: Matcher<View>): View? = try {
        var view: View? = null
        UiThreadStatement.runOnUiThread {
            val viewInteraction = onView(viewMatcher).inRoot(rootMatcher)

            val finderField: Field = viewInteraction
                .javaClass
                .getDeclaredField("viewFinder").apply {
                    isAccessible = true
                }

            val finder = finderField.get(viewInteraction) as ViewFinder
            view = finder.view
        }
        view
    } catch (e: Exception) {
        null
    }

    private fun printViewHierarchy() {
        try {
            assertDisplayed("Force print hierarchy")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
