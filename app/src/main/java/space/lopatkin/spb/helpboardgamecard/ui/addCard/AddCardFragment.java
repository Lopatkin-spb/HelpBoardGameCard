package space.lopatkin.spb.helpboardgamecard.ui.addCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;


public class AddCardFragment extends Fragment {

    public static final String EXTRA_HELPCARD = "space.lopatkin.spb.helpboardgamecard.ui.newCard.EXTRA_HELPCARD";
    //private static Intent intent;

    private Helpcard helpCard;

    private EditText editText;


    //включение режим вывода елементов фрагмента в action bar
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    //    //метод вызова етого активити извне
//    // (такой способ упрощает код -
//    // чем прописывать каждый раз при вызове -
//    // проще написать один раз в самом активити)
//    public static void start(Fragment caller, HelpCard helpCard) {
//        Intent intent = new Intent(caller, NewCardFragment.class);
//        if (helpCard != null) {
//            intent.putExtra(EXTRA_HELPCARD, helpCard);
//        }
//        caller.startActivity(intent);
//    }


    //создание вью
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addcard, container, false);

        editText = root.findViewById(R.id.edit_text);


        // разобраться с кнопкой впоследствии
//        setSupportActionBar();
        //  setTitle


        //editText.setText(getArguments().getString("context"));


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


    private void saveCard() {
        String newCard = editText.getText().toString();

        if (newCard.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please insert a text", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_HELPCARD, newCard);

        //выключить етот фрагмент навигацией

    }


    //создание меню
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
                saveCard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //private void closeFragment() {
    //     getActivity().getFragmentManager().popBackStack();
    //}

}