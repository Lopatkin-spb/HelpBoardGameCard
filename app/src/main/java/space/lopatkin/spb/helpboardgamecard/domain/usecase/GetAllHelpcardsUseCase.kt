package space.lopatkin.spb.helpboardgamecard.domain.usecase

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class GetAllHelpcardsUseCase(private val repository: AppRepository) {

    fun execute(): LiveData<List<Helpcard>> {
        return repository.getAllHelpcards()
    }

}
