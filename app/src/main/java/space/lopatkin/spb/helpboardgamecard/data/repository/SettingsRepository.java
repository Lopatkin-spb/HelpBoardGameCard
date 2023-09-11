package space.lopatkin.spb.helpboardgamecard.data.repository;

public interface SettingsRepository {

    /**
     * Сохранить вариант включенной клавиатуры: кастомная или дефолтная.
     */
    void saveKeyboardVariant(int keyboardVariant);

    /**
     * Получить вариант включенной клавиатуры: кастомная или дефолтная.
     */
    int getKeyboardVariant();

}
