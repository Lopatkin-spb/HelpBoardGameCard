package space.lopatkin.spb.helpboardgamecard.ui.helpcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import space.lopatkin.spb.helpboardgamecard.R;


public class HelpcardFragment extends Fragment {

    public static final String EXTRA_TITLE =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "space.lopatkin.spb.testnavdrawer.ui.add.EXTRA_PRIORITY";

    private TextView textViewTitle;
    private TextView textViewVictoryCondition;
    private TextView textViewEndGame;
    private TextView textViewPreparation;
    private TextView textViewDescription;
    private TextView textViewPlayerTurn;
    private TextView textViewEffects;
    //    private boolean editCheckFavorites;
    private TextView textViewPriority;


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
        View root = inflater.inflate(R.layout.fragment_helpcard, container, false);


        //инициализация полей вью
        textViewTitle = root.findViewById(R.id.text_view_title);
        textViewVictoryCondition = root.findViewById(R.id.text_view_victory_condition);
        textViewEndGame = root.findViewById(R.id.text_view_end_game);
        textViewPreparation = root.findViewById(R.id.text_view_preparation);
        textViewDescription = root.findViewById(R.id.text_view_description);
        textViewPlayerTurn = root.findViewById(R.id.text_view_player_turn);
        textViewEffects = root.findViewById(R.id.text_view_effects);
//        editCheckFavorites = false;
        textViewPriority = root.findViewById(R.id.text_view_priority);


        //устанавливает в верхнем меню левую иконку
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //устанавливает тайтл динамически
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_view_card);

//        //разрешает верхнее правое меню
//        setHasOptionsMenu(true);


        return root;
    }


//    private void saveHelpcard() {
//        //забор данных в переменные
//        String messageTitle = editTextTitle.getText().toString();
//        String messageVictoryCondition = editTextVictoryCondition.getText().toString();
//        String messageEndGame = editTextEndGame.getText().toString();
//        String messagePreparation = editTextPreparation.getText().toString();
//        String messageDescription = editTextDescription.getText().toString();
//
//        String messagePlayerTurn = editTextPlayerTurn.getText().toString();
//        String messageEffects = editTextEffects.getText().toString();
//
//        boolean messageFavorites = editCheckFavorites;
//        int messagePriority = numberPickerPriority.getValue();
//
////        if (messageTitle.trim().isEmpty() || messageDescription.trim().isEmpty()) {
//        if (messageTitle.trim().isEmpty()) {
//            Toast.makeText(getActivity(), "Please insert a title", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
////        //интент для обычного передачи результата
////        Intent data = new Intent();
////        data.putExtra(EXTRA_TITLE, title);
////        data.putExtra(EXTRA_DESCRIPTION, description);
////        data.putExtra(EXTRA_PRIORITY, priority);
////        getActivity().setResult(RESULT_OK, data);
////
////        Toast.makeText(getActivity(), "" + data, Toast.LENGTH_LONG).show();
////        //vstavit navigatiu
//
//
//        //v5safeargs
//        AddcardEditcardFragmentDirections.ActionNavAddcardToNavHelpcard action =
//                AddcardEditcardFragmentDirections.actionNavAddcardToNavHelpcard();
//        action.setMessageTitle(messageTitle);
//
//        action.setMessageVictoryCondition(messageVictoryCondition);
//        action.setMessageEndGame(messageEndGame);
//        action.setMessagePreparation(messagePreparation);
//
//        action.setMessageDescription(messageDescription);
//
//        action.setMessagePlayerTurn(messagePlayerTurn);
//        action.setMessageEffects(messageEffects);
//
//
//        action.setMessageFavorites(messageFavorites);
//        action.setMessagePriority(messagePriority);
//
//        //если перепись заметки то отправляется старый АйДи
//        AddcardEditcardFragmentArgs args = AddcardEditcardFragmentArgs.fromBundle(getArguments());
//        int id = args.getMessageId();
//        if (id != -1) {
//            action.setMessageId(id);
//        }
//
//        navController.navigate(action);
//    }
//


    //v5safeargs
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getView());

        if (getArguments() != null) {
            HelpcardFragmentArgs args = HelpcardFragmentArgs.fromBundle(getArguments());

            //установка значений в поля
            textViewTitle.setText(args.getMessageTitle());
            textViewVictoryCondition.setText(args.getMessageVictoryCondition());
            textViewEndGame.setText(args.getMessageEndGame());
            textViewPreparation.setText(args.getMessagePreparation());
            textViewDescription.setText(args.getMessageDescription());
            textViewPlayerTurn.setText(args.getMessagePlayerTurn());
            textViewEffects.setText(args.getMessageEffects());
//                t = args.getMessageFavorites();
            textViewPriority.setText(String.valueOf(args.getMessagePriority()));

        }
    }


}


