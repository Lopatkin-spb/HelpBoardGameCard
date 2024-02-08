package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentCardEditBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.KeyboardDoneEvent
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import space.lopatkin.spb.keyboard.KeyboardCapabilities
import javax.inject.Inject

class CardEditFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CardEditViewModel
    private var binding: FragmentCardEditBinding? = null
    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }
    private val args: CardEditFragmentArgs by navArgs()
    private var inputConnection: InputConnection? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardEditBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory!!).get(CardEditViewModel::class.java)

        loadBoardgameFromDb(args.boardgameId)

        loadKeyboardType()
        setAnimationSizeForExpandableViews()
        return binding!!.root
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.menu_card_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_card_save -> {
                updateData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        resultListener()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onKeyboardDoneEvent(event: KeyboardDoneEvent) {
        if (binding != null) {
            clearFocus()
            binding!!.keyboardCardEdit.visibility = View.GONE
        }
    }

    private fun clearFocus() {
        if (binding!!.editTitle.isFocused) {
            binding!!.editTitle.clearFocus()
        }
        if (binding!!.editDescription.isFocused) {
            binding!!.editDescription.clearFocus()
        }
        if (binding!!.editVictoryCondition.isFocused) {
            binding!!.editVictoryCondition.clearFocus()
        }
        if (binding!!.editEndGame.isFocused) {
            binding!!.editEndGame.clearFocus()
        }
        if (binding!!.editPreparation.isFocused) {
            binding!!.editPreparation.clearFocus()
        }
        if (binding!!.editPlayerTurn.isFocused) {
            binding!!.editPlayerTurn.clearFocus()
        }
    }

    private fun loadKeyboardType() {
        viewModel.loadKeyboardType()
        viewModel.keyboardType.observe(viewLifecycleOwner) { keyboardType ->
            if (keyboardType == KeyboardType.CUSTOM) {
                setupViewsForCustomKeyboard()
            } else {
                setupViews()
            }
        }
    }

    private fun setupViews() {
        if (binding != null) {
            binding!!.editTitle.imeOptions = EditorInfo.IME_ACTION_DONE
            binding!!.editDescription.imeOptions = EditorInfo.IME_ACTION_DONE
            binding!!.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            binding!!.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        }
    }

    private fun setupViewsForCustomKeyboard() {
        if (binding != null) {
            binding!!.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTitle.showSoftInputOnFocus = false
            binding!!.editDescription.showSoftInputOnFocus = false
            binding!!.editVictoryCondition.showSoftInputOnFocus = false
            binding!!.editEndGame.showSoftInputOnFocus = false
            binding!!.editPreparation.showSoftInputOnFocus = false
            binding!!.editPlayerTurn.showSoftInputOnFocus = false
            onActionTitle()
            onActionDescription()
            onActionVictoryCondition()
            onActionEndGame()
            onActionPreparation()
            onActionPlayerTurn()
        }
    }

    private fun loadBoardgameFromDb(boardgameId: Long?) {
        viewModel.loadBoardgameRaw(boardgameId)
        viewModel.boardgameRaw?.observe(viewLifecycleOwner) { boardgameRaw ->

            if (binding != null && boardgameRaw != null) {
                binding!!.editTitle.setText(boardgameRaw.name)
                binding!!.editDescription.setText(boardgameRaw.description)
                binding!!.editVictoryCondition.setText(boardgameRaw.victoryCondition)
                binding!!.editEndGame.setText(boardgameRaw.endGame)
                binding!!.editPreparation.setText(boardgameRaw.preparation)
                binding!!.editPlayerTurn.setText(boardgameRaw.playerTurn)
            }
        }
    }

    private fun updateData() {
        viewModel.boardgameRaw?.observe(viewLifecycleOwner) { boardgameRawDb ->
            if (binding != null && boardgameRawDb != null) {
                viewModel.update(getEditedInstance(boardgameRawDb))
            }
        }

    }

    private fun getEditedInstance(boardgameRawDb: BoardgameRaw): BoardgameRaw {
        return BoardgameRaw(
            id = boardgameRawDb.id,
            name = binding!!.editTitle.text.toString(),
            description = binding!!.editDescription.text.toString(),
            victoryCondition = binding!!.editVictoryCondition.text.toString(),
            playerTurn = binding!!.editPlayerTurn.text.toString(),
            endGame = binding!!.editEndGame.text.toString(),
            effects = boardgameRawDb.effects,
            preparation = binding!!.editPreparation.text.toString(),
            priority = boardgameRawDb.priority,
            favorite = boardgameRawDb.favorite,
            lock = boardgameRawDb.lock,
            author = boardgameRawDb.author,
        )
    }

    private fun navigateToCatalog() {
        navController.navigate(CardEditFragmentDirections.actionNavCardEditToNavCatalog())
    }

    private fun onActionTitle() {
        binding!!.editTitle.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionDescription() {
        binding!!.editDescription.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionVictoryCondition() {
        binding!!.editVictoryCondition.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editVictoryCondition)
            }
        }
    }

    private fun onActionEndGame() {
        binding!!.editEndGame.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editEndGame)
            }
        }
    }

    private fun onActionPreparation() {
        binding!!.editPreparation.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editPreparation)
            }
        }
    }

    private fun onActionPlayerTurn() {
        binding!!.editPlayerTurn.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editPlayerTurn)
            }
        }
    }

    private fun enableCustomKeyboard(view: View, capabilities: KeyboardCapabilities) {
        if (inputConnection != null) {
            inputConnection!!.closeConnection()
        }
        inputConnection = view.onCreateInputConnection(EditorInfo())
        binding!!.keyboardCardEdit.setInputConnection(inputConnection)
        binding!!.keyboardCardEdit.setCapabilities(capabilities)
        binding!!.keyboardCardEdit.visibility = View.VISIBLE
    }

    private fun scrollTo(view: EditText) {
        binding!!.keyboardCardEdit.setHeightFragment(binding!!.layoutCardEdit.height)
        binding!!.keyboardCardEdit.setScrollView(binding!!.scrollCardEdit)
        binding!!.keyboardCardEdit.scrollEditTextToKeyboard(view)
    }

    private fun resultListener() {
        viewModel.message.observe(this) { result ->
            if (result != Message.POOL_EMPTY && binding != null) {
                selectingTextFrom(result)
            }
        }
    }

    private fun selectingTextFrom(result: Message) {
        when (result) {
            Message.ACTION_STOPPED -> showMessage(binding!!.scrollCardEdit, R.string.message_insert_title)
            Message.ACTION_ENDED_SUCCESS -> {
                showMessage(binding!!.scrollCardEdit, R.string.message_card_updated)
                navigateToCatalog()
            }

            Message.ACTION_ENDED_ERROR -> showMessage(binding!!.scrollCardEdit, R.string.error_action_ended)
            else -> {}
        }
    }

    private fun setAnimationSizeForExpandableViews() {
        val layoutTransition: LayoutTransition = LayoutTransition()
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding!!.layoutExpandableCardedit.layoutTransition = layoutTransition
    }

}