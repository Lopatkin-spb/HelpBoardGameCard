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


public class AddcardEditcardFragment extends Fragment {

    public static final String EXTRA_TITLE =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private boolean editCheckFavorites;
    private NumberPicker numberPickerPriority;


    NavController navController;

//    //включение режим вывода елементов фрагмента в action bar
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }


    //создание вью
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcard, container, false);

        editTextTitle = root.findViewById(R.id.edit_text_title);
        editTextDescription = root.findViewById(R.id.edit_text_description);
        editCheckFavorites = false;
        numberPickerPriority = root.findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        //устанавливает в верхнем меню левую иконку
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //устанавливает тайтл динамически
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_addcard);

        //разрешает верхнее правое меню
        setHasOptionsMenu(true);


//        //вызов намерения из метода старт
//        if (getActivity().getIntent().hasExtra(EXTRA_HELPCARD)) {
//
//            getActivity().getApplicationContext();
//            helpCard = getActivity().getIntent().getParcelableExtra(EXTRA_HELPCARD);
//            editText.setText(helpCard.text);
//        } else {
//            helpCard = new HelpCard();
//
//        }


        return root;
    }


//    //создание меню
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //извлечение меню
//        getMenuInflater().inflate(R.menu.menu_details, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


//    //обработка для событий
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//            case R.id.action_save:
//                if (editText.getText().length() > 0) {
//                    helpCard.text = editText.getText().toString();
//                    helpCard.favorites = false;
//                    helpCard.timestamp = System.currentTimeMillis();
//
//                    if (getIntent().hasExtra(EXTRA_HELPCARD)) {
//                        App.getInstance().getHelpCardDao().update(helpCard);
//                    } else {
//                        App.getInstance().getHelpCardDao().insert(helpCard);
//                    }
//
//                    finish();
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
////            case android.R.id.home:
////                finish();
////                break;
//            case R.id.action_save:
//                if (editText.getText().length() > 0) {
//                    helpCard.text = editText.getText().toString();
//                    helpCard.favorites = false;
//                    helpCard.timestamp = System.currentTimeMillis();
//
//                    if (getActivity().getIntent().hasExtra(EXTRA_HELPCARD)) {
//                        App.getInstance().getHelpCardDao().update(helpCard);
//                    } else {
//                        App.getInstance().getHelpCardDao().insert(helpCard);
//                    }
//
//                    //closeFragment();
//                    //finish();
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


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
        String messageDescription = editTextDescription.getText().toString();
        boolean messageFavorites = editCheckFavorites;
        int messagePriority = numberPickerPriority.getValue();
        if (messageTitle.trim().isEmpty() || messageDescription.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

//        //интент для обычного передачи результата
//        Intent data = new Intent();
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_PRIORITY, priority);
//        getActivity().setResult(RESULT_OK, data);
//
//        Toast.makeText(getActivity(), "" + data, Toast.LENGTH_LONG).show();
//        //vstavit navigatiu


        //v5safeargs
        AddcardEditcardFragmentDirections.ActionNavAddcardToNavHelpcard action =
                AddcardEditcardFragmentDirections.actionNavAddcardToNavHelpcard();
        action.setMessageTitle(messageTitle);
        action.setMessageDescription(messageDescription);
        action.setMessageFavorites(messageFavorites);
        action.setMessagePriority(messagePriority);

        //если перепись заметки то отправляется старый АйДи
        AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
        int id = args.getMessageId();
        if (id != -1) {
            action.setMessageId(id);
        }

        navController.navigate(action);
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


    //v5safeargs
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());

        if (getArguments() != null) {
            AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
            int id = args.getMessageId();

            if (id == -1) {
                //смена названия заголовка в тулбаре
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_addcard);


            } else {
                //смена названия заголовка в тулбаре
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_edit_card);

                //установка значений в поля для редактирования
                editTextTitle.setText(args.getMessageTitle());
                editTextDescription.setText(args.getMessageDescription());
                editCheckFavorites = args.getMessageFavorites();
                numberPickerPriority.setValue(args.getMessagePriority());
            }
        }
    }


}

