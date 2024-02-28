package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByBoardgameIdUseCase

class HelpcardViewModel(
    private val getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : ViewModel() {

    private val _boardgameId = MutableLiveData<Long>()
    private val _helpcard = MutableLiveData<Helpcard>()
    private val _message = MutableLiveData<Message>()
    val helpcard: LiveData<Helpcard> = _helpcard
    val boardgameId: LiveData<Long> = _boardgameId
    val message: LiveData<Message> = _message

    fun loadHelpcard(boardgameId: Long?) {
        _boardgameId.value = boardgameId
        viewModelScope.launch(dispatchers.main + CoroutineName(LOAD_HELPCARD)) {
            getHelpcardByBoardgameIdUseCase.execute(boardgameId)
                .cancellable()
                .catch { exception ->
                    //TODO: logging only exception but not error
                    _message.value = Message.ACTION_ENDED_ERROR
                }
                .onCompletion {
                    //TODO: stop loading
                }
                .collect { result ->
                    _helpcard.value = result
                }
        }
    }

    companion object {
        private const val LOAD_HELPCARD: String = "coroutine_load_helpcard"
    }

}
