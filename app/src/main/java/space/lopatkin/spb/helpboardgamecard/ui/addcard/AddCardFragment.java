package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentAddcardBinding;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;

public class AddCardFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private AddCardViewModel viewModel;
    private FragmentAddcardBinding binding;
    private NavController navController;
    private InputConnection inputConnection;

    //todo: move to abstract fragment
    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddcardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setScreenTitle(R.string.title_addcard);
        //разрешает верхнее правое меню
        setHasOptionsMenu(true);
        setupViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(AddCardViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.addcard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onKeyboardDoneEvent(new KeyboardDoneEvent());
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNewHelpcard();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardDoneEvent(KeyboardDoneEvent event) {
        if (binding.editTextTitle.isFocused()) {
            binding.editTextTitle.clearFocus();
        }
        if (binding.editTextDescription.isFocused()) {
            binding.editTextDescription.clearFocus();
        }
        if (binding.editTextVictoryCondition.isFocused()) {
            binding.editTextVictoryCondition.clearFocus();
        }
        if (binding.editTextEndGame.isFocused()) {
            binding.editTextEndGame.clearFocus();
        }
        if (binding.editTextPreparation.isFocused()) {
            binding.editTextPreparation.clearFocus();
        }
        if (binding.editTextPlayerTurn.isFocused()) {
            binding.editTextPlayerTurn.clearFocus();
        }
        if (binding.editTextEffects.isFocused()) {
            binding.editTextEffects.clearFocus();
        }
        binding.keyboardAddcard.setVisibility(View.GONE);
    }

    private void setupViews() {
        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);

        binding.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextTitle.setTextIsSelectable(true);
        binding.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextDescription.setTextIsSelectable(true);
        binding.editTextVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextVictoryCondition.setTextIsSelectable(true);
        binding.editTextEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextEndGame.setTextIsSelectable(true);
        binding.editTextPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextPreparation.setTextIsSelectable(true);
        binding.editTextPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextPlayerTurn.setTextIsSelectable(true);
        binding.editTextEffects.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextEffects.setTextIsSelectable(true);
        onActionTitle();
        onActionDescription();
        onActionVictoryCondition();
        onActionEndGame();
        onActionPreparation();
        onActionPlayerTurn();
        onActionEffects();
    }

    private void saveNewHelpcard() {
        Helpcard newHelpcard = getData();
        if (newHelpcard.getTitle().isEmpty()) {
            showMessage(R.string.message_insert_title);
            return;
        }
        viewModel.saveNewHelpcard(newHelpcard);
        showMessage(R.string.message_helpcard_saved);
        //todo: move to onOptionsItemSel
        hideKeyboard();
        navigateToCatalog();
    }

    private Helpcard getData() {
        return new Helpcard(
                binding.editTextTitle.getText().toString(),
                binding.editTextVictoryCondition.getText().toString(),
                binding.editTextEndGame.getText().toString(),
                binding.editTextPreparation.getText().toString(),
                binding.editTextDescription.getText().toString(),
                binding.editTextPlayerTurn.getText().toString(),
                binding.editTextEffects.getText().toString(),
                false,
                false,
                binding.numberPickerPriority.getValue());
    }

    private void navigateToCatalog() {
        navController.navigate(AddCardFragmentDirections.actionNavAddcardToNavCatalog());
    }

    //todo: rework to delete focus to
    private void hideKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setScreenTitle(int toolbarTitle) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolbarTitle);
    }

    //todo: move to main act after add eventBus
    private void showMessage(int message) {
        Snackbar.make(binding.scrollAddcard, message, Snackbar.LENGTH_SHORT).show();
    }

    private void onActionTitle() {
        binding.editTextTitle.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS);
            return true;
        });
    }

    private void onActionDescription() {
        binding.editTextDescription.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS);
            return true;
        });
    }

    private void onActionVictoryCondition() {
        binding.editTextVictoryCondition.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS);
            return true;
        });
    }

    private void onActionEndGame() {
        binding.editTextEndGame.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS);
            return true;
        });
    }

    private void onActionPreparation() {
        binding.editTextPreparation.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS);
            return true;
        });
    }

    private void onActionPlayerTurn() {
        binding.editTextPlayerTurn.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS);
            return true;
        });
    }

    private void onActionEffects() {
        binding.editTextEffects.setOnTouchListener((view, motionEvent) -> {
            onActionTouch(view, KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS);
            return true;

        });
    }

    private void onActionTouch(View view, KeyboardType type) {
        view.requestFocus();
        if (inputConnection != null) {
            inputConnection.closeConnection();
        }
        inputConnection = view.onCreateInputConnection(new EditorInfo());
        binding.keyboardAddcard.setInputConnection(inputConnection);
        binding.keyboardAddcard.setKeyboardType(type);
        binding.keyboardAddcard.setVisibility(View.VISIBLE);
    }

}


