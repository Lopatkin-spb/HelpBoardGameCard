package space.lopatkin.spb.helpboardgamecard.domain.repository

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface BoardgameRepository {

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw>

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    suspend fun getAllBoardgamesInfo(): Result<List<BoardgameInfo>>

    /**
     * Удалить настолку.
     */
    suspend fun deleteBoardgameBy(boardgameId: Long): Result<Message>

    /**
     * Обновить мин данные настолки.
     */
    suspend fun update(boardgameInfo: BoardgameInfo): Result<Message>

    /**
     * Удалить незаблокированные настолки.
     */
    suspend fun deleteUnlockBoardgames(): Result<Message>

    /**
     * Сохранить новую настолку.
     */
    suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message>

    /**
     * Обновить настолку.
     */
    suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message>

}
