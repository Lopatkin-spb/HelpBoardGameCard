package space.lopatkin.spb.helpboardgamecard.ui.helpCard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardAdapter;
import space.lopatkin.spb.helpboardgamecard.ui.addCard.AddCardFragment;

import java.util.List;


public class HelpCardFragment extends Fragment {

    private RecyclerView recyclerView;


    @SuppressLint("FragmentLiveDataObserve")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_helpcard, container, false);


        //список
        recyclerView = root.findViewById(R.id.list);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        //LinearLayoutManager linearLayoutManager =
        //     new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //разделитель
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        final HelpcardAdapter adapter = new HelpcardAdapter();
        recyclerView.setAdapter(adapter);


        //позволяет получить вьюмодел для нашей активити
        HelpCardViewModel mainViewModel = ViewModelProviders.of(this).get(HelpCardViewModel.class);
        mainViewModel.getHelpCardLiveData().observe(this, new Observer<List<Helpcard>>() {
            @Override
            public void onChanged(List<Helpcard> helpcards) {
                adapter.setHelpcards(helpcards);
            }
        });


        adapter.setOnItemClickListener(new HelpcardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Helpcard helpCard) {
                Intent intent = new Intent(getContext(), AddCardFragment.class);

                intent.putExtra(AddCardFragment.EXTRA_HELPCARD, helpCard.getDescription());

                startActivityForResult(intent, 1);
            }
        });

//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //eeeeeee();
//
//                Navigation.findNavController(view).navigate(R.id.action_nav_helpcard_to_nav_newcard);
//            }
//        });


        return root;
    }


}