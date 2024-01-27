package space.lopatkin.spb.helpboardgamecard.presentation.catalog;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

import java.util.ArrayList;
import java.util.List;

public class HelpcardAdapter extends RecyclerView.Adapter<HelpcardItem> {

    private List<Helpcard> list = new ArrayList<>();
    private CatalogFragment parent;

    public HelpcardAdapter(CatalogFragment parent) {
        this.parent = parent;
    }

    @NonNull
    @NotNull
    @Override
    public HelpcardItem onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemCardBinding binding = ItemCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new HelpcardItem(this.parent, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HelpcardItem holder, int position) {
        Helpcard item = list.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Helpcard> list) {
        this.list = list;
        updateList();
    }

    //uznaet poziciu dlia svaipa stirania
    public Helpcard getHelpcardAt(int position) {
        return list.get(position);
    }

    private void updateList() {
        notifyDataSetChanged();
    }

}
