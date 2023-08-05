package space.lopatkin.spb.helpboardgamecard.ui.catalog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.ViewModelFactory;

import javax.inject.Inject;
import java.util.List;

public class CatalogFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private CatalogViewModel viewModel;
    private RecyclerView recyclerView;
    private HelpcardAdapter adapter;
    private NavController navController;
    private String allCardDelete = "All unlock helpcards deleted";
    private String update = "Helpcard updated";
    private String lock = "Helpcard is lock";
    private String unlock = "Helpcard unlock";
    private String favorites = "Helpcard is favorites";
    private String unfavorites = "Helpcard unfavorites";
    private String delete = "Helpcard delete";
    private String notDelete = "Helpcard not delete";

    @Override
    public void onAttach(@NonNull Context context) {
        ((HelpBoardGameCardApplication) context.getApplicationContext()).getApplicationComponent().inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_catalog, container, false);
        //разрешение на создание верхнего меню
        setHasOptionsMenu(true);
//инициализация ресайкл вью
        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
//подключение адаптера к ресайкл вью
        adapter = new HelpcardAdapter();
        recyclerView.setAdapter(adapter);

        setSwipeDelete(adapter);
        setEditFavoritesItem(adapter);
        setEditLockItem(adapter);
        navigateToShowItem(adapter);
        navigateToEditItem(adapter);
        return root;
    }

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
                showSystemMessage(allCardDelete);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    showSystemMessage(delete);
                } else {
                    showSystemMessage(notDelete);
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setEditFavoritesItem(HelpcardAdapter adapter) {
        adapter.setOnItemCheckboxListenerTest(new HelpcardAdapter.OnItemCheckboxListenerTest() {
            @Override
            public void onItemCheckboxTest(Helpcard helpcard, boolean b) {
                helpcard.setFavorites(b);
                viewModel.update(helpcard);
                if (b) {
                    showSystemMessage(favorites);
                } else {
                    showSystemMessage(unfavorites);
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
                    showSystemMessage(lock);
                } else {
                    showSystemMessage(unlock);
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
                        CatalogFragmentDirections.actionNavCatalogToNavHelpcard()
                                .setId(id);
                navController.navigate(action);
            }
        });
    }

    private void navigateToEditItem(HelpcardAdapter adapter) {
        //редактирование одной карты: навигация и передача данных
        adapter.setOnItemEditClickListener(new HelpcardAdapter.OnItemEditClickListener() {
            @Override
            public void onItemEditClick(Helpcard helpcard) {
//                Helpcard helpcardToAddcard = getData(helpcard);
//                CatalogFragmentDirections.ActionNavCatalogToNavAddcard action =
//                        CatalogFragmentDirections.actionNavCatalogToNavAddcard()
//                                .setHelpcard(helpcardToAddcard);
//                navController.navigate(action);
            }
        });
    }

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


    private void showSystemMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}