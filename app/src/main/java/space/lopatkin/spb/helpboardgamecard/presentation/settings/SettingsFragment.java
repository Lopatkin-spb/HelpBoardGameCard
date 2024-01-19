package space.lopatkin.spb.helpboardgamecard.presentation.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentSettingsBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;

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

        loadKeyboardType();
        onActionSpinner();
    }

    @Override
    public void onResume() {
        super.onResume();
        actionEndedListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadKeyboardType() {
        viewModel.loadKeyboardType();

        viewModel.keyboardType.observe(getViewLifecycleOwner(), keyboardType -> {
            if (binding != null) {
                binding.actionSpinnerKeyboards.setSelection(keyboardType.ordinal());
            }
        });
    }

    private void onActionSpinner() {
        SpinnerInteractionListener listener = new SpinnerInteractionListener(viewModel);
        binding.actionSpinnerKeyboards.setOnTouchListener(listener);
        binding.actionSpinnerKeyboards.setOnItemSelectedListener(listener);
    }

    private void actionEndedListener() {
        viewModel.message.observe(this, messageType -> {
            if (messageType != Message.DEFAULT) {
                selectingTextFrom(messageType);
            }
        });
    }

    private void selectingTextFrom(Message type) {
        switch (type) {
            case ACTION_ENDED_SUCCESS:
                showMessage(R.string.message_action_ended_success);
                break;
            case ACTION_ENDED_ERROR:
                showMessage(R.string.error_action_ended);
                break;
        }
    }

    //todo: move to main activity after add eventBus
    private void showMessage(int message) {
        Snackbar.make(binding.layoutSettings, message, Snackbar.LENGTH_SHORT).show();
    }

}