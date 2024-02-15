package space.lopatkin.spb.helpboardgamecard.domain.repository

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface SettingsRepository {

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
