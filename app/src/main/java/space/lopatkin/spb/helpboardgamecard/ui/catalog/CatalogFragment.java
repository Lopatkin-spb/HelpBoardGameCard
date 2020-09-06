package space.lopatkin.spb.helpboardgamecard.ui.catalog;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardAdapter;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardViewModel;

import java.util.List;


public class CatalogFragment extends Fragment {

    private RecyclerView recyclerView;


    private HelpcardViewModel helpcardViewModel;


    NavController navController;


    public CatalogFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_catalog, container, false);

        //разрешение на создание верхнего меню
        setHasOptionsMenu(true);


//        FloatingActionButton fab = root.findViewById(R.id.button_add_note);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//
////                Intent intent = new Intent(getActivity(), AddFragment.class);
////                startActivityForResult(intent, ADD_NOTE_REQUEST);
////                getActivity().setResult(intent, ADD_NOTE_REQUEST);
//
//
//
//                //    //v5safeargs
////                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_nav_home);
//
//
//            }
//        });


//        //список
//        recyclerView = root.findViewById(R.id.list);
//
//        LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        //LinearLayoutManager linearLayoutManager =
//        //     new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //разделитель
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//
//
//        final HelpcardAdapter adapter = new HelpcardAdapter();
//        recyclerView.setAdapter(adapter);
//
//
//        //позволяет получить вьюмодел для нашей активити
//        HelpCardViewModel mainViewModel = ViewModelProviders.of(this).get(HelpCardViewModel.class);
//        mainViewModel.getHelpCardLiveData().observe(this, new Observer<List<Helpcard>>() {
//            @Override
//            public void onChanged(List<Helpcard> helpcards) {
//                adapter.setHelpcards(helpcards);
//            }
//        });
//
//
//        adapter.setOnItemClickListener(new HelpcardAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Helpcard helpCard) {
//                Intent intent = new Intent(getContext(), AddCardFragment.class);
//
//                intent.putExtra(AddCardFragment.EXTRA_HELPCARD, helpCard.getDescription());
//
//                startActivityForResult(intent, 1);
//            }
//        });
//
////        recyclerView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                //eeeeeee();
////
////                Navigation.findNavController(view).navigate(R.id.action_nav_helpcard_to_nav_newcard);
////            }
////        });


        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final HelpcardAdapter adapter = new HelpcardAdapter();
        recyclerView.setAdapter(adapter);


        helpcardViewModel = ViewModelProviders.of(getActivity()).get(HelpcardViewModel.class);
        helpcardViewModel.getAllHelpcards().observe(getActivity(), new Observer<List<Helpcard>>() {
            @Override
            public void onChanged(@Nullable List<Helpcard> helpcards) {
                adapter.setHelpcards(helpcards);

            }
        });


        //swipe delete left & right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                helpcardViewModel.delete(adapter.getHelpcardAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Helpcard delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //редактирование: навигация и передача данных
        adapter.setOnItemClickListener(new HelpcardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helpcard helpcard) {
                //переход на новый фрагмент редактирования
                // и забор и передача данных в редактирование

                int editId = helpcard.getId();
                String editTitle = helpcard.getTitle();
                String editDescription = helpcard.getDescription();
                int editPriority = helpcard.getPriority();

                CatalogFragmentDirections.ActionNavHelpcardToNavNewcard action =
                        CatalogFragmentDirections.actionNavHelpcardToNavNewcard();
                action.setMessageEditId(editId);
                action.setMessageEditTitle(editTitle);
                action.setMessageEditDescription(editDescription);
                action.setMessageEditPriority(editPriority);
                navController.navigate(action);


            }
        });


        return root;
    }


    //v5safeargs: заюор данных из АДД и запись в КАТАЛОГ
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());

        if (getArguments() != null) {

            CatalogFragmentArgs args = CatalogFragmentArgs.fromBundle(getArguments());
            String title = args.getMessageNewTitle();
            String description = args.getMessageNewDescription();
            int priority = args.getMessageNewPriority();
            int id = args.getId();

            if (!title.equals("default") && description.equals("default")
                    || title.equals("default") && !description.equals("default")) {
                Toast.makeText(getActivity(), "Helpcard not saved", Toast.LENGTH_SHORT).show();

            } else if (!title.equals("default") && !description.equals("default") && id == -1) {
                Helpcard helpcard = new Helpcard(title, description, false, priority);
                helpcardViewModel.insert(helpcard);
                Toast.makeText(getActivity(), "Helpcard saved", Toast.LENGTH_SHORT).show();

//            } else if (id == -1) {
//                //сли реквест на корректировку записи но айди нет
//                Toast.makeText(getActivity(), "Helpcard can't be updated", Toast.LENGTH_SHORT).show();
//                return;

                //и потом запись

            } else if (!title.equals("default") && !description.equals("default") && id != -1) {
                Helpcard helpcard = new Helpcard(title, description, false, priority);
                helpcard.setId(id);
                helpcardViewModel.update(helpcard);
                Toast.makeText(getActivity(), "Helpcard updated", Toast.LENGTH_SHORT).show();


            } else {
                //никаких знаков не вылезает - к примеру если ето просто переключение между фрагментами
            }
        }

    }


    //создание меню верхнего справа
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_right_side_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //что происходит в меню на нажатие определенных иконок
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_helpcards:
                helpcardViewModel.deleteAllHelpcards();
                Toast.makeText(getActivity(), "All helpcards deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}