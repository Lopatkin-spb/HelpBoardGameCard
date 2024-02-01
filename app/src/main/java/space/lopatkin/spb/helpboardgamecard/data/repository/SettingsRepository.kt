package space.lopatkin.spb.helpboardgamecard.data.repository

interface SettingsRepository {
    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    fun saveKeyboardType(type: Int)

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    fun getKeyboardType(): Int

}
