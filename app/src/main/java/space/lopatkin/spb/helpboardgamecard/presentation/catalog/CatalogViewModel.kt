package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*

class CatalogViewModel(
    private val getAllHelpcardsUseCase: GetAllHelpcardsUseCase,
    private val deleteHelpcardUnlockedByHelpcardIdUseCase: DeleteHelpcardUnlockedByHelpcardIdUseCase,
    private val updateHelpcardFavoriteByHelpcardIdUseCase: UpdateHelpcardFavoriteByHelpcardIdUseCase,
    private val updateHelpcardLockingByHelpcardIdUseCase: UpdateHelpcardLockingByHelpcardIdUseCase,
    private val deleteHelpcardsByUnlockStateUseCase: DeleteHelpcardsByUnlockStateUseCase
) : ViewModel() {

    private val messageMutable = MutableLiveData<Message>()
    val message: LiveData<Message> = messageMutable
    var listHelpcards: LiveData<List<Helpcard>>? = null

    fun loadHelpcardsList() {
        listHelpcards = getAllHelpcardsUseCase.execute()
    }

    fun deleteAllUnlockHelpcards() {
        deleteHelpcardsByUnlockStateUseCase.execute()
    }

    fun updateFavorite(helpcard: Helpcard?) {
        val messageResponse: Message = updateHelpcardFavoriteByHelpcardIdUseCase.execute(helpcard)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

    fun updateLocking(helpcard: Helpcard?) {
        val messageResponse: Message = updateHelpcardLockingByHelpcardIdUseCase.execute(helpcard)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

    fun delete(helpcard: Helpcard?) {
        val messageResponse: Message = deleteHelpcardUnlockedByHelpcardIdUseCase.execute(helpcard)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}
