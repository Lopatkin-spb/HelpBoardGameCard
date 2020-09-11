package space.lopatkin.spb.helpboardgamecard.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.ui.HelpcardAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);



//инициализация ресайкл вью
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
//подключение адаптера к ресайкл вью
        final HelpcardAdapter adapter = new HelpcardAdapter();
        recyclerView.setAdapter(adapter);

        favoritesViewModel = ViewModelProviders.of(getActivity()).get(FavoritesViewModel.class);
        favoritesViewModel.getAllFavoritesHelpcards().observe(getActivity(), new Observer<List<Helpcard>>() {
            @Override
            public void onChanged(@Nullable List<Helpcard> helpcards) {
                adapter.setListHelpcards(helpcards);
            }
        });


        //редактирование чека
        adapter.setOnItemCheckboxListenerTest(new HelpcardAdapter.OnItemCheckboxListenerTest() {
            @Override
            public void onItemCheckboxTest(Helpcard helpcard, boolean b) {
                helpcard.setFavorites(b);
                favoritesViewModel.update(helpcard);
                if (b) {
                    Toast.makeText(getActivity(), "Helpcard is favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Helpcard unfavorites", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return root;
    }

}