package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.lopatkin.spb.helpboardgamecard.databinding.ItemCardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard

class HelpcardAdapter(
    private val parent: CatalogFragment
) : RecyclerView.Adapter<HelpcardItem>() {

    private var list: List<Helpcard> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpcardItem {
        val binding: ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HelpcardItem(this.parent, binding)
    }

    override fun onBindViewHolder(holder: HelpcardItem, position: Int) {
        val item: Helpcard = list[position]
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Helpcard>) {
        this.list = list
        updateList()
    }

    //uznaet poziciu dlia svaipa stirania
    fun getHelpcardAt(position: Int): Helpcard {
        return list[position]
    }

    private fun updateList() {
        notifyDataSetChanged()
    }

}
