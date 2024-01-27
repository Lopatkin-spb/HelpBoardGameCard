package space.lopatkin.spb.helpboardgamecard.presentation.catalog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRecyclerViewHolder<E> extends RecyclerView.ViewHolder {

    public AbstractRecyclerViewHolder(@NonNull @NotNull ViewBinding binding, Fragment parent) {
        super(binding.getRoot());
    }

    protected abstract void onActionItem();

    protected abstract void setData(E data);

    protected abstract void updateItem();

}
