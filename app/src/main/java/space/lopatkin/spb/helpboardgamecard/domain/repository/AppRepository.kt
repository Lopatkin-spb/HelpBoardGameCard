package space.lopatkin.spb.helpboardgamecard.domain.repository

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.*

interface AppRepository {

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    fun getHelpcardBy(boardGameId: Long): LiveData<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw>

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>>

    /**
     * Удалить настолку.
     */
    fun deleteBoardgameBy(boardgameId: Long)

    /**
     * Обновить мин данные настолки.
     */
    fun update(boardgameInfo: BoardgameInfo)

    /**
     * Удалить незаблокированные настолки.
     */
    fun deleteUnlockBoardgames()

    /**
     * Сохранить новую настолку.
     */
    fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw)

    /**
     * Обновить настолку.
     */
    fun updateBoardgameBy(boardgameRaw: BoardgameRaw)

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
