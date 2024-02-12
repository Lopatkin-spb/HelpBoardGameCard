package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByBoardgameIdUseCase

class HelpcardViewModel(
    private val getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase
) : ViewModel() {

    private val _boardgameId = MutableLiveData<Long>()
    private val _helpcard = MutableLiveData<Helpcard>()
    val helpcard: LiveData<Helpcard> = _helpcard
    val boardgameId: LiveData<Long> = _boardgameId

    fun loadHelpcard(boardgameId: Long?) {
        _boardgameId.value = boardgameId
        viewModelScope.launch {
            val result = try {
                getHelpcardByBoardgameIdUseCase.execute(boardgameId)
            } catch (cause: Throwable) {
                Result.failure(cause)
            }
            when (result.isSuccess) {
                true -> {
                    _helpcard.value = result.getOrNull()
                }

                else -> {
                    _helpcard.value = result.getOrNull()
                }
            }
        }
    }

}
