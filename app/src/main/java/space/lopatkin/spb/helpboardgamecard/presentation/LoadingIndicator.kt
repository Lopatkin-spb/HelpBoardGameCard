package space.lopatkin.spb.helpboardgamecard.presentation

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class LoadingIndicator(context: Context, attributeSet: AttributeSet) : SwipeRefreshLayout(context, attributeSet) {

    init {
        setColorSchemeColors(Color.GRAY, Color.WHITE, Color.BLACK)
        setProgressBackgroundColorSchemeColor(Color.LTGRAY)

        isEnabled = false // Disable swipe
    }

}