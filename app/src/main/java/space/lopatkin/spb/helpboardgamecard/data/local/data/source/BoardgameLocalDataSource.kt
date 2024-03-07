package space.lopatkin.spb.helpboardgamecard.data.local.data.source

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.domain.model.*

interface BoardgameLocalDataSource {

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    fun getAllBoardgamesInfo(): Flow<List<BoardgameInfo>>

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    fun getHelpcardBy(boardgameId: Long): Flow<Helpcard>

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    fun getBoardgameRawBy(boardgameId: Long): Flow<BoardgameRaw>

    /**
     * Сохранить новую настолку.
     */
    fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Completable>

    /**
     * Обновить настолку.
     */
    fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Completable>

    /**
     * Обновить мин данные настолки.
     */
    fun update(boardgameInfo: BoardgameInfo): Flow<Completable>

    /**
     * Удалить настолку.
     */
    fun deleteBoardgameBy(boardgameId: Long): Flow<Completable>

    /**
     * Удалить все незаблокированные настолки.
     */
    fun deleteUnlockBoardgames(): Flow<Completable>

}
