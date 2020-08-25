package space.lopatkin.spb.helpboardgamecard.ui.newCard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import space.lopatkin.spb.helpboardgamecard.App;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewCardFragment extends Fragment {

    private static final String EXTRA_HELPCARD = "NewCardFragment.EXTRA_HELPCARD";
    //private static Intent intent;

    private HelpCard helpCard;

    private EditText editText;


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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newcard, container, false);


//        //knopka nazad тулбара
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        setTitle(getString(R.string.newCard_title));


        editText = root.findViewById(R.id.text);

        //вызов намерения из метода старт
        if (getActivity().getIntent().hasExtra(EXTRA_HELPCARD)) {
            helpCard = getActivity().getIntent().getParcelableExtra(EXTRA_HELPCARD);
            editText.setText(helpCard.text);
        } else {
            helpCard = new HelpCard();
        }


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


    //private void closeFragment() {
   //     getActivity().getFragmentManager().popBackStack();
    //}

}