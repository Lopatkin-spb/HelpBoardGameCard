package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface BoardgameLocalDataSource {

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    suspend fun getAllBoardgamesInfo(): Result<List<BoardgameInfo>>

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw>

    /**
     * Сохранить новую настолку.
     */
    suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Message

    /**
     * Обновить настолку.
     */
    suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Message

    /**
     * Обновить мин данные настолки.
     */
    suspend fun update(boardgameInfo: BoardgameInfo): Result<Message>

    /**
     * Удалить настолку.
     */
    suspend fun deleteBoardgameBy(boardgameId: Long): Result<Message>

    /**
     * Удалить все незаблокированные настолки.
     */
    suspend fun deleteUnlockBoardgames(): Result<Message>

    /**
     * Получить идентификаторы всех незаблокированных настолок.
     */
    suspend fun getBoardgamesIdByUnlock(): Result<Array<Long>>

}
