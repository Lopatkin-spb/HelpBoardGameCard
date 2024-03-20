package space.lopatkin.spb.helpboardgamecard.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.application.HelpBoardGameCardApplication
import space.lopatkin.spb.helpboardgamecard.databinding.FragmentSettingsBinding
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class SettingsFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view: View = binding.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)
        setupSpinner()
        onActionSpinner()

        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadKeyboardType()
        uiStateListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.action_spinner_keyboards,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.actionSpinnerKeyboards.adapter = adapter
    }

    private fun onActionSpinner() {
        val spinnerListener: SpinnerInteractionListener = SpinnerInteractionListener()

        binding.actionSpinnerKeyboards.setOnTouchListener(spinnerListener)
        binding.actionSpinnerKeyboards.onItemSelectedListener = spinnerListener

        spinnerListener.setEndActionListener(SpinnerInteractionListener.OnEndActionListener { userSelection ->
            viewModel.saveKeyboardType(userSelection)
        })

    }

    private fun uiStateListener() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (uiState.isLoading != binding.loadingIndicator.isRefreshing) {
                binding.loadingIndicator.isRefreshing = uiState.isLoading
            }
            uiState.keyboard?.let { type ->
                binding.actionSpinnerKeyboards.setSelection(type.ordinal)
                viewModel.keyboardInstalledToScreen()
            }
            uiState.message?.let { message ->
                selectingTextFrom(message)
                viewModel.messageShowedToUser()
            }
        }
    }

    private fun selectingTextFrom(result: Message) {
        when (result) {
            Message.ACTION_ENDED_ERROR -> showMessage(binding.layoutSettings, R.string.error_action_ended)
            else -> {}
        }
    }

}