package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo

class BoardgameItem(
    parent: Fragment,
    binding: ViewBinding
) : AbstractRecyclerViewHolder<BoardgameInfo>(binding, parent) {

    private val parent: CatalogFragment
    private val binding: ItemCardBinding
    private var data: BoardgameInfo? = null

    init {
        this.binding = binding as ItemCardBinding
        this.parent = parent as CatalogFragment
        onActionItem()
    }

    override fun onActionItem() {
        itemView.setOnClickListener { view: View? ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                parent.notifyToNavigateToHelpcard(this.data?.boardgameId)
            }
        }
    }

    override fun setData(data: BoardgameInfo) {
        this.data = data
        if (this.data != null) {
            updateItem()
        }
    }

    override fun updateItem() {
        binding.textItemTitle.text = this.data?.boardgameName
        binding.textItemDescription.text = this.data?.boardgameDescription

        binding.textItemPriority.text = this.data?.boardgamePriority.toString()

        binding.actionItemFavorites.setOnCheckedChangeListener(null)
        binding.actionItemFavorites.isChecked = this.data!!.boardgameFavorite
        onActionFavorite()

        binding.actionItemLock.setOnCheckedChangeListener(null)
        binding.actionItemLock.isChecked = this.data!!.boardgameLock
        onActionLock()
    }

    private fun onActionFavorite() {
        binding.actionItemFavorites.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                this.data?.boardgameFavorite = isChecked
                parent.notifyToUpdateFavorite(this.data)
            }
        }
    }

    private fun onActionLock() {
        binding.actionItemLock.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            if (adapterPosition != RecyclerView.NO_POSITION) {
                this.data?.boardgameLock = isChecked
                parent.notifyToUpdateLocking(this.data)
            }
        }
    }

}
