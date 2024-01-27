package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class AbstractRecyclerViewHolder<E>(
    binding: ViewBinding, parent: Fragment?
) : RecyclerView.ViewHolder(binding.root) {

    protected abstract fun onActionItem()
    abstract fun setData(data: E)
    protected abstract fun updateItem()

}
