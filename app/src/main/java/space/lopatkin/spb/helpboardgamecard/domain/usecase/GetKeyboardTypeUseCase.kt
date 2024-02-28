package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class GetKeyboardTypeUseCase(private val repository: SettingsRepository) {

    fun execute(): Flow<KeyboardType> {
        return repository.getKeyboardType()
    }

}
