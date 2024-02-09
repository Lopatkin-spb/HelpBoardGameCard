package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByBoardgameIdUseCase

class HelpcardViewModel(
    private val getHelpcardByBoardgameIdUseCase: GetHelpcardByBoardgameIdUseCase
) : ViewModel() {

    private val _boardgameId = MutableLiveData<Long>()
    var helpcard: LiveData<Helpcard>? = null
    val boardgameId: LiveData<Long> = _boardgameId

    fun loadHelpcard(boardgameId: Long?) {
        if (boardgameId != null && boardgameId > 0) {
            _boardgameId.value = boardgameId
            helpcard = Transformations.switchMap(_boardgameId) { thisId ->
                getHelpcardByBoardgameIdUseCase.execute(thisId)
            }
        }
    }

}
