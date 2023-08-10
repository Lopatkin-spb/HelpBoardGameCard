package space.lopatkin.spb.helpboardgamecard.ui.catalog;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCatalogBinding;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;
import java.util.List;

public class CatalogFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private CatalogViewModel viewModel;
    private FragmentCatalogBinding binding;
    private HelpcardAdapter adapter;
    private NavController navController;

    //todo: extract strings to res
    private String allCardDelete = "All unlock helpcards deleted";
    private String update = "Helpcard updated";
    private String lock = "Helpcard is lock";
    private String unlock = "Helpcard unlock";
    private String favorites = "Helpcard is favorites";
    private String unfavorites = "Helpcard unfavorites";
    private String delete = "Helpcard delete";
    private String notDelete = "Helpcard not delete";

    //todo: move to absFrag
    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCatalogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //разрешение на создание верхнего меню
        setHasOptionsMenu(true);
//инициализация ресайкл вью
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
//подключение адаптера к ресайкл вью
        adapter = new HelpcardAdapter();
        binding.recyclerView.setAdapter(adapter);

        setSwipeDelete(adapter);
        setEditFavoritesItem(adapter);
        setEditLockItem(adapter);
        navigateToShowItem(adapter);
        return view;
    }

    //todo: upgrade dependencies in gradle (add fragment last vers)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(CatalogViewModel.class);
        viewModel.getListHelpcards().observe(getActivity(), new Observer<List<Helpcard>>() {
            @Override
            public void onChanged(@Nullable List<Helpcard> helpcards) {
                adapter.setListHelpcards(helpcards);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_right_side_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_unlock_helpcards:
                viewModel.deleteAllUnlockHelpcards();
                showMessage(allCardDelete);
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

    private void setSwipeDelete(HelpcardAdapter adapter) {
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
                boolean lock = adapter.getHelpcardAt(viewHolder.getAdapterPosition()).isLock();
                if (!lock) {
                    Helpcard helpcard = adapter.getHelpcardAt(viewHolder.getAdapterPosition());
                    viewModel.delete(helpcard);
                    showMessage(delete);
                } else {
                    showMessage(notDelete);
                }
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    private void setEditFavoritesItem(HelpcardAdapter adapter) {
        adapter.setOnItemCheckboxListenerTest(new HelpcardAdapter.OnItemCheckboxListenerTest() {
            @Override
            public void onItemCheckboxTest(Helpcard helpcard, boolean b) {
                helpcard.setFavorites(b);
                viewModel.update(helpcard);
                if (b) {
                    showMessage(favorites);
                } else {
                    showMessage(unfavorites);
                }
            }
        });
    }

    private void setEditLockItem(HelpcardAdapter adapter) {
        adapter.setOnItemCheckboxListenerLock(new HelpcardAdapter.OnItemCheckboxListenerLock() {
            @Override
            public void onItemCheckboxLock(Helpcard helpcard, boolean b) {
                helpcard.setLock(b);
                viewModel.update(helpcard);
                if (b) {
                    showMessage(lock);
                } else {
                    showMessage(unlock);
                }
            }
        });
    }

    private void navigateToShowItem(HelpcardAdapter adapter) {
        adapter.setOnItemClickListener(new HelpcardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helpcard helpcard) {
                Helpcard helpcardToViewcard = getData(helpcard);
                Integer id = helpcardToViewcard.getId();
                //отправка данных (parcelable)
                CatalogFragmentDirections.ActionNavCatalogToNavHelpcard action =
                        CatalogFragmentDirections.actionNavCatalogToNavHelpcard().setId(id);
                navController.navigate(action);
            }
        });
    }

    //todo: redundant method - delete
    private Helpcard getData(Helpcard data) {
        int editId = data.getId();
        String editTitle = data.getTitle();
        String editVictoryCondition = data.getVictoryCondition();
        String editEndGame = data.getEndGame();
        String editPreparation = data.getPreparation();
        String editDescription = data.getDescription();
        String editPlayerTurn = data.getPlayerTurn();
        String editEffects = data.getEffects();
        boolean editFavorites = data.isFavorites();
        boolean editLock = data.isLock();
        int editPriority = data.getPriority();
        Helpcard h = new Helpcard(editId, editTitle,
                editVictoryCondition, editEndGame, editPreparation,
                editDescription, editPlayerTurn, editEffects,
                editFavorites, editLock, editPriority);
        return h;
    }

    //todo: move method to activity and send message from EventBus
    private void showMessage(String message) {
        Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

}