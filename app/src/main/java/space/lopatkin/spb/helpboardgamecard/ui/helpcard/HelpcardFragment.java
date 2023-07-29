package space.lopatkin.spb.helpboardgamecard.ui.helpcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.ui.helpcard.HelpCardViewModel;

public class HelpCardFragment extends Fragment {

    private HelpCardViewModel helpCardViewModel;

    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpCardViewModel =
                ViewModelProviders.of(this).get(HelpCardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_helpcard, container, false);





//        //список
//        recyclerView = root.findViewById(R.id.list);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (this, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //разделитель
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));







        return root;
    }

    private void init() {

    }

}