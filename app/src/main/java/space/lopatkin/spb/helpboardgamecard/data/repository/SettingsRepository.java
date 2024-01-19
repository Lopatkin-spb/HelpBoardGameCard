package space.lopatkin.spb.helpboardgamecard.data.repository;

public interface SettingsRepository {

    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    void saveKeyboardType(int type);

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    int getKeyboardType();

}
