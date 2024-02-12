package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface SettingsLocalDataSource {
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
