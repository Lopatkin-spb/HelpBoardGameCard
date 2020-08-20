package space.lopatkin.spb.helpboardgamecard.ui.helpCard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import space.lopatkin.spb.helpboardgamecard.R;

public class HelpCardFragment extends Fragment {

    private HelpCardViewModel helpCardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpCardViewModel =
                ViewModelProviders.of(this).get(HelpCardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_helpcard, container, false);
        final TextView textView = root.findViewById(R.id.text_helpcard);
        helpCardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}