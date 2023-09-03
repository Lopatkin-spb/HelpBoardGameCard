package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentHelpcardBinding;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;


public class HelpcardFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private HelpcardViewModel viewModel;
    private FragmentHelpcardBinding binding;
    private NavController navController;

    //todo: create abstractFragment, move code to abstractFragment, extends from absFrag
    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHelpcardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setScreenTitle(R.string.title_helpcard_details);
        setHasOptionsMenu(true);
        onVictoryCondition();
        onEndGame();
        onPreparation();
        onPlayerTurn();
        onEffects();
        return view;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadDetails(int id) {
        viewModel.setId(id);
        viewModel.helpcardLiveData.observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
            @Override
            public void onChanged(Helpcard helpcard) {
                if (helpcard != null) {
                    binding.textViewTitle.setText(helpcard.getTitle());
                    binding.textViewDescription.setText(helpcard.getDescription());
                    binding.textVictoryCondition.setText(helpcard.getVictoryCondition());
                    binding.textEndGame.setText(helpcard.getEndGame());
                    binding.textPreparation.setText(helpcard.getPreparation());
                    binding.textPlayerTurn.setText(helpcard.getPlayerTurn());
                    binding.textEffects.setText(helpcard.getEffects());
                }
            }
        });
    }

    private void setScreenTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
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

    @SuppressLint("ClickableViewAccessibility")
    private void onVictoryCondition() {
        binding.textVictoryCondition.setOnTouchListener((view, motionEvent) -> {
            snowLabel(view, motionEvent, binding.textVictoryCondition);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onEndGame() {
        binding.textEndGame.setOnTouchListener((view, motionEvent) -> {
            snowLabel(view, motionEvent, binding.textEndGame);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onPreparation() {
        binding.textPreparation.setOnTouchListener((view, motionEvent) -> {
            snowLabel(view, motionEvent, binding.textPreparation);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onPlayerTurn() {
        binding.textPlayerTurn.setOnTouchListener((view, motionEvent) -> {
            snowLabel(view, motionEvent, binding.textPlayerTurn);
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onEffects() {
        binding.textEffects.setOnTouchListener((view, motionEvent) -> {
            snowLabel(view, motionEvent, binding.textEffects);
            return false;
        });
    }

    private void snowLabel(View view, MotionEvent motionEvent, TextView textView) {
        // Binding create this. If binding create inside LabelPopupView then bug in draw background.
        space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding bindingLabel =
                space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding.inflate(LayoutInflater.from(getContext()));

        LabelPopupView label = new LabelPopupView(bindingLabel);
        label.show(view, motionEvent, textView);
    }

}
