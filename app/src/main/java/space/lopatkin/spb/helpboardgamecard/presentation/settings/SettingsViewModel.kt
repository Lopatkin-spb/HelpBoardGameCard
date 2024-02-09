package space.lopatkin.spb.helpboardgamecard.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase

class SettingsViewModel(
    private val saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message> = _message
    val keyboardType: LiveData<KeyboardType> = _keyboardType

    fun loadKeyboardType() {
        _keyboardType.value = getKeyboardTypeUseCase.execute()
    }

    fun saveKeyboardType(type: Any?) {
        val messageResponse: Message = saveKeyboardTypeByUserChoiceUseCase.execute(type)
        _message.value = messageResponse
        _message.value = Message.POOL_EMPTY
    }

}