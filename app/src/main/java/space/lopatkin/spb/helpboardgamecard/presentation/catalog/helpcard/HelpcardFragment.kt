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
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class HelpcardFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HelpcardViewModel
    private var binding: FragmentHelpcardBinding? = null
    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }
    private val args: HelpcardFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        (context.applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpcardBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(HelpcardViewModel::class.java)
        val helpcardId: Int = args.id
        if (helpcardId > 0) {
            loadHelpcard(helpcardId)
        }

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
                navigateToCardEdit()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadHelpcard(helpcardId: Int) {
        viewModel.loadHelpcard(helpcardId)
        viewModel.helpcard?.observe(viewLifecycleOwner) { helpcard ->
            if (helpcard != null && binding != null) {
                binding!!.textViewTitle.text = helpcard.title
                binding!!.textViewDescription.text = helpcard.description
                binding!!.textVictoryCondition.text = helpcard.victoryCondition
                binding!!.textEndGame.text = helpcard.endGame
                binding!!.textPreparation.text = helpcard.preparation
                binding!!.textPlayerTurn.text = helpcard.playerTurn
                binding!!.textEffects.text = helpcard.effects
            }
        }
    }

    private fun navigateToCardEdit() {
        viewModel.helpcardId.observe(viewLifecycleOwner) { helpcardId ->
            if (helpcardId != null) {
                val action = HelpcardFragmentDirections.actionNavHelpcardToNavCardEdit().setId(helpcardId)
                navController.navigate(action)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onVictoryCondition() {
        binding!!.textVictoryCondition.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding!!.textVictoryCondition)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onEndGame() {
        binding!!.textEndGame.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding!!.textEndGame)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onPreparation() {
        binding!!.textPreparation.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding!!.textPreparation)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onPlayerTurn() {
        binding!!.textPlayerTurn.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding!!.textPlayerTurn)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onEffects() {
        binding!!.textEffects.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            showLabel(view, motionEvent, binding!!.textEffects)
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
        binding!!.layoutExpandableHelpcard.layoutTransition = layoutTransition
    }

}
