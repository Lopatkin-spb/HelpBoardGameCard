package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo

class BoardgameAdapter(
    private val parent: CatalogFragment
) : RecyclerView.Adapter<BoardgameItem>() {

    private var list: List<BoardgameInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardgameItem {
        val binding: ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardgameItem(this.parent, binding)
    }

    override fun onBindViewHolder(holder: BoardgameItem, position: Int) {
        val item: BoardgameInfo = list[position]
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<BoardgameInfo>) {
        this.list = list
        updateList()
    }

    //uznaet poziciu dlia svaipa stirania
    fun getBoardgameInfoAt(position: Int): BoardgameInfo {
        return list[position]
    }

    private fun updateList() {
        notifyDataSetChanged()
    }

}
