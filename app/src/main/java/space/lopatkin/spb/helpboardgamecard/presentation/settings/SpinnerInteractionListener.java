package space.lopatkin.spb.helpboardgamecard.presentation.settings;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private boolean userSelection = false;

    private SettingsViewModel viewModel;

    public SpinnerInteractionListener(SettingsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelection = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (userSelection) {
            Object userChoice = parent.getItemAtPosition(position);
            viewModel.saveKeyboardType(userChoice);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
