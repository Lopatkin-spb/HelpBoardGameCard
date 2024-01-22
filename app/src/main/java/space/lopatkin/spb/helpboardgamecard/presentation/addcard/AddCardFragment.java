package space.lopatkin.spb.helpboardgamecard.presentation.addcard;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentAddcardBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.KeyboardDoneEvent;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;
import space.lopatkin.spb.keyboard.KeyboardCapabilities;

import javax.inject.Inject;

public class AddCardFragment extends AbstractFragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private AddCardViewModel viewModel;
    private FragmentAddcardBinding binding;
    private NavController navController;
    private InputConnection inputConnection;

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
        binding = FragmentAddcardBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(AddCardViewModel.class);
        loadKeyboardType();

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu,
                                    @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.addcard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                viewModel.saveNewHelpcard(getData());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        resultListener();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardDoneEvent(KeyboardDoneEvent event) {
        clearFocus();
        if (binding != null) {
            binding.keyboardAddcard.setVisibility(View.GONE);
        }
    }

    private void clearFocus() {
        if (binding.editTextTitle.isFocused()) {
            binding.editTextTitle.clearFocus();
        }
        if (binding.editTextDescription.isFocused()) {
            binding.editTextDescription.clearFocus();
        }
        if (binding.editTextVictoryCondition.isFocused()) {
            binding.editTextVictoryCondition.clearFocus();
        }
        if (binding.editTextEndGame.isFocused()) {
            binding.editTextEndGame.clearFocus();
        }
        if (binding.editTextPreparation.isFocused()) {
            binding.editTextPreparation.clearFocus();
        }
        if (binding.editTextPlayerTurn.isFocused()) {
            binding.editTextPlayerTurn.clearFocus();
        }
        if (binding.editTextEffects.isFocused()) {
            binding.editTextEffects.clearFocus();
        }
    }

    private void loadKeyboardType() {
        viewModel.loadKeyboardType();

        viewModel.keyboardType.observe(getViewLifecycleOwner(), keyboardType -> {
            if (keyboardType == KeyboardType.CUSTOM) {
                setupViewsForCustomKeyboard();
            } else {
                setupViews();
            }
        });
    }

    private void setupViews() {
        binding.editTextTitle.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.editTextDescription.setImeOptions(EditorInfo.IME_ACTION_DONE);

        binding.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        binding.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);
    }

    private void setupViewsForCustomKeyboard() {
        binding.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextEffects.setRawInputType(InputType.TYPE_CLASS_TEXT);

        binding.editTextTitle.setShowSoftInputOnFocus(false);
        binding.editTextDescription.setShowSoftInputOnFocus(false);
        binding.editTextVictoryCondition.setShowSoftInputOnFocus(false);
        binding.editTextEndGame.setShowSoftInputOnFocus(false);
        binding.editTextPreparation.setShowSoftInputOnFocus(false);
        binding.editTextPlayerTurn.setShowSoftInputOnFocus(false);
        binding.editTextEffects.setShowSoftInputOnFocus(false);

        onActionTitle();
        onActionDescription();
        onActionVictoryCondition();
        onActionEndGame();
        onActionPreparation();
        onActionPlayerTurn();
        onActionEffects();
    }

    private Helpcard getData() {
        return new Helpcard(
                binding.editTextTitle.getText().toString(),
                binding.editTextVictoryCondition.getText().toString(),
                binding.editTextEndGame.getText().toString(),
                binding.editTextPreparation.getText().toString(),
                binding.editTextDescription.getText().toString(),
                binding.editTextPlayerTurn.getText().toString(),
                binding.editTextEffects.getText().toString(),
                false,
                false,
                binding.numberPickerPriority.getValue());
    }

    private void navigateToCatalog() {
        navController.navigate(AddCardFragmentDirections.actionNavAddcardToNavCatalog());
    }

    private void onActionTitle() {
        binding.editTextTitle.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS);
            }
        });
    }

    private void onActionDescription() {
        binding.editTextDescription.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS);
            }
        });
    }

    private void onActionVictoryCondition() {
        binding.editTextVictoryCondition.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editTextVictoryCondition);
            }
        });
    }

    private void onActionEndGame() {
        binding.editTextEndGame.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editTextEndGame);
            }
        });
    }

    private void onActionPreparation() {
        binding.editTextPreparation.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editTextPreparation);
            }
        });
    }

    private void onActionPlayerTurn() {
        binding.editTextPlayerTurn.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editTextPlayerTurn);
            }
        });
    }

    private void onActionEffects() {
        binding.editTextEffects.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editTextEffects);
            }
        });
    }

    private void enableCustomKeyboard(View view, KeyboardCapabilities capabilities) {
        if (inputConnection != null) {
            inputConnection.closeConnection();
        }
        inputConnection = view.onCreateInputConnection(new EditorInfo());
        binding.keyboardAddcard.setInputConnection(inputConnection);
        binding.keyboardAddcard.setCapabilities(capabilities);
        binding.keyboardAddcard.setVisibility(View.VISIBLE);
    }

    private void scrollTo(EditText view) {
        binding.keyboardAddcard.setHeightFragment(binding.containerAddcard.getHeight());
        binding.keyboardAddcard.setScrollView(binding.scrollAddcard);
        binding.keyboardAddcard.scrollEditTextToKeyboard(view);
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
            case ACTION_STOPPED:
                showMessage(binding.scrollAddcard, R.string.message_insert_title);
                break;
            case ACTION_ENDED_SUCCESS:
                showMessage(binding.scrollAddcard, R.string.message_helpcard_saved);
                navigateToCatalog();
                break;
            case ACTION_ENDED_ERROR:
                showMessage(binding.scrollAddcard, R.string.error_action_ended);
                break;
        }
    }

}


