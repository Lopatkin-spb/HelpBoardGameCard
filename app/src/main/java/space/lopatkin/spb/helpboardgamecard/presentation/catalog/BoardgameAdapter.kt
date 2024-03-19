package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo

class BoardgameAdapter(
    private val parent: CatalogFragment
) : RecyclerView.Adapter<BoardgameItem>() {

    private var currentList: List<BoardgameInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardgameItem {
        val binding: ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardgameItem(this.parent, binding)
    }

    override fun onBindViewHolder(holder: BoardgameItem, position: Int) {
        val itemData: BoardgameInfo = currentList[position]
        holder.setData(itemData)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setList(newList: List<BoardgameInfo>) {
        redrawList(newList)
        currentList = newList
    }

    //uznaet poziciu dlia svaipa stirania
    fun getBoardgameInfoAt(position: Int): BoardgameInfo {
        return currentList[position]
    }

    fun deleteItemAt(position: Int) {
        val newList: MutableList<BoardgameInfo> = currentList.toMutableList()
        newList.removeAt(position)
        setList(newList.toList())
    }

    private fun redrawList() {
        notifyDataSetChanged()
    }

    private fun redrawList(newList: List<BoardgameInfo>) {
        val diffResult = DiffUtil.calculateDiff(BoardgameDiffUtilCallback(currentList, newList))
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItemFavorite(itemData: BoardgameInfo?) {
        if (itemData != null) {
            val newList: MutableList<BoardgameInfo> = currentList.toMutableList()

            for (index in newList.indices) {
                if (newList[index].boardgameId == itemData.boardgameId) {
                    newList[index].boardgameFavorite = itemData.boardgameFavorite
                    break
                }
            }
            currentList = newList
        }
    }

    fun updateItemLocking(itemData: BoardgameInfo?) {
        if (itemData != null) {
            val newList: MutableList<BoardgameInfo> = currentList.toMutableList()

            for (index in newList.indices) {
                if (newList[index].boardgameId == itemData.boardgameId) {
                    newList[index].boardgameLock = itemData.boardgameLock
                    break
                }
            }
            currentList = newList
        }
    }

}
