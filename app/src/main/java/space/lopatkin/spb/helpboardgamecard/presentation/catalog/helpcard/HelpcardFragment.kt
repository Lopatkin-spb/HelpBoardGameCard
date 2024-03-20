package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentHelpcardBinding
import space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class HelpcardFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HelpcardViewModel
    private var _binding: FragmentHelpcardBinding? = null
    private val binding get() = _binding!!
    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }
    private val args: HelpcardFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelpcardBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(HelpcardViewModel::class.java)

        onVictoryCondition()
        onEndGame()
        onPreparation()
        onPlayerTurn()
        onEffects()
        setAnimationSizeForExpandableViews()
        return view
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.menu_card_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_card_edit -> {
                viewModel.notifyNavigateToEdit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHelpcard(args.boardgameId)
        uiStateListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onVictoryCondition() {
        binding.textVictoryCondition.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding.textVictoryCondition)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onEndGame() {
        binding.textEndGame.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding.textEndGame)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onPreparation() {
        binding.textPreparation.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding.textPreparation)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onPlayerTurn() {
        binding.textPlayerTurn.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding.textPlayerTurn)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onEffects() {
        binding.textEffects.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding.textEffects)
        }
    }

    private fun showLabel(view: View, motionEvent: MotionEvent, textView: TextView): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            // Binding create this. If binding create inside LabelPopupView then bug in draw background.
            val bindingLabel: ViewLabelPopupBinding = ViewLabelPopupBinding.inflate(LayoutInflater.from(context))
            val label: LabelPopupView = LabelPopupView(requireContext(), bindingLabel, viewLifecycleOwner)
            label.show(view, motionEvent, textView)
        }
        return true
    }

    private fun setAnimationSizeForExpandableViews() {
        val layoutTransition: LayoutTransition = LayoutTransition()
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layoutExpandableHelpcard.layoutTransition = layoutTransition
    }

    private fun uiStateListener() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (uiState.isLoading != binding.loadingIndicator.isRefreshing) {
                binding.loadingIndicator.isRefreshing = uiState.isLoading
            }
            uiState.helpcard?.let { helpcard ->
                binding.textViewTitle.text = helpcard.boardgameName
                binding.textVictoryCondition.text = helpcard.helpcardVictoryCondition
                binding.textEndGame.text = helpcard.helpcardEndGame
                binding.textPreparation.text = helpcard.helpcardPreparation
                binding.textPlayerTurn.text = helpcard.helpcardPlayerTurn
                binding.textEffects.text = helpcard.helpcardEffects
            }
            uiState.message?.let { message ->
                selectingTextFrom(message)
                viewModel.messageShownToUser()
            }
            if (uiState.isNavigate) {
                uiState.boardgameId?.let { id ->
                    val action = HelpcardFragmentDirections.actionNavHelpcardToNavCardEdit().setBoardgameId(id)
                    navController.navigate(action)
                    viewModel.userNavigated()
                }
            }
        }
    }

    private fun selectingTextFrom(result: Message) {
        when (result) {
            Message.ACTION_ENDED_ERROR -> showMessage(binding.scrollHelpcard, R.string.error_action_ended)
            else -> {}
        }
    }

}
