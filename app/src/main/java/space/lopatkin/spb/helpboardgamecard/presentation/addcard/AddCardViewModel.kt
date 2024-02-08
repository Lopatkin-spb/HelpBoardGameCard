package space.lopatkin.spb.helpboardgamecard.presentation.addcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveBoardgameNewByBoardgameIdUseCase

class AddCardViewModel(
    private val saveBoardgameNewByBoardgameIdUseCase: SaveBoardgameNewByBoardgameIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val keyboardTypeMutable = MutableLiveData<KeyboardType>()
    private val messageMutable = MutableLiveData<Message>()
    val keyboardType: LiveData<KeyboardType> = keyboardTypeMutable
    val message: LiveData<Message> = messageMutable

    fun loadKeyboardType() {
        keyboardTypeMutable.value = getKeyboardTypeUseCase.execute()
    }

    fun saveNewBoardgame(boardgameRaw: BoardgameRaw?) {
        val messageResponse: Message = saveBoardgameNewByBoardgameIdUseCase.execute(boardgameRaw)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}
