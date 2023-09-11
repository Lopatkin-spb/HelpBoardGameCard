package space.lopatkin.spb.helpboardgamecard.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentSettingsBinding;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;

public class SettingsFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SettingsViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.action_spinner_keyboards, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.actionSpinnerKeyboards.setAdapter(adapter);

        onActionSpinner();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getKeyboardVariant().observe(this, data -> {
            if (binding != null) {
                binding.actionSpinnerKeyboards.setSelection(data.ordinal());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onActionSpinner() {
        binding.actionSpinnerKeyboards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object userChoice = parent.getItemAtPosition(position);
                viewModel.saveKeyboardVariant(userChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}