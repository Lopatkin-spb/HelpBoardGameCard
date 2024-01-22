package space.lopatkin.spb.helpboardgamecard.presentation.catalog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCatalogBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.model.Message;
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory;

import javax.inject.Inject;
import java.util.List;

public class CatalogFragment extends AbstractFragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private CatalogViewModel viewModel;
    private FragmentCatalogBinding binding;
    private HelpcardAdapter adapter;
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
        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CatalogViewModel.class);

//инициализация ресайкл вью
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
//подключение адаптера к ресайкл вью
        adapter = new HelpcardAdapter();
        binding.recyclerView.setAdapter(adapter);

        onActionSwipe(adapter);
        onActionFavorite(adapter);
        onActionLock(adapter);
        navigateToHelpcard(adapter);

        loadHelpcardsList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu,
                                    @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_right_side_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_unlock_helpcards:
                viewModel.deleteAllUnlockHelpcards();
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
    public void onResume() {
        super.onResume();
        resultListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void showMessage(View parentView, int message) {
        super.showMessage(parentView, message);
    }

    private void loadHelpcardsList() {
        viewModel.loadHelpcardsList();
        viewModel.listHelpcards.observe(getViewLifecycleOwner(), new Observer<List<Helpcard>>() {
            @Override
            public void onChanged(@Nullable List<Helpcard> helpcards) {
                adapter.setListHelpcards(helpcards);
            }
        });
    }

    private void onActionSwipe(HelpcardAdapter adapter) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Helpcard helpcard = adapter.getHelpcardAt(viewHolder.getAdapterPosition());
                viewModel.delete(helpcard);
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    private void onActionFavorite(HelpcardAdapter adapter) {
        adapter.setOnItemCheckboxListenerTest(new HelpcardAdapter.OnItemCheckboxListenerTest() {
            @Override
            public void onItemCheckboxTest(Helpcard helpcard, boolean b) {
                helpcard.setFavorites(b);
                viewModel.updateFavorite(helpcard);
            }
        });
    }

    private void onActionLock(HelpcardAdapter adapter) {
        adapter.setOnItemCheckboxListenerLock(new HelpcardAdapter.OnItemCheckboxListenerLock() {
            @Override
            public void onItemCheckboxLock(Helpcard helpcard, boolean b) {
                helpcard.setLock(b);
                viewModel.updateLocking(helpcard);
            }
        });
    }

    private void navigateToHelpcard(HelpcardAdapter adapter) {
        adapter.setOnItemClickListener(new HelpcardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helpcard helpcard) {
                CatalogFragmentDirections.ActionNavCatalogToNavHelpcard action =
                        CatalogFragmentDirections.actionNavCatalogToNavHelpcard().setId(helpcard.getId());
                navController.navigate(action);
            }
        });
    }

    private void resultListener() {
        viewModel.message.observe(this, messageType -> {
            if (messageType != Message.POOL_EMPTY) {
                selectingTextFrom(messageType);
            }
        });
    }

    private void selectingTextFrom(Message type) {
        switch (type) {
            case ACTION_ENDED_SUCCESS:
                showMessage(binding.recyclerView, R.string.message_action_ended_success);
                break;
            case ACTION_ENDED_ERROR:
                showMessage(binding.recyclerView, R.string.error_action_ended);
                break;
            case DELETE_ITEM_ACTION_ENDED_SUCCESS:
                showMessage(binding.recyclerView, R.string.message_helpcard_deleted);
                break;
            case DELETE_ITEM_ACTION_STOPPED:
                showMessage(binding.recyclerView, R.string.message_helpcard_not_deleted);
                break;
            case FAVORITE_ITEM_ACTION_ENDED_SUCCESS:
                showMessage(binding.recyclerView, R.string.message_helpcard_favorite_updated);
                break;
            case FAVORITE_ITEM_ACTION_STOPPED:
                showMessage(binding.recyclerView, R.string.message_helpcard_favorite_not_updated);
                break;
            case LOCKING_ITEM_ACTION_ENDED_SUCCESS:
                showMessage(binding.recyclerView, R.string.message_helpcard_locking_updated);
                break;
        }
    }

}