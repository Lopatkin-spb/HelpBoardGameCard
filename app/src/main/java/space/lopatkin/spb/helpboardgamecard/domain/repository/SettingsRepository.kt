package space.lopatkin.spb.helpboardgamecard.domain.repository

import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface SettingsRepository {

    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    suspend fun saveKeyboardType(type: Int): Message

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    suspend fun getKeyboardType(): Int

}
