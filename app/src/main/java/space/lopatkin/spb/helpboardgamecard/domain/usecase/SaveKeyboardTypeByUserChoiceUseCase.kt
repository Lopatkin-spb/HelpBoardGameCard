package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SaveKeyboardTypeByUserChoiceUseCase(private val repository: SettingsRepository) {

    suspend fun execute(userChoice: Any?): Message {
        if (userChoice != null) {
            val type: KeyboardType = KeyboardType.getOrdinalFrom(name = userChoice.toString())
            return repository.saveKeyboardType(type = type.ordinal)
        }
        return Message.ACTION_ENDED_ERROR
    }

}
