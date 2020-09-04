package space.lopatkin.spb.helpboardgamecard.ui.catalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardAdapter;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardViewModel;

import java.util.List;


public class CatalogFragment extends Fragment {

    private RecyclerView recyclerView;


    private HelpcardViewModel helpcardViewModel;

    public CatalogFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_catalog, container, false);



        FloatingActionButton fab = root.findViewById(R.id.button_add_note);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


//                Intent intent = new Intent(getActivity(), AddFragment.class);
//                startActivityForResult(intent, ADD_NOTE_REQUEST);
//                getActivity().setResult(intent, ADD_NOTE_REQUEST);



                //    //v5safeargs
//                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_nav_home);


            }
        });




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


        return root;
    }



//    //v5safeargs
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (getArguments() != null) {
//
//            CatalogFragmentArgs args = CatalogFragmentArgs.fromBundle(getArguments());
//            String title = args.getMessageTitle();
//            String description = args.getMessageDescription();
//            int priority = args.getMessagePriority();
//
//            if (!title.equals("default") || !description.equals("default")) {
//
//                Helpcard helpcard = new Helpcard(title, description, priority);
//                helpcardViewModel.insert(helpcard);
//                Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();
//
//            } else {
//                Toast.makeText(getActivity(), "Note not saved", Toast.LENGTH_SHORT).show();
//
//
//            }
//        }
//
//    }



}