package dev.dmanluc.freenowchallenge.presentation.feature.vehiclesmap

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class TouchInterceptLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) : CoordinatorLayout(context, attributeSet) {

    var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    var viewToIntercept: View? = null

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev ?: return false
        if (bottomSheetBehavior != null && viewToIntercept != null) {
            bottomSheetBehavior?.isDraggable =
                !isViewInBounds(viewToIntercept, ev.rawX.toInt(), ev.rawY.toInt())
        }
        return false
    }
}

private fun isViewInBounds(view: View?, x: Int, y: Int): Boolean {
    view ?: return false

    val outRect = Rect()
    val location = IntArray(2)
    view.getDrawingRect(outRect)
    view.getLocationOnScreen(location)
    outRect.offset(location.get(0), location.get(1))
    return outRect.contains(x, y)
}