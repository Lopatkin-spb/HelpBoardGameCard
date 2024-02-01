package space.lopatkin.spb.helpboardgamecard.presentation.addcard

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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentAddcardBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.KeyboardDoneEvent
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import space.lopatkin.spb.keyboard.KeyboardCapabilities
import javax.inject.Inject

class AddCardFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AddCardViewModel
    private var binding: FragmentAddcardBinding? = null
    private var inputConnection: InputConnection? = null
    private val navController: NavController by lazy { Navigation.findNavController(requireView()) }

    override fun onAttach(context: Context) {
        (context.applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddcardBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, viewModelFactory!!).get(AddCardViewModel::class.java)
        loadKeyboardType()

        return binding!!.root
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.addcard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveNewHelpcard(getData())
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
            binding!!.keyboardAddcard.visibility = View.GONE
        }
    }

    private fun clearFocus() {
        if (binding!!.editTextTitle.isFocused) {
            binding!!.editTextTitle.clearFocus()
        }
        if (binding!!.editTextDescription.isFocused) {
            binding!!.editTextDescription.clearFocus()
        }
        if (binding!!.editTextVictoryCondition.isFocused) {
            binding!!.editTextVictoryCondition.clearFocus()
        }
        if (binding!!.editTextEndGame.isFocused) {
            binding!!.editTextEndGame.clearFocus()
        }
        if (binding!!.editTextPreparation.isFocused) {
            binding!!.editTextPreparation.clearFocus()
        }
        if (binding!!.editTextPlayerTurn.isFocused) {
            binding!!.editTextPlayerTurn.clearFocus()
        }
        if (binding!!.editTextEffects.isFocused) {
            binding!!.editTextEffects.clearFocus()
        }
    }

    private fun loadKeyboardType() {
        viewModel.loadKeyboardType()
        viewModel.keyboardType.observe(viewLifecycleOwner) { keyboardType: KeyboardType ->
            if (keyboardType == KeyboardType.CUSTOM) {
                setupViewsForCustomKeyboard()
            } else {
                setupViews()
            }
        }
    }

    private fun setupViews() {
        if (binding != null) {
            binding!!.editTextTitle.imeOptions = EditorInfo.IME_ACTION_DONE
            binding!!.editTextDescription.imeOptions = EditorInfo.IME_ACTION_DONE
            binding!!.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            binding!!.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            binding!!.numberPickerPriority.minValue = 1
            binding!!.numberPickerPriority.maxValue = 10
        }
    }

    private fun setupViewsForCustomKeyboard() {
        if (binding != null) {
            binding!!.editTextTitle.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextDescription.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextVictoryCondition.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextEndGame.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextPreparation.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextPlayerTurn.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextEffects.setRawInputType(InputType.TYPE_CLASS_TEXT)
            binding!!.editTextTitle.showSoftInputOnFocus = false
            binding!!.editTextDescription.showSoftInputOnFocus = false
            binding!!.editTextVictoryCondition.showSoftInputOnFocus = false
            binding!!.editTextEndGame.showSoftInputOnFocus = false
            binding!!.editTextPreparation.showSoftInputOnFocus = false
            binding!!.editTextPlayerTurn.showSoftInputOnFocus = false
            binding!!.editTextEffects.showSoftInputOnFocus = false
            onActionTitle()
            onActionDescription()
            onActionVictoryCondition()
            onActionEndGame()
            onActionPreparation()
            onActionPlayerTurn()
            onActionEffects()
        }
    }

    private fun getData(): Helpcard {
        return Helpcard(
            id = 0,
            title = binding!!.editTextTitle.text.toString(),
            victoryCondition = binding!!.editTextVictoryCondition.text.toString(),
            endGame = binding!!.editTextEndGame.text.toString(),
            preparation = binding!!.editTextPreparation.text.toString(),
            description = binding!!.editTextDescription.text.toString(),
            playerTurn = binding!!.editTextPlayerTurn.text.toString(),
            effects = binding!!.editTextEffects.text.toString(),
            isFavorites = false,
            isLock = false,
            priority = binding!!.numberPickerPriority.value
        )
    }

    private fun navigateToCatalog() {
        navController.navigate(AddCardFragmentDirections.actionNavAddcardToNavCatalog())
    }

    private fun onActionTitle() {
        binding!!.editTextTitle.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionDescription() {
        binding!!.editTextDescription.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS)
            }
        }
    }

    private fun onActionVictoryCondition() {
        binding!!.editTextVictoryCondition.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editTextVictoryCondition)
            }
        }
    }

    private fun onActionEndGame() {
        binding!!.editTextEndGame.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editTextEndGame)
            }
        }
    }

    private fun onActionPreparation() {
        binding!!.editTextPreparation.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editTextPreparation)
            }
        }
    }

    private fun onActionPlayerTurn() {
        binding!!.editTextPlayerTurn.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editTextPlayerTurn)
            }
        }
    }

    private fun onActionEffects() {
        binding!!.editTextEffects.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                enableCustomKeyboard(view, KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS)
                scrollTo(binding!!.editTextEffects)
            }
        }
    }

    private fun enableCustomKeyboard(view: View, capabilities: KeyboardCapabilities) {
        if (inputConnection != null) {
            inputConnection!!.closeConnection()
        }
        inputConnection = view.onCreateInputConnection(EditorInfo())
        binding!!.keyboardAddcard.setInputConnection(inputConnection)
        binding!!.keyboardAddcard.setCapabilities(capabilities)
        binding!!.keyboardAddcard.visibility = View.VISIBLE
    }

    private fun scrollTo(view: EditText) {
        binding!!.keyboardAddcard.setHeightFragment(binding!!.containerAddcard.height)
        binding!!.keyboardAddcard.setScrollView(binding!!.scrollAddcard)
        binding!!.keyboardAddcard.scrollEditTextToKeyboard(view)
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
                Message.ACTION_STOPPED -> showMessage(binding!!.scrollAddcard, R.string.message_insert_title)
                Message.ACTION_ENDED_SUCCESS -> {
                    showMessage(binding!!.scrollAddcard, R.string.message_helpcard_saved)
                    navigateToCatalog()
                }

                Message.ACTION_ENDED_ERROR -> showMessage(binding!!.scrollAddcard, R.string.error_action_ended)
                else -> {}
            }
        }
    }

}
