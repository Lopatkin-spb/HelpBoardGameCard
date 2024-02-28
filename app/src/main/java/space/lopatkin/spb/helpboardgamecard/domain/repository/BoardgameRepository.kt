package space.lopatkin.spb.helpboardgamecard.domain.repository

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface BoardgameRepository {

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    fun getHelpcardBy(boardgameId: Long): Flow<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    fun getBoardgameRawBy(boardgameId: Long): Flow<BoardgameRaw>

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    fun getAllBoardgamesInfo(): Flow<List<BoardgameInfo>>

    /**
     * Удалить настолку.
     */
    fun deleteBoardgameBy(boardgameId: Long): Flow<Message>

    /**
     * Обновить мин данные настолки.
     */
    fun update(boardgameInfo: BoardgameInfo): Flow<Message>

    /**
     * Удалить незаблокированные настолки.
     */
    fun deleteUnlockBoardgames(): Flow<Message>

    /**
     * Сохранить новую настолку.
     */
    fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message>

    /**
     * Обновить настолку.
     */
    fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message>

}
