package space.lopatkin.spb.helpboardgamecard.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SaveKeyboardTypeByUserChoiceUseCase(private val repository: SettingsRepository) {

    fun execute(userChoice: Any?): Flow<Message> {
        return flow {
            if (userChoice == null) {
                throw Exception("NotFoundException (usecase): data (userChoiceKeyboardType) is null")
            } else {
                val type: KeyboardType = KeyboardType.getOrdinalFrom(userChoice.toString())
                emit(type)
            }
        }.flatMapMerge { data -> repository.saveKeyboardType(data) }
    }

}
