package space.lopatkin.spb.helpboardgamecard.domain.repository

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable

interface SettingsRepository {

    /**
     * Сохранить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     *
     * @param type выбранный пользователем тип.
     */
    fun saveKeyboardType(type: KeyboardType): Flow<Completable>

    /**
     * Получить тип включенной клавиатуры в настройках: кастомная или дефолтная.
     */
    fun getKeyboardType(): Flow<KeyboardType>

}
