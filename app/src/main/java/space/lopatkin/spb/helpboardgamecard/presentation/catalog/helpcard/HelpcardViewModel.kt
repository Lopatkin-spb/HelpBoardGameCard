package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase

class HelpcardViewModel(
    private val getHelpcardByHelpcardIdUseCase: GetHelpcardByHelpcardIdUseCase
) : ViewModel() {

    private val helpcardIdMutable = MutableLiveData<Int>()
    var helpcard: LiveData<Helpcard>? = null
    val helpcardId: LiveData<Int> = helpcardIdMutable

    fun loadHelpcard(helpcardId: Int) {
        helpcardIdMutable.value = helpcardId
        helpcard = Transformations.switchMap(helpcardIdMutable) { thisId: Int? ->
            getHelpcardByHelpcardIdUseCase.execute(thisId!!)
        }
    }

}
