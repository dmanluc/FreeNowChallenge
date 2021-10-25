package dev.dmanluc.freenowchallenge.data.common

import dev.dmanluc.freenowchallenge.data.BuildConfig
import org.junit.Assert
import org.junit.Test

class BaseUrlTest {

    @Test
    fun `verify base url`() {
        Assert.assertTrue(urlRegex().matches(BuildConfig.BASE_URL))
    }

    private fun urlRegex() = "^https://fake-poi-api\\.mytaxi\\.com\$".toRegex()
}
