package space.lopatkin.spb.helpboardgamecard.presentation.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    private val _text: MutableLiveData<String>

    init {
        _text = MutableLiveData()
        _text.value = "This is share fragment. пока свободный фрагмент"
    }

    val text: LiveData<String>
        get() = _text
}