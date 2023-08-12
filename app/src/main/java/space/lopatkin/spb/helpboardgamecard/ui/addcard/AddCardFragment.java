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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.addcard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNewHelpcard();
                return true;
            case R.id.action_keyboard_temp_state:
                keyboardManagerTempState();
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

    private void setupViews() {
        binding.editTextTitle.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        binding.editTextDescription.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.numberPickerPriority.setMinValue(1);
        binding.numberPickerPriority.setMaxValue(10);

        setupKeyboardTempState();
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

    //-----------------------------------------------------

    private void keyboardManagerTempState() {
        if (binding.keyboardAddcard.getVisibility() == View.VISIBLE) {
            binding.keyboardAddcard.setVisibility(View.GONE);
        } else {
            binding.keyboardAddcard.setVisibility(View.VISIBLE);
        }
    }

    private void setupKeyboardTempState() {
        binding.editTextVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.editTextVictoryCondition.setTextIsSelectable(true);

        InputConnection inputConnection = binding.editTextVictoryCondition.onCreateInputConnection(new EditorInfo());
        binding.keyboardAddcard.setInputConnection(inputConnection);
    }

}


