package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCardEditBinding;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;
import space.lopatkin.spb.helpboardgamecard.ui.KeyboardDoneEvent;
import space.lopatkin.spb.helpboardgamecard.ui.utils.keyboard.KeyboardCapabilities;
import space.lopatkin.spb.helpboardgamecard.ui.utils.keyboard.KeyboardVariant;

import javax.inject.Inject;

public class CardEditFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private CardEditViewModel viewModel;
    private FragmentCardEditBinding binding;
    private int idCard = 0;
    private String effects = "";
    private boolean favorites = false;
    private boolean lock = false;
    private int priority = 0;
    private NavController navController;
    private InputConnection inputConnection;

    //todo: move to absFrag
    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardEditBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setScreenTitle(R.string.title_card_edit);
        setHasOptionsMenu(true);
        setupEditViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CardEditViewModel.class);

        if (getArguments() != null) {
            CardEditFragmentArgs args = CardEditFragmentArgs.fromBundle(getArguments());
            int cardId = args.getId();
            if (cardId > 0) {
                loadCardDetails(cardId);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onKeyboardDoneEvent(new KeyboardDoneEvent());

        switch (item.getItemId()) {
            case R.id.action_card_save:
                cardSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getKeyboardVariant().observe(this, data -> {
            if (data == KeyboardVariant.CUSTOM) {
                setupViewsForCustomKeyboard();
            }
        });
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardDoneEvent(KeyboardDoneEvent event) {
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
        binding.keyboardCardEdit.setVisibility(View.GONE);
    }

    private void setupEditViews() {
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

    private void loadCardDetails(int cardId) {
        viewModel.setCardId(cardId);
        viewModel.getCardDetails.observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
            @Override
            public void onChanged(Helpcard card) {
                if (card != null) {
                    binding.editTitle.setText(card.getTitle());
                    binding.editDescription.setText(card.getDescription());
                    binding.editVictoryCondition.setText(card.getVictoryCondition());
                    binding.editEndGame.setText(card.getEndGame());
                    binding.editPreparation.setText(card.getPreparation());
                    binding.editPlayerTurn.setText(card.getPlayerTurn());

                    idCard = card.getId();
                    effects = card.getEffects();
                    favorites = card.isFavorites();
                    lock = card.isLock();
                    priority = card.getPriority();
                }
            }
        });
    }

    private void cardSave() {
        Helpcard editedCard = getEditedData();
        if (editedCard.getTitle().isEmpty()) {
            showMessage(R.string.message_insert_title);
            return;
        }
        viewModel.update(editedCard);
        showMessage(R.string.message_card_updated);
        navigateToCatalog();
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

    //todo: move to activity
    private void showMessage(int message) {
        Snackbar.make(binding.scrollCardEdit, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setScreenTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
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

}