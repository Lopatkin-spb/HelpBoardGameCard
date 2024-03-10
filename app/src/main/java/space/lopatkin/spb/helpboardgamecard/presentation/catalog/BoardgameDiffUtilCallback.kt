package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.recyclerview.widget.DiffUtil
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo

class BoardgameDiffUtilCallback(
    private val oldList: List<BoardgameInfo>,
    private val newList: List<BoardgameInfo>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).boardgameId == newList.get(newItemPosition).boardgameId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
    }

}