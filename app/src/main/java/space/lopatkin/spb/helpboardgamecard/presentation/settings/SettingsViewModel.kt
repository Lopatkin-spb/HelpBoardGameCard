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

    private val keyboardTypeMutable = MutableLiveData<KeyboardType>()
    private val messageMutable = MutableLiveData<Message>()
    val message: LiveData<Message> = messageMutable
    val keyboardType: LiveData<KeyboardType> = keyboardTypeMutable

    fun loadKeyboardType() {
        keyboardTypeMutable.value = getKeyboardTypeUseCase.execute()
    }

    fun saveKeyboardType(type: Any?) {
        val messageResponse: Message = saveKeyboardTypeByUserChoiceUseCase.execute(type)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}