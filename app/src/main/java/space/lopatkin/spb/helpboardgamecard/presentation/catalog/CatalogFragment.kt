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
import org.greenrobot.eventbus.EventBus
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCatalogBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class CatalogFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CatalogViewModel
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    private val adapter: BoardgameAdapter by lazy { BoardgameAdapter(this) }
    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(CatalogViewModel::class.java)
        setupList()
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
                viewModel.deleteAllUnlockBoardgames()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(viewModel)
        viewModel.loadListBoardgamesInfo()
        uiStateListener()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(viewModel)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun uiStateListener() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (uiState.isLoading != binding.loadingIndicator.isRefreshing) {
                binding.loadingIndicator.isRefreshing = uiState.isLoading
            }
            if (!uiState.isLoading) {
                adapter.setList(uiState.list)
            }
            uiState.message?.let { message ->
                selectingTextFrom(message)
                viewModel.messageShownToUser()
            }
        }
    }

    private fun setupList() {
        //инициализация ресайкл вью
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        //подключение адаптера к ресайкл вью
        binding.recyclerView.adapter = adapter
        onActionSwipe()
    }

    private fun onActionSwipe() {
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
                val position = viewHolder.adapterPosition
                val item: BoardgameInfo = adapter.getBoardgameInfoAt(position)
                adapter.deleteItemAt(position)
                viewModel.delete(item)
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    fun notifyToUpdateFavorite(boardgameInfo: BoardgameInfo?) {
        adapter.updateItemFavorite(boardgameInfo)
        viewModel.updateFavorite(boardgameInfo)
    }

    fun notifyToUpdateLocking(boardgameInfo: BoardgameInfo?) {
        adapter.updateItemLocking(boardgameInfo)
        viewModel.updateLocking(boardgameInfo)
    }

    fun notifyToNavigateToHelpcard(boardgameId: Long?) {
        if (boardgameId != null) {
            val action = CatalogFragmentDirections.actionNavCatalogToNavHelpcard().setBoardgameId(boardgameId)
            navController.navigate(action)
        }
    }

    private fun selectingTextFrom(result: Message) {
        when (result) {
            Message.ACTION_ENDED_SUCCESS -> showMessage(binding.recyclerView, R.string.message_action_ended_success)
            Message.ACTION_ENDED_ERROR -> showMessage(binding.recyclerView, R.string.error_action_ended)
            else -> {}
        }
    }

}