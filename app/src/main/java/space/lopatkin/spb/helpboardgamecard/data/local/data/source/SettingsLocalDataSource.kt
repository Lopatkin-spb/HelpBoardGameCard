package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface SettingsLocalDataSource {

    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    fun saveKeyboardType(type: KeyboardType): Flow<Message>

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    fun getKeyboardType(): Flow<KeyboardType>

}
