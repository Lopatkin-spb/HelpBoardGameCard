package space.lopatkin.spb.helpboardgamecard.data.local.room

import androidx.lifecycle.LiveData;
import androidx.room.*
import space.lopatkin.spb.helpboardgamecard.domain.model.*

@Dao
interface BoardgameDao {

    /**
     * Получить карточку памяти по идентификатору настолки.
     */
    @Query("SELECT * FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard>

    /**
     * Получить идентификатор карточки памяти по идентификатору настолки.
     */
    @Query("SELECT ${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_ID} FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    fun getHelpcardIdBy(boardgameId: Long): Long

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     */
    @Query(
        "SELECT " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_ID} AS id, " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_NAME} AS name, " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_DESCRIPTION} AS description, " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_FAVORITE} AS favorite, " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_LOCK} AS lock, " +
                "${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_PRIORITY} AS priority, " +

                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_VICTORY_COND} AS victoryCondition, " +
                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_PLAYER_TURN} AS playerTurn, " +
                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_END_GAME} AS endGame, " +
                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_EFFECTS} AS effects, " +
                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_PREPARATION} AS preparation, " +
                "${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_AUTHOR} AS author " +

                "FROM ${BoardgameInfo.TABLE_NAME}, ${Helpcard.TABLE_NAME} " +
                "WHERE ${BoardgameInfo.TABLE_NAME}.${BoardgameInfo.COLUMN_ID} = :boardgameId " +
                "AND ${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId"
    )
    fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw>

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     */
    @Query("SELECT * FROM ${BoardgameInfo.TABLE_NAME} ORDER BY ${BoardgameInfo.COLUMN_PRIORITY} DESC")
    fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>>

    /**
     * Удалить настолку с мин данными по идентификатору настолки.
     */
    @Query("DELETE FROM ${BoardgameInfo.TABLE_NAME} WHERE ${BoardgameInfo.COLUMN_ID} = :boardgameId")
    fun deleteBoardgameInfoBy(boardgameId: Long)

    /**
     * Удалить карточку памяти по идентификатору настолки.
     */
    @Query("DELETE FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    fun deleteHelpcardBy(boardgameId: Long)

    /**
     * Обновить мин данные настолки по идентификатору настолки.
     */
    @Update(entity = BoardgameInfo::class)
    fun update(boardgameInfo: BoardgameInfo)

    /**
     * Обновить карточку памяти по идентификатору карточки памяти.
     */
    @Update(entity = Helpcard::class)
    fun update(helpcard: Helpcard)

    /**
     * Добавить мин данные о настолке.
     * @return идентификатор добавленной настолки.
     */
    @Insert(entity = BoardgameInfo::class)
    fun add(boardgameInfo: BoardgameInfo): Long

    /**
     * Добавить карточку памяти.
     */
    @Insert(entity = Helpcard::class)
    fun add(helpcard: Helpcard)

    /**
     * Удалить все незаблокированные настолки с мин данными.
     */
    @Query("DELETE FROM ${BoardgameInfo.TABLE_NAME} WHERE ${BoardgameInfo.COLUMN_LOCK} = 0")
    fun deleteBoardgamesInfoByUnlock()

    /**
     * Получить идентификаторы всех незаблокированных настолок.
     */
    @Query("SELECT ${BoardgameInfo.COLUMN_ID} FROM ${BoardgameInfo.TABLE_NAME} WHERE ${BoardgameInfo.COLUMN_LOCK} = 0")
    fun getBoardgameIdsByUnlock(): Array<Long>

    /**
     * Получить любимые настолки с мин данными.
     */
    @Query(
        "SELECT * FROM ${BoardgameInfo.TABLE_NAME} WHERE ${BoardgameInfo.COLUMN_FAVORITE} = 1 " +
                "ORDER BY ${BoardgameInfo.COLUMN_PRIORITY} DESC"
    )
    fun getBoardgamesInfoFavorite(): LiveData<List<BoardgameInfo>>

}
