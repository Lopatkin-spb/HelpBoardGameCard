package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.app.App;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;

public class AddCardFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private AddCardViewModel viewModel;
    private ScrollView parentView;
    private EditText editTitle;
    private EditText editVictoryCondition;
    private EditText editEndGame;
    private EditText editPreparation;
    private EditText editDescription;
    private EditText editPlayerTurn;
    private EditText editEffects;
    private boolean editFavorites;
    private boolean editLock;
    private NumberPicker numberPickerPriority;
    private NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        ((App) context.getApplicationContext()).getAppComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcard, container, false);
        parentView = root.findViewById(R.id.scroll_addcard);
        editTitle = root.findViewById(R.id.edit_text_title);
        editVictoryCondition = root.findViewById(R.id.edit_text_victory_condition);
        editEndGame = root.findViewById(R.id.edit_text_end_game);
        editPreparation = root.findViewById(R.id.edit_text_preparation);
        editDescription = root.findViewById(R.id.edit_text_description);
        editPlayerTurn = root.findViewById(R.id.edit_text_player_turn);
        editEffects = root.findViewById(R.id.edit_text_effects);
        numberPickerPriority = root.findViewById(R.id.number_picker_priority);
        editFavorites = false;
        editLock = false;
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
        setScreenTitle(R.string.title_addcard);
        //разрешает верхнее правое меню
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(AddCardViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.addcard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNewHelpcard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNewHelpcard() {
        Helpcard newHelpcard = getData();
        if (newHelpcard.getTitle().isEmpty()) {
            showMessage(R.string.message_insert_title);
            return;
        }
        viewModel.saveNewHelpcard(newHelpcard);
        showMessage(R.string.message_helpcard_saved);
        hideKeyboard();
        navigateToCatalog();
    }

    private Helpcard getData() {
        String messageTitle = editTitle.getText().toString();
        String messageVictoryCondition = editVictoryCondition.getText().toString();
        String messageEndGame = editEndGame.getText().toString();
        String messagePreparation = editPreparation.getText().toString();
        String messageDescription = editDescription.getText().toString();
        String messagePlayerTurn = editPlayerTurn.getText().toString();
        String messageEffects = editEffects.getText().toString();
        boolean messageFavorites = editFavorites;
        boolean messageLock = editLock;
        int messagePriority = numberPickerPriority.getValue();
        Helpcard h = new Helpcard(messageTitle, messageVictoryCondition,
                messageEndGame, messagePreparation, messageDescription,
                messagePlayerTurn, messageEffects, messageFavorites, messageLock, messagePriority);
        return h;
    }

    private void navigateToCatalog() {
        AddCardFragmentDirections.ActionNavAddcardToNavCatalog action =
                AddCardFragmentDirections.actionNavAddcardToNavCatalog();
        navController.navigate(action);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setScreenTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

    private void showMessage(int message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
    }

}


