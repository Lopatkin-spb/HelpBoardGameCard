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
    private var _binding: FragmentCardEditBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentCardEditBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewModel = ViewModelProvider(this, viewModelFactory!!).get(CardEditViewModel::class.java)
        setupUserSettings()

        setAnimationSizeForExpandableViews()
        return view
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
                viewModel.getDataDetailsForUpdate()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        viewModel.loadBoardgameRaw(args.boardgameId)
        uiStateListener()
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUserSettings() {
        viewModel.loadKeyboardType()
        setupViewsForDeviceKeyboard()
        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            settings.keyboard?.let { type ->
                if (type == KeyboardType.CUSTOM) {
                    setupViewsForCustomKeyboard()
                } else {
                    setupViewsForDeviceKeyboard()
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onKeyboardDoneEvent(event: KeyboardDoneEvent) {
        clearFocus()
        binding.keyboardCardEdit.visibility = View.GONE
    }

    private fun clearFocus() {
        if (binding.editTitle.isFocused) {
            binding.editTitle.clearFocus()
        }
        if (binding.editDescription.isFocused) {
            binding.editDescription.clearFocus()
        }
        if (binding.editVictoryCondition.isFocused) {
            binding.editVictoryCondition.clearFocus()
        }
        if (binding.editEndGame.isFocused) {
            binding.editEndGame.clearFocus()
        }
        if (binding.editPreparation.isFocused) {
            binding.editPreparation.clearFocus()
        }
        if (binding.editPlayerTurn.isFocused) {
            binding.editPlayerTurn.clearFocus()
        }
    }

    private fun setupViewsForDeviceKeyboard() {
        binding.editTitle.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.editDescription.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
        binding.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
    }

    private fun setupViewsForCustomKeyboard() {
        binding.editTitle.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editDescription.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.editTitle.showSoftInputOnFocus = false
        binding.editDescription.showSoftInputOnFocus = false
        binding.editVictoryCondition.showSoftInputOnFocus = false
        binding.editEndGame.showSoftInputOnFocus = false
        binding.editPreparation.showSoftInputOnFocus = false
        binding.editPlayerTurn.showSoftInputOnFocus = false
        onActionTitle()
        onActionDescription()
        onActionVictoryCondition()
        onActionEndGame()
        onActionPreparation()
        onActionPlayerTurn()
    }

    private fun getEditedInstance(boardgameRawDb: BoardgameRaw): BoardgameRaw {
        return BoardgameRaw(
            id = boardgameRawDb.id,
            name = binding.editTitle.text.toString(),
            description = binding.editDescription.text.toString(),
            victoryCondition = binding.editVictoryCondition.text.toString(),
            playerTurn = binding.editPlayerTurn.text.toString(),
            endGame = binding.editEndGame.text.toString(),
            effects = boardgameRawDb.effects,
            preparation = binding.editPreparation.text.toString(),
            priority = boardgameRawDb.priority,
            favorite = boardgameRawDb.favorite,
            lock = boardgameRawDb.lock,
            author = boardgameRawDb.author,
        )
    }

    private fun navigateToCatalog() {
        navController.navigate(R.id.nav_catalog)
    }

    private fun onActionTitle() {
        binding.editTitle.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionDescription() {
        binding.editDescription.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionVictoryCondition() {
        binding.editVictoryCondition.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding.editVictoryCondition)
            }
        }
    }

    private fun onActionEndGame() {
        binding.editEndGame.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding.editEndGame)
            }
        }
    }

    private fun onActionPreparation() {
        binding.editPreparation.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding.editPreparation)
            }
        }
    }

    private fun onActionPlayerTurn() {
        binding.editPlayerTurn.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding.editPlayerTurn)
            }
        }
    }

    private fun enableCustomKeyboard(view: View, capabilities: KeyboardCapabilities) {
        if (inputConnection != null) {
            inputConnection!!.closeConnection()
        }
        inputConnection = view.onCreateInputConnection(EditorInfo())
        binding.keyboardCardEdit.setInputConnection(inputConnection)
        binding.keyboardCardEdit.setCapabilities(capabilities)
        binding.keyboardCardEdit.visibility = View.VISIBLE
    }

    private fun scrollTo(view: EditText) {
        binding.keyboardCardEdit.setHeightFragment(binding.layoutCardEdit.height)
        binding.keyboardCardEdit.setScrollView(binding.scrollCardEdit)
        binding.keyboardCardEdit.scrollEditTextToKeyboard(view)
    }

    private fun uiStateListener() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (uiState.isLoading != binding.loadingIndicator.isRefreshing) {
                binding.loadingIndicator.isRefreshing = uiState.isLoading
            }
            if (uiState.isUpdateStart) {
                uiState.boardgameRaw?.let { dataFromDb ->
                    viewModel.update(getEditedInstance(dataFromDb))
                    viewModel.eventPassedToFragment()
                }
            } else {
                uiState.boardgameRaw?.let { boardgameRaw ->
                    binding.editTitle.setText(boardgameRaw.name)
                    binding.editDescription.setText(boardgameRaw.description)
                    binding.editVictoryCondition.setText(boardgameRaw.victoryCondition)
                    binding.editEndGame.setText(boardgameRaw.endGame)
                    binding.editPreparation.setText(boardgameRaw.preparation)
                    binding.editPlayerTurn.setText(boardgameRaw.playerTurn)
                }
            }
            uiState.message?.let { message ->
                selectingTextFrom(message)
                viewModel.messageShownToUser()
            }
            if (uiState.isUpdateCompleted) {
                navigateToCatalog()
            }
        }
    }

    private fun selectingTextFrom(result: Message) {
        when (result) {
            Message.ACTION_STOPPED -> showMessage(binding.scrollCardEdit, R.string.message_insert_title)
            Message.ACTION_ENDED_SUCCESS -> showMessage(binding.scrollCardEdit, R.string.message_card_updated)
            Message.ACTION_ENDED_ERROR -> showMessage(binding.scrollCardEdit, R.string.error_action_ended)
            else -> {}
        }
    }

    private fun setAnimationSizeForExpandableViews() {
        val layoutTransition: LayoutTransition = LayoutTransition()
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layoutExpandableCardedit.layoutTransition = layoutTransition
    }

}