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
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.presentation.AbstractFragment
import space.lopatkin.spb.helpboardgamecard.presentation.ViewModelFactory
import javax.inject.Inject

class SettingsFragment : AbstractFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SettingsViewModel
    private var binding: FragmentSettingsBinding? = null

    override fun onAttach(context: Context) {
        (context.applicationContext as HelpBoardGameCardApplication).applicationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        setupSpinner()
        loadKeyboardType()
        onActionSpinner()

        return view
    }

    override fun onResume() {
        super.onResume()
        resultListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupSpinner() {
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.action_spinner_keyboards,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if (binding != null) {
            binding!!.actionSpinnerKeyboards.adapter = adapter
        }
    }

    private fun loadKeyboardType() {
        viewModel.loadKeyboardType()
        viewModel.keyboardType.observe(viewLifecycleOwner) { type: KeyboardType ->
            if (binding != null) {
                binding!!.actionSpinnerKeyboards.setSelection(type.ordinal)
            }
        }
    }

    private fun onActionSpinner() {
        val spinnerListener: SpinnerInteractionListener = SpinnerInteractionListener()

        if (binding != null) {
            binding!!.actionSpinnerKeyboards.setOnTouchListener(spinnerListener)
            binding!!.actionSpinnerKeyboards.onItemSelectedListener = spinnerListener
        }

        spinnerListener.setEndActionListener(SpinnerInteractionListener.OnEndActionListener { userSelection ->
            viewModel.saveKeyboardType(userSelection)
        })

    }

    private fun resultListener() {
        viewModel.message.observe(this) { result: Message ->
            if (result != Message.POOL_EMPTY) {
                selectingTextFrom(result)
            }
        }

    }

    private fun selectingTextFrom(result: Message) {
        if (binding != null) {
            when (result) {
                Message.ACTION_ENDED_SUCCESS -> showMessage(
                    binding!!.layoutSettings,
                    R.string.message_action_ended_success
                )

                Message.ACTION_ENDED_ERROR -> showMessage(binding!!.layoutSettings, R.string.error_action_ended)
                else -> {}
            }
        }
    }

}