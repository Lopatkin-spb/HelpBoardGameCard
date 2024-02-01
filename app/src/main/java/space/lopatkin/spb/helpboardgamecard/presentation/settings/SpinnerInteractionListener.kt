package space.lopatkin.spb.helpboardgamecard.presentation.settings

import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView

class SpinnerInteractionListener : AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private var onEndActionListener: OnEndActionListener? = null
    private var userSelection = false

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        userSelection = true
        return false
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

        if (onEndActionListener != null && userSelection) {
            val userChoice: Any? = parent.getItemAtPosition(position)
            onEndActionListener?.onUserSelection(userChoice)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    fun setEndActionListener(onEndActionListener: OnEndActionListener) {
        this.onEndActionListener = onEndActionListener
    }

    fun interface OnEndActionListener {

        fun onUserSelection(selection: Any?)

    }

}
