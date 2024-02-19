package space.lopatkin.spb.helpboardgamecard.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase

class SettingsViewModel(
    private val saveKeyboardTypeByUserChoiceUseCase: SaveKeyboardTypeByUserChoiceUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private val _keyboardType = MutableLiveData<KeyboardType>()
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message> = _message
    val keyboardType: LiveData<KeyboardType> = _keyboardType

    fun loadKeyboardType() {
        viewModelScope.launch(dispatchers.io + CoroutineName(LOAD_KEYBOARD_TYPE)) {
            val result: Result<KeyboardType> =
                try {
                    getKeyboardTypeUseCase.execute()
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.isSuccess) {
                true -> {
                    _keyboardType.postValue(result.getOrDefault(DEFAULT_TYPE))
                }

                else -> {
                    //TODO: logging error
                    _keyboardType.postValue(DEFAULT_TYPE)
                }
            }
        }
    }

    fun saveKeyboardType(type: Any?) {
        viewModelScope.launch(dispatchers.io + CoroutineName(SAVE_KEYBOARD_TYPE)) {
            val result: Result<Message> =
                try {
                    saveKeyboardTypeByUserChoiceUseCase.execute(type)
                } catch (cause: Throwable) {
                    Result.failure(cause)
                }
            when (result.isSuccess) {
                true -> {
                    _message.postValue(result.getOrNull())
                }

                else -> {
                    //TODO: logging error
                    _message.postValue(Message.ACTION_ENDED_ERROR)
                }
            }
        }
    }

    companion object {
        private val DEFAULT_TYPE: KeyboardType = KeyboardType.CUSTOM
        private const val SAVE_KEYBOARD_TYPE: String = "coroutine_save_keyboard_type"
        private const val LOAD_KEYBOARD_TYPE: String = "coroutine_load_keyboard_type"
    }

}