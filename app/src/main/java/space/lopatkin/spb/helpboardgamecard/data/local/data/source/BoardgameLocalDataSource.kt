package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

interface BoardgameLocalDataSource {

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    fun getAllBoardgamesInfo(): Flow<List<BoardgameInfo>>

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
    suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message>

    /**
     * Обновить настолку.
     */
    suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message>

    /**
     * Обновить мин данные настолки.
     */
    fun update(boardgameInfo: BoardgameInfo): Flow<Message>

    /**
     * Удалить настолку.
     */
    fun deleteBoardgameBy(boardgameId: Long): Flow<Message>

    /**
     * Удалить все незаблокированные настолки.
     */
    fun deleteUnlockBoardgames(): Flow<Message>

}
