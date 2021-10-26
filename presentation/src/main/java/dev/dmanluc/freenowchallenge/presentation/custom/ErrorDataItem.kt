package dev.dmanluc.freenowchallenge.presentation.custom

import androidx.annotation.StringRes

class ErrorDataItem(
    @StringRes val textResId: Int? = null,
    val errorTextMessage: String? = null,
    val onErrorAction: () -> Unit = {}
)