package space.lopatkin.spb.helpboardgamecard.domain.repository

import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.domain.model.*

interface BoardgameRepository {

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
    suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Message

    /**
     * Обновить настолку.
     */
    suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Message

}
