package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCatalogBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class CatalogFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CatalogViewModel
    private var binding: FragmentCatalogBinding? = null
    private val adapter: HelpcardAdapter by lazy { HelpcardAdapter(this) }
    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(CatalogViewModel::class.java)
        setupList()
        loadHelpcardsList()
        return view
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.app_bar_right_side_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all_unlock_helpcards -> {
                viewModel.deleteAllUnlockHelpcards()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        resultListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadHelpcardsList() {
        viewModel.loadHelpcardsList()
        viewModel.listHelpcards!!.observe(viewLifecycleOwner) { helpcards -> adapter.setList(helpcards) }
    }

    private fun setupList() {
        if (binding != null) {
            //инициализация ресайкл вью
            binding!!.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding!!.recyclerView.setHasFixedSize(true)
            //подключение адаптера к ресайкл вью
            binding!!.recyclerView.adapter = adapter
            onActionSwipe()
        }
    }

    private fun onActionSwipe() {
        if (binding != null) {
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val helpcard: Helpcard = adapter.getHelpcardAt(viewHolder.adapterPosition)
                    viewModel.delete(helpcard)
                }
            }).attachToRecyclerView(binding!!.recyclerView)
        }
    }

    fun notifyToUpdateFavorite(helpcard: Helpcard?) {
        viewModel.updateFavorite(helpcard)
    }

    fun notifyToUpdateLocking(helpcard: Helpcard?) {
        viewModel.updateLocking(helpcard)
    }

    fun notifyToNavigateToHelpcard(helpcardId: Int) {
        val action = CatalogFragmentDirections.actionNavCatalogToNavHelpcard().setId(helpcardId)
        navController.navigate(action)
    }

    private fun resultListener() {
        viewModel.message.observe(this) { result ->
            if (result != Message.POOL_EMPTY) {
                selectingTextFrom(result)
            }
        }
    }

    private fun selectingTextFrom(result: Message) {
        if (binding != null) {

            when (result) {
                Message.ACTION_ENDED_SUCCESS -> showMessage(
                    binding!!.recyclerView,
                    R.string.message_action_ended_success
                )

                Message.ACTION_ENDED_ERROR -> showMessage(binding!!.recyclerView, R.string.error_action_ended)
                Message.DELETE_ITEM_ACTION_ENDED_SUCCESS -> showMessage(
                    binding!!.recyclerView, R.string.message_helpcard_deleted
                )

                Message.DELETE_ITEM_ACTION_STOPPED -> showMessage(
                    binding!!.recyclerView,
                    R.string.message_helpcard_not_deleted
                )

                Message.FAVORITE_ITEM_ACTION_ENDED_SUCCESS -> showMessage(
                    binding!!.recyclerView, R.string.message_helpcard_favorite_updated
                )

                Message.FAVORITE_ITEM_ACTION_STOPPED -> showMessage(
                    binding!!.recyclerView, R.string.message_helpcard_favorite_not_updated
                )

                Message.LOCKING_ITEM_ACTION_ENDED_SUCCESS -> showMessage(
                    binding!!.recyclerView, R.string.message_helpcard_locking_updated
                )

                else -> {}
            }
        }
    }

}