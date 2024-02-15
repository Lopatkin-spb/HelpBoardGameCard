package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class GetKeyboardTypeUseCase(private val repository: SettingsRepository) {

    suspend fun execute(): Result<KeyboardType> {
        return repository.getKeyboardType()
    }

}
