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

    public static final String EXTRA_TITLE =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_PRIORITY";

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


    NavController navController;


    //создание вью
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcard, container, false);

        //инициализация полей вью
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

        //устанавливает в верхнем меню левую иконку
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //устанавливает тайтл динамически
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_addcard);

        //разрешает верхнее правое меню
        setHasOptionsMenu(true);

        return root;
    }






//    private void saveCard() {
//        String newCard = editText.getText().toString();
//
//        if (newCard.trim().isEmpty()) {
//            Toast.makeText(getContext(), "Please insert a text", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Intent data = new Intent();
//        data.putExtra(EXTRA_HELPCARD, newCard);
//
//        //выключить етот фрагмент навигацией
//
//    }


    private void saveHelpcard() {
        //забор данных в переменные
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

//        if (messageTitle.trim().isEmpty() || messageDescription.trim().isEmpty()) {
        if (messageTitle.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }


//safeargs + parcelable
        Helpcard helpcard = new Helpcard(messageTitle, messageVictoryCondition,
                messageEndGame, messagePreparation, messageDescription,
                messagePlayerTurn, messageEffects, messageFavorites, messageLock, messagePriority);
        //если перепись заметки то отправляется старый АйДи
        AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
        Helpcard messageHelpcardFromCatalog = args.getHelpcard();
        int id = 0;
        if (messageHelpcardFromCatalog != null) {
            id = messageHelpcardFromCatalog.getId();
            if (id != -1) {
                helpcard.setId(id);
            }
        }
        AddcardEditcardFragmentDirections.ActionNavAddcardToNavCatalog action =
                AddcardEditcardFragmentDirections.actionNavAddcardToNavCatalog()
                        .setHelpcard(helpcard);
        navController.navigate(action);

                //выключить етот фрагмент навигацией

    }


    private void hideKeyboard() {
        //скрытие клавиатуры
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
                saveHelpcard();
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
//смена названия заголовка в тулбаре
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_addcard);

                } else {
                    //смена названия заголовка в тулбаре
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_edit_card);

                    Helpcard helpcard = messageHelpcardFromCatalog;
                    //установка значений в поля для редактирования
                    editTextTitle.setText(helpcard.getTitle());
                    editTextVictoryCondition.setText(helpcard.getVictoryCondition());
                    editTextEndGame.setText(helpcard.getEndGame());
                    editTextPreparation.setText(helpcard.getPreparation());
                    editTextDescription.setText(helpcard.getDescription());
                    editTextPlayerTurn.setText(helpcard.getPlayerTurn());
                    editTextEffects.setText(helpcard.getEffects());
                    editCheckFavorites = helpcard.isFavorites();
                    editCheckLock = helpcard.isLock();
                    numberPickerPriority.setValue(helpcard.getPriority());
                }

            }


        }
    }


}


