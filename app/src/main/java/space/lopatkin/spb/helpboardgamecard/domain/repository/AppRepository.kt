package space.lopatkin.spb.helpboardgamecard.domain.repository

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

interface AppRepository {

    /**
     * Получить карточку памяти по идентификатору настольной игры.
     */
    fun getHelpcard(boardGameId: Int): LiveData<Helpcard>

    /**
     * Получить все карточки памяти.
     */
    fun getAllHelpcards(): LiveData<List<Helpcard>>

    /**
     * Удалить карточку памяти.
     */
    fun delete(helpcard: Helpcard)

    /**
     * Удалить карточку памяти.
     */
    fun delete(id: Int)

    /**
     * Обновить карточку памяти.
     */
    fun update(helpcard: Helpcard)

    /**
     * Удалить все незаблокированные карточки памяти.
     */
    fun deleteAllUnlockHelpcards()

    /**
     * Сохранить новую карточку памяти.
     */
    fun saveNewHelpcard(helpcard: Helpcard)

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
