package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class AddcardEditcardFragment extends Fragment {
    private EditText editTextTitle;
    private EditText editTextVictoryCondition;
    private EditText editTextEndGame;
    private EditText editTextPreparation;
    private EditText editTextDescription;
    private EditText editTextPlayerTurn;
    private EditText editTextEffects;
    private boolean editCheckFavorites;
    private boolean editCheckLock;
    private NumberPicker numberPickerPriority;
    private NavController navController;
    private int buttonClose = R.drawable.ic_close;
    private int toolbarTitleEditcard = R.string.menu_edit_card;
    private int toolbarTitleAddcard = R.string.menu_addcard;
    private String insertTitle = "Please insert a title";

    //создание вью
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcard, container, false);
        initializationViewField(root);
        setUpButtonClose();
        setToolbarTitle(toolbarTitleAddcard);
        //разрешает верхнее правое меню
        setHasOptionsMenu(true);
        return root;
    }

    //создание меню верхнего справа
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.addcard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //что происходит в меню на нажатие определенных иконок
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveData();
                hideKeyboard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //safeargs + parcelable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());
        if (getArguments() != null) {
            AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
            Helpcard messageHelpcardFromCatalog = args.getHelpcard();
            int id = 0;
            if (messageHelpcardFromCatalog != null) {
                id = messageHelpcardFromCatalog.getId();
                if (id == -1) {
                    setToolbarTitle(toolbarTitleAddcard);
                } else {
                    setToolbarTitle(toolbarTitleEditcard);
                    setDataForEdit(messageHelpcardFromCatalog);
                }
            }
        }
    }

    private void initializationViewField(View root) {
        editTextTitle = root.findViewById(R.id.edit_text_title);
        editTextVictoryCondition = root.findViewById(R.id.edit_text_victory_condition);
        editTextEndGame = root.findViewById(R.id.edit_text_end_game);
        editTextPreparation = root.findViewById(R.id.edit_text_preparation);
        editTextDescription = root.findViewById(R.id.edit_text_description);
        editTextPlayerTurn = root.findViewById(R.id.edit_text_player_turn);
        editTextEffects = root.findViewById(R.id.edit_text_effects);
        editCheckFavorites = false;
        editCheckLock = false;
        numberPickerPriority = root.findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);
    }

    private void setUpButtonClose() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(buttonClose);
    }

    private void saveData() {
        Helpcard data = getData();
        String title = editTextTitle.getText().toString();
        if (title.trim().isEmpty()) {
            showSystemMessage(insertTitle);
            return;
        }
        //если перепись заметки то отправляется старый АйДи
        AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
        Helpcard messageHelpcardFromCatalog = args.getHelpcard();
        int id = 0;
        if (messageHelpcardFromCatalog != null) {
            id = messageHelpcardFromCatalog.getId();
            if (id != -1) {
                data.setId(id);
            }
        }
        navigationAndSend(data);
        //TODO выключить етот фрагмент навигацией
    }

    private void showSystemMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private Helpcard getData() {
        String messageTitle = editTextTitle.getText().toString();
        String messageVictoryCondition = editTextVictoryCondition.getText().toString();
        String messageEndGame = editTextEndGame.getText().toString();
        String messagePreparation = editTextPreparation.getText().toString();
        String messageDescription = editTextDescription.getText().toString();
        String messagePlayerTurn = editTextPlayerTurn.getText().toString();
        String messageEffects = editTextEffects.getText().toString();
        boolean messageFavorites = editCheckFavorites;
        boolean messageLock = editCheckLock;
        int messagePriority = numberPickerPriority.getValue();
        Helpcard h = new Helpcard(messageTitle, messageVictoryCondition,
                messageEndGame, messagePreparation, messageDescription,
                messagePlayerTurn, messageEffects, messageFavorites, messageLock, messagePriority);
        return h;
    }

    private void navigationAndSend(Helpcard helpcard) {
        AddcardEditcardFragmentDirections.ActionNavAddcardToNavCatalog action =
                AddcardEditcardFragmentDirections.actionNavAddcardToNavCatalog()
                        .setHelpcard(helpcard);
        navController.navigate(action);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setDataForEdit(Helpcard data) {
        editTextTitle.setText(data.getTitle());
        editTextVictoryCondition.setText(data.getVictoryCondition());
        editTextEndGame.setText(data.getEndGame());
        editTextPreparation.setText(data.getPreparation());
        editTextDescription.setText(data.getDescription());
        editTextPlayerTurn.setText(data.getPlayerTurn());
        editTextEffects.setText(data.getEffects());
        editCheckFavorites = data.isFavorites();
        editCheckLock = data.isLock();
        numberPickerPriority.setValue(data.getPriority());
    }

    private void setToolbarTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

}


