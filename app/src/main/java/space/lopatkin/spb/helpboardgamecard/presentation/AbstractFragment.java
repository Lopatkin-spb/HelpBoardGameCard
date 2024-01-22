package space.lopatkin.spb.helpboardgamecard.presentation;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractFragment extends Fragment {

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        setHasOptionsMenu(true);

        super.onAttach(context);
    }

    protected void showMessage(View parentView, int message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
    }

}
