package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class GetKeyboardTypeUseCase(private val repository: AppRepository) {

    fun execute(): KeyboardType {
        return KeyboardType.getOrdinalFrom(value = repository.getKeyboardType())
    }

}
