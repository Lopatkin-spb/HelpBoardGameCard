package space.lopatkin.spb.helpboardgamecard.presentation.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentSettingsBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;

import javax.inject.Inject;

public class SettingsFragment extends AbstractFragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(SettingsViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
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
        resultListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void showMessage(View parentView, int message) {
        super.showMessage(parentView, message);
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

    private void resultListener() {
        viewModel.message.observe(this, messageType -> {
            if (messageType != Message.POOL_EMPTY) {
                selectingTextFrom(messageType);
            }
        });
    }

    private void selectingTextFrom(Message type) {
        switch (type) {
            case ACTION_ENDED_SUCCESS:
                showMessage(binding.layoutSettings, R.string.message_action_ended_success);
                break;
            case ACTION_ENDED_ERROR:
                showMessage(binding.layoutSettings, R.string.error_action_ended);
                break;
        }
    }

}