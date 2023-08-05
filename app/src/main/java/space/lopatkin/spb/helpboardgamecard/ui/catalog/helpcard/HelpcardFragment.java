package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;

public class HelpcardFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private HelpcardViewModel viewModel;
    private TextView textVictoryCondition;
    private TextView textEndGame;
    private TextView textPreparation;
    private TextView textPlayerTurn;
    private TextView textEffects;
    private Helpcard details;
    private NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        //todo: create abstractFragment, move code to abstractFragment, extends from absFrag
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

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

        details = new Helpcard();

        setHasOptionsMenu(true);

        return root;
    }

    //safeargs + parcelable
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(HelpcardViewModel.class);

        if (getArguments() != null) {
            HelpcardFragmentArgs args = HelpcardFragmentArgs.fromBundle(getArguments());
            int id = args.getId();
            if (id > 0) {
                loadDetails(id);
            } else {
                setTitleDynamically();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card_edit:
                navigateToCardEdit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadDetails(int id) {
        viewModel.setId(id);
        viewModel.helpcardLiveData.observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
            @Override
            public void onChanged(Helpcard helpcard) {
                if (helpcard != null) {
                    details = helpcard;
                    textVictoryCondition.setText(helpcard.getVictoryCondition());
                    textEndGame.setText(helpcard.getEndGame());
                    textPreparation.setText(helpcard.getPreparation());
                    textPlayerTurn.setText(helpcard.getPlayerTurn());
                    textEffects.setText(helpcard.getEffects());

                    setTitleDynamically();
                }
            }
        });
    }

    private void setTitleDynamically() {
        if (details != null && details.getId() > 0) {
            String titleUp = details.getTitle();
            String descriptionUp = details.getDescription();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(descriptionUp + " " + titleUp);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_view_card);
        }
    }

    private void navigateToCardEdit() {
        viewModel.getCardId().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                HelpcardFragmentDirections.ActionNavHelpcardToNavCardEdit action =
                        HelpcardFragmentDirections.actionNavHelpcardToNavCardEdit().setId(integer);
                navController.navigate(action);
            }
        });
    }

}
