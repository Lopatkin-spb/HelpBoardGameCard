package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByHelpcardIdUseCase

class CardEditViewModel(
    private val getHelpcardByHelpcardIdUseCase: GetHelpcardByHelpcardIdUseCase,
    private val updateHelpcardByHelpcardIdUseCase: UpdateHelpcardByHelpcardIdUseCase,
    private val getKeyboardTypeUseCase: GetKeyboardTypeUseCase
) : ViewModel() {

    private val helpcardIdMutable = MutableLiveData<Int>()
    private val keyboardTypeMutable = MutableLiveData<KeyboardType>()
    private val messageMutable = MutableLiveData<Message>()
    var helpcard: LiveData<Helpcard>? = null
    val keyboardType: LiveData<KeyboardType> = keyboardTypeMutable
    val message: LiveData<Message> = messageMutable

    fun loadKeyboardType() {
        keyboardTypeMutable.value = getKeyboardTypeUseCase.execute()
    }

    fun loadHelpcard(helpcardId: Int) {
        helpcardIdMutable.value = helpcardId
        helpcard = Transformations.switchMap(helpcardIdMutable) { thisId ->
            getHelpcardByHelpcardIdUseCase.execute(thisId!!)
        }
    }

    fun update(helpcard: Helpcard?) {
        val messageResponse: Message = updateHelpcardByHelpcardIdUseCase.execute(helpcard)
        messageMutable.value = messageResponse
        messageMutable.value = Message.POOL_EMPTY
    }

}