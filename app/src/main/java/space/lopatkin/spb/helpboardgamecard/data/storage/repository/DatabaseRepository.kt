package space.lopatkin.spb.helpboardgamecard.data.storage.repository

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.domain.model.*

interface DatabaseRepository {

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>>

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw>

    /**
     * Сохранить новую настолку.
     */
    fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw)

    /**
     * Обновить настолку.
     */
    fun updateBoardgameBy(boardgameRaw: BoardgameRaw)

    /**
     * Обновить мин данные настолки.
     */
    fun update(boardgameInfo: BoardgameInfo)

    /**
     * Удалить настолку.
     */
    fun deleteBoardgameBy(boardgameId: Long)

    /**
     * Удалить все незаблокированные настолки.
     */
    fun deleteUnlockBoardgames()

}
