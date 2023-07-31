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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

public class HelpcardFragment extends Fragment {

    private HelpcardViewModel viewModel;
    private TextView textVictoryCondition;
    private TextView textEndGame;
    private TextView textPreparation;
    private TextView textPlayerTurn;
    private TextView textEffects;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_helpcard, container, false);
        textVictoryCondition = root.findViewById(R.id.text_victory_condition);
        textEndGame = root.findViewById(R.id.text_end_game);
        textPreparation = root.findViewById(R.id.text_preparation);
        textPlayerTurn = root.findViewById(R.id.text_player_turn);
        textEffects = root.findViewById(R.id.text_effects);

        viewModel = new ViewModelProvider(requireActivity()).get(HelpcardViewModel.class);

        return root;
    }


    //safeargs + parcelable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            HelpcardFragmentArgs args = HelpcardFragmentArgs.fromBundle(getArguments());
            int id = args.getId();
            if (id >= 0) {
                loadDetails(id);
            } else {
                //устанавливает тайтл динамически
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_view_card);
            }
        }
    }

    private void loadDetails(int id) {
        viewModel.loadHelpcard(id).observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
            @Override
            public void onChanged(Helpcard helpcard) {
                if (helpcard != null) {
                    textVictoryCondition.setText(helpcard.getVictoryCondition());
                    textEndGame.setText(helpcard.getEndGame());
                    textPreparation.setText(helpcard.getPreparation());
                    textPlayerTurn.setText(helpcard.getPlayerTurn());
                    textEffects.setText(helpcard.getEffects());

                    String titleUp = helpcard.getTitle();
                    String descriptionUp = helpcard.getDescription();
                    //устанавливает тайтл динамически
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(descriptionUp + " " + titleUp);

                }
            }
        });

    }

}
