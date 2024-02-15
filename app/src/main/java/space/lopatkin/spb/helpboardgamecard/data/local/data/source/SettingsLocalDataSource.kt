package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface SettingsLocalDataSource {
    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    suspend fun saveKeyboardType(type: KeyboardType): Result<Message>

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    suspend fun getKeyboardType(): Result<KeyboardType>

}
