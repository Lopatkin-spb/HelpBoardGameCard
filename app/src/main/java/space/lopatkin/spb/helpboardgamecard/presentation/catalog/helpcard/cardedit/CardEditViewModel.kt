package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.*
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetBoardgameRawByBoardgameIdUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateBoardgameByBoardgameIdUseCase

class CardEditViewModel(
    private val getBoardgameRawByBoardgameIdUseCase: GetBoardgameRawByBoardgameIdUseCase,
    private val updateBoardgameByBoardgameIdUseCase: UpdateBoardgameByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val boardgameIdMutable = MutableLiveData<Long>()
    private val keyboardTypeMutable = MutableLiveData<KeyboardType>()
    private val messageMutable = MutableLiveData<Message>()
    var boardgameRaw: LiveData<BoardgameRaw>? = null
    val keyboardType: LiveData<KeyboardType> = keyboardTypeMutable
    val message: LiveData<Message> = messageMutable

    fun loadKeyboardType() {
        keyboardTypeMutable.value = getKeyboardTypeUseCase.execute()
    }

    fun loadBoardgameRaw(boardgameId: Long?) {
        if (boardgameId != null && boardgameId > 0) {
            boardgameIdMutable.value = boardgameId
            boardgameRaw = Transformations.switchMap(boardgameIdMutable) { thisId ->
                getBoardgameRawByBoardgameIdUseCase.execute(thisId)
            }
        }
    }

    fun update(boardgameRaw: BoardgameRaw?) {
        val messageResponse: Message = updateBoardgameByBoardgameIdUseCase.execute(boardgameRaw)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}