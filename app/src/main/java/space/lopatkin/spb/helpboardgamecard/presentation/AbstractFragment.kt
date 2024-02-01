package space.lopatkin.spb.helpboardgamecard.presentation

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class AbstractFragment : Fragment() {
    override fun onAttach(context: Context) {
        setHasOptionsMenu(true)

        super.onAttach(context)
    }

    protected fun showMessage(parentView: View, message: Int) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()
    }

}
