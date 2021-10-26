package dev.dmanluc.freenowchallenge.sync

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.internal.util.resourceMatcher
import dev.dmanluc.freenowchallenge.matcher.withMinItemCount
import dev.dmanluc.freenowchallenge.rule.ConditionWatcher
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not

@Suppress("unused")
object ViewConditionWatcher {

    fun waitUntilViewIsDisplayed(text: String) {
        waitUntilViewMatchesCondition(text, isDisplayed())
    }

    fun waitUntilViewIsDisplayed(viewStringId: Int) {
        waitUntilViewMatchesCondition(viewStringId, isDisplayed())
    }

    fun waitUntilViewIsNotDisplayed(viewStringId: Int) {
        waitUntilViewMatchesCondition(viewStringId, not(isDisplayed()))
    }

    fun waitUntilViewIsEnabled(viewStringId: Int) {
        waitUntilViewMatchesCondition(viewStringId, isEnabled())
    }

    fun waitUntilListIsLoaded(recyclerViewId: Int, minCount: Int = 1) {
        waitUntilViewMatchesCondition(recyclerViewId, withMinItemCount(minCount))
    }

    private fun waitUntilViewMatchesCondition(
        viewStringId: Int,
        condition: Matcher<View>,
    ) {
        ConditionWatcher.waitForCondition(
            ViewInstruction(viewStringId.resourceMatcher(), condition)
        )
    }

    private fun waitUntilViewMatchesCondition(
        text: String,
        condition: Matcher<View>,
    ) {
        ConditionWatcher.waitForCondition(
            ViewInstruction(withText(text), condition)
        )
    }
}
