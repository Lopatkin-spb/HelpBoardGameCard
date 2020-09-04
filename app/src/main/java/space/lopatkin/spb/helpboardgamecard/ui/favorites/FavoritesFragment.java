package space.lopatkin.spb.helpboardgamecard.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import space.lopatkin.spb.helpboardgamecard.R;

public class FavoritesFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);


        final TextView textView = root.findViewById(R.id.text_settings);

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity mainActivity = new MainActivity();
//                mainActivity.startNewcardFragment();
//            }
//        });

        Button buttonToRecordFragment = root.findViewById(R.id.buttonRecFrag);

        buttonToRecordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eeeeeee();

                Navigation.findNavController(view).navigate(R.id.action_nav_settings_to_nav_newcard);
            }
        });



        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
//
//    @SuppressLint("ResourceType")
//    private void eeeeeee( ) {
//        NewCardFragment newCardFragment = new NewCardFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()  ;
//        fragmentTransaction.replace(R.id.flContent , newCardFragment);
//        fragmentTransaction.commit();
//
//
//
//    }
}