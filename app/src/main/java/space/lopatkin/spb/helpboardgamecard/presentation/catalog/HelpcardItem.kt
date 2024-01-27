package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard

class HelpcardItem(
    parent: Fragment,
    binding: ViewBinding
) : AbstractRecyclerViewHolder<Helpcard>(binding, parent) {

    private val parent: CatalogFragment
    private val binding: ItemCardBinding
    private var data: Helpcard? = null

    init {
        this.binding = binding as ItemCardBinding
        this.parent = parent as CatalogFragment
        onActionItem()
    }

    override fun onActionItem() {
        itemView.setOnClickListener { view: View? ->
            if (adapterPosition != RecyclerView.NO_POSITION && data != null) {
                parent.notifyToNavigateToHelpcard(data!!.id)
            }
        }
    }

    override fun setData(data: Helpcard) {
        this.data = data
        if (this.data != null) {
            updateItem()
        }
    }

    override fun updateItem() {
        binding.textItemTitle.text = data!!.title
        if (data!!.description != null && !data!!.description.isNullOrEmpty()) {
            binding.textItemDescription.text = data!!.description
        }
        binding.textItemPriority.text = data!!.priority.toString()

        binding.actionItemFavorites.setOnCheckedChangeListener(null)
        binding.actionItemFavorites.isChecked = data!!.isFavorites
        onActionFavorite()

        binding.actionItemLock.setOnCheckedChangeListener(null)
        binding.actionItemLock.isChecked = data!!.isLock
        onActionLock()
    }

    private fun onActionFavorite() {
        binding.actionItemFavorites.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                data!!.isFavorites = isChecked
                parent.notifyToUpdateFavorite(data)
            }
        }
    }

    private fun onActionLock() {
        binding.actionItemLock.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                data!!.isLock = isChecked
                parent.notifyToUpdateLocking(data)
            }
        }
    }

}
