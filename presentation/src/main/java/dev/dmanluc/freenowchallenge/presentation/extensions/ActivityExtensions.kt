package dev.dmanluc.freenowchallenge.presentation.extensions

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
        // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.setDecorFitsSystemWindows(false)
        // finally, show the system bars
        window.insetsController?.show(WindowInsets.Type.statusBars())
        window.insetsController?.hide(WindowInsets.Type.navigationBars())
    } else {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }