package space.lopatkin.spb.helpboardgamecard.presentation.settings

import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView

class SpinnerInteractionListener(
    private val viewModel: SettingsViewModel
) : AdapterView.OnItemSelectedListener,
    View.OnTouchListener {

    private var userSelection = false
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        userSelection = true
        return false
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (userSelection) {
            val userChoice = parent.getItemAtPosition(position)
            viewModel.saveKeyboardType(userChoice)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}
