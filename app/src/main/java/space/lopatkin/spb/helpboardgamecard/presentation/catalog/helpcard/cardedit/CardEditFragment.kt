package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit;

import android.animation.LayoutTransition;
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
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCardEditBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;
import space.lopatkin.spb.helpboardgamecard.presentation.KeyboardDoneEvent;
import space.lopatkin.spb.keyboard.KeyboardCapabilities;
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType;

import javax.inject.Inject;

public class CardEditFragment extends AbstractFragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private CardEditViewModel viewModel;
    private FragmentCardEditBinding binding;
    private NavController navController;
    private InputConnection inputConnection;
    private int idCard = 0;
    private String effects = "";
    private boolean favorites = false;
    private boolean lock = false;
    private int priority = 0;

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
        binding = FragmentCardEditBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CardEditViewModel.class);

        if (getArguments() != null) {
            CardEditFragmentArgs args = CardEditFragmentArgs.fromBundle(getArguments());
            int helpcardId = args.getId();
            if (helpcardId > 0) {
                loadHelpcard(helpcardId);
            }
        }

        loadKeyboardType();
        setAnimationSizeForExpandableViews();

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu,
                                    @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card_save:
                viewModel.update(getEditedData());
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
            binding.keyboardCardEdit.setVisibility(View.GONE);
        }
    }

    private void clearFocus() {
        if (binding.editTitle.isFocused()) {
            binding.editTitle.clearFocus();
        }
        if (binding.editDescription.isFocused()) {
            binding.editDescription.clearFocus();
        }
        if (binding.editVictoryCondition.isFocused()) {
            binding.editVictoryCondition.clearFocus();
        }
        if (binding.editEndGame.isFocused()) {
            binding.editEndGame.clearFocus();
        }
        if (binding.editPreparation.isFocused()) {
            binding.editPreparation.clearFocus();
        }
        if (binding.editPlayerTurn.isFocused()) {
            binding.editPlayerTurn.clearFocus();
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
        binding.editTitle.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.editDescription.setImeOptions(EditorInfo.IME_ACTION_DONE);

        binding.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        binding.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    }

    private void setupViewsForCustomKeyboard() {
        binding.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT);

        binding.editTitle.setShowSoftInputOnFocus(false);
        binding.editDescription.setShowSoftInputOnFocus(false);
        binding.editVictoryCondition.setShowSoftInputOnFocus(false);
        binding.editEndGame.setShowSoftInputOnFocus(false);
        binding.editPreparation.setShowSoftInputOnFocus(false);
        binding.editPlayerTurn.setShowSoftInputOnFocus(false);

        onActionTitle();
        onActionDescription();
        onActionVictoryCondition();
        onActionEndGame();
        onActionPreparation();
        onActionPlayerTurn();
    }

    private void loadHelpcard(int helpcardId) {
        viewModel.loadHelpcard(helpcardId);

        viewModel.helpcard.observe(getViewLifecycleOwner(), helpcard -> {
            if (helpcard != null) {
                binding.editTitle.setText(helpcard.getTitle());
                binding.editDescription.setText(helpcard.getDescription());
                binding.editVictoryCondition.setText(helpcard.getVictoryCondition());
                binding.editEndGame.setText(helpcard.getEndGame());
                binding.editPreparation.setText(helpcard.getPreparation());
                binding.editPlayerTurn.setText(helpcard.getPlayerTurn());

                idCard = helpcard.getId();
                effects = helpcard.getEffects();
                favorites = helpcard.isFavorites();
                lock = helpcard.isLock();
                priority = helpcard.getPriority();
            }
        });
    }

    private Helpcard getEditedData() {
        return new Helpcard(
                idCard,
                binding.editTitle.getText().toString(),
                binding.editVictoryCondition.getText().toString(),
                binding.editEndGame.getText().toString(),
                binding.editPreparation.getText().toString(),
                binding.editDescription.getText().toString(),
                binding.editPlayerTurn.getText().toString(),
                effects,
                favorites,
                lock,
                priority);
    }

    private void navigateToCatalog() {
        navController.navigate(CardEditFragmentDirections.actionNavCardEditToNavCatalog());
    }

    private void onActionTitle() {
        binding.editTitle.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS);
            }
        });
    }

    private void onActionDescription() {
        binding.editDescription.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS);
            }
        });
    }

    private void onActionVictoryCondition() {
        binding.editVictoryCondition.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editVictoryCondition);
            }
        });
    }

    private void onActionEndGame() {
        binding.editEndGame.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editEndGame);
            }
        });
    }

    private void onActionPreparation() {
        binding.editPreparation.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editPreparation);
            }
        });
    }

    private void onActionPlayerTurn() {
        binding.editPlayerTurn.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS);
                scrollTo(binding.editPlayerTurn);
            }
        });
    }

    private void enableCustomKeyboard(View view, KeyboardCapabilities capabilities) {
        if (inputConnection != null) {
            inputConnection.closeConnection();
        }
        inputConnection = view.onCreateInputConnection(new EditorInfo());
        binding.keyboardCardEdit.setInputConnection(inputConnection);
        binding.keyboardCardEdit.setCapabilities(capabilities);
        binding.keyboardCardEdit.setVisibility(View.VISIBLE);
    }

    private void scrollTo(EditText view) {
        binding.keyboardCardEdit.setHeightFragment(binding.layoutCardEdit.getHeight());
        binding.keyboardCardEdit.setScrollView(binding.scrollCardEdit);
        binding.keyboardCardEdit.scrollEditTextToKeyboard(view);
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
                showMessage(binding.scrollCardEdit, R.string.message_insert_title);
                break;
            case ACTION_ENDED_SUCCESS:
                showMessage(binding.scrollCardEdit, R.string.message_card_updated);
                navigateToCatalog();
                break;
            case ACTION_ENDED_ERROR:
                showMessage(binding.scrollCardEdit, R.string.error_action_ended);
                break;
        }
    }

    private void setAnimationSizeForExpandableViews() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        binding.layoutExpandableCardedit.setLayoutTransition(layoutTransition);
    }

}