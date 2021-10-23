package dev.dmanluc.freenowchallenge.presentation.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Context.bitmapDescriptorFromVector(@DrawableRes vectorResId: Int, sizeInPx: Int? = null): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, vectorResId)?.run {
        setBounds(0, 0, sizeInPx ?: intrinsicWidth, sizeInPx ?: intrinsicHeight)
        val bitmap = Bitmap.createBitmap(sizeInPx ?: intrinsicWidth, sizeInPx ?: intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}