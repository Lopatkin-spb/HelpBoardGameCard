package space.lopatkin.spb.helpboardgamecard.presentation.catalog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;


public class HelpcardItem extends AbstractRecyclerViewHolder<Helpcard> {

    private CatalogFragment parent;
    private ItemCardBinding binding;
    private Helpcard data;

    public HelpcardItem(@NonNull @NotNull Fragment parent, @NonNull @NotNull ViewBinding binding) {
        super(binding, parent);
        this.binding = (ItemCardBinding) binding;
        this.parent = (CatalogFragment) parent;

        onActionItem();
    }

    @Override
    protected void onActionItem() {
        itemView.setOnClickListener(view -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                parent.notifyToNavigateToHelpcard(data.getId());
            }
        });
    }

    @Override
    protected void setData(Helpcard data) {
        this.data = data;
        updateItem();
    }

    @Override
    protected void updateItem() {
        binding.textItemTitle.setText(data.getTitle());
        if (data.getDescription() != null && !data.getDescription().isEmpty()) {
            binding.textItemDescription.setText(data.getDescription());
        }
        binding.textItemPriority.setText(String.valueOf(data.getPriority()));

        binding.actionItemFavorites.setOnCheckedChangeListener(null);
        binding.actionItemFavorites.setChecked((data.isFavorites()));
        onActionFavorite();

        binding.actionItemLock.setOnCheckedChangeListener(null);
        binding.actionItemLock.setChecked((data.isLock()));
        onActionLock();
    }

    private void onActionFavorite() {
        binding.actionItemFavorites.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                data.setFavorites(isChecked);
                parent.notifyToUpdateFavorite(data);
            }
        });
    }

    private void onActionLock() {
        binding.actionItemLock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                data.setLock(isChecked);
                parent.notifyToUpdateLocking(data);
            }
        });
    }

}
