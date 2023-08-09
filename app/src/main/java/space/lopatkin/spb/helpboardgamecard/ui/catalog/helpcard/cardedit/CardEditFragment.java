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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;

public class CardEditFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private CardEditViewModel viewModel;
    private ScrollView parentView;
    private EditText editTitle;
    private EditText editDescription;
    private EditText editVictoryCondition;
    private EditText editEndGame;
    private EditText editPreparation;
    private EditText editPlayerTurn;
    //values temp state start
    private int idCard = 0;
    private String effects = "";
    private boolean favorites = false;
    private boolean lock = false;
    private int priority = 0;
    //values temp state end

    private NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_card_edit, container, false);
        parentView = root.findViewById(R.id.scroll_card_edit);
        editTitle = root.findViewById(R.id.edit_title);
        editDescription = root.findViewById(R.id.edit_description);
        editVictoryCondition = root.findViewById(R.id.edit_victory_condition);
        editEndGame = root.findViewById(R.id.edit_end_game);
        editPreparation = root.findViewById(R.id.edit_preparation);
        editPlayerTurn = root.findViewById(R.id.edit_player_turn);
        setScreenTitle(R.string.title_card_edit);
        setHasOptionsMenu(true);
        setupEditViews();
        return root;
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
        hideKeyboard();

        switch (item.getItemId()) {
            case R.id.action_card_save:
                cardSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupEditViews() {
        editTitle.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editDescription.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void loadCardDetails(int cardId) {
        viewModel.setCardId(cardId);
        viewModel.getCardDetails.observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
            @Override
            public void onChanged(Helpcard card) {
                if (card != null) {
                    editTitle.setText(card.getTitle());
                    editDescription.setText(card.getDescription());
                    editVictoryCondition.setText(card.getVictoryCondition());
                    editEndGame.setText(card.getEndGame());
                    editPreparation.setText(card.getPreparation());
                    editPlayerTurn.setText(card.getPlayerTurn());

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
        String messageTitle = editTitle.getText().toString();
        String messageDescription = editDescription.getText().toString();
        String messageVictoryCondition = editVictoryCondition.getText().toString();
        String messageEndGame = editEndGame.getText().toString();
        String messagePreparation = editPreparation.getText().toString();
        String messagePlayerTurn = editPlayerTurn.getText().toString();

        return new Helpcard(idCard, messageTitle, messageVictoryCondition,
                messageEndGame, messagePreparation, messageDescription,
                messagePlayerTurn, effects, favorites, lock, priority);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void showMessage(int message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
    }

    private void setScreenTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

    private void navigateToCatalog() {
        navController.navigate(CardEditFragmentDirections.actionNavCardEditToNavCatalog());
    }

}