package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard;

import android.animation.LayoutTransition;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentHelpcardBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;

import javax.inject.Inject;


public class HelpcardFragment extends AbstractFragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private HelpcardViewModel viewModel;
    private FragmentHelpcardBinding binding;
    private NavController navController;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentHelpcardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(HelpcardViewModel.class);

        if (getArguments() != null) {
            HelpcardFragmentArgs args = HelpcardFragmentArgs.fromBundle(getArguments());
            int helpcardId = args.getId();
            if (helpcardId > 0) {
                loadHelpcard(helpcardId);
            }
        }

        onVictoryCondition();
        onEndGame();
        onPreparation();
        onPlayerTurn();
        onEffects();

        setAnimationSizeForExpandableViews();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu,
                                    @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_card_edit:
                navigateToCardEdit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getView());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadHelpcard(int helpcardId) {
        viewModel.loadHelpcard(helpcardId);
        viewModel.helpcard.observe(getViewLifecycleOwner(), new Observer<Helpcard>() {
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

    private void navigateToCardEdit() {
        viewModel.helpcardId.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer helpcardId) {
                HelpcardFragmentDirections.ActionNavHelpcardToNavCardEdit action =
                        HelpcardFragmentDirections.actionNavHelpcardToNavCardEdit().setId(helpcardId);
                navController.navigate(action);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onVictoryCondition() {
        binding.textVictoryCondition.setOnTouchListener((view, motionEvent) ->
                showLabel(view, motionEvent, binding.textVictoryCondition));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onEndGame() {
        binding.textEndGame.setOnTouchListener((view, motionEvent) ->
                showLabel(view, motionEvent, binding.textEndGame));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onPreparation() {
        binding.textPreparation.setOnTouchListener((view, motionEvent) ->
                showLabel(view, motionEvent, binding.textPreparation));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onPlayerTurn() {
        binding.textPlayerTurn.setOnTouchListener((view, motionEvent) ->
                showLabel(view, motionEvent, binding.textPlayerTurn));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onEffects() {
        binding.textEffects.setOnTouchListener((view, motionEvent) ->
                showLabel(view, motionEvent, binding.textEffects));
    }

    private boolean showLabel(View view, MotionEvent motionEvent, TextView textView) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // Binding create this. If binding create inside LabelPopupView then bug in draw background.
            space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding bindingLabel =
                    space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding.inflate(LayoutInflater.from(getContext()));

            LabelPopupView label = new LabelPopupView(getContext(), bindingLabel, getViewLifecycleOwner());
            label.show(view, motionEvent, textView);
        }
        return true;
    }

    private void setAnimationSizeForExpandableViews() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        binding.layoutExpandableHelpcard.setLayoutTransition(layoutTransition);
    }

}
