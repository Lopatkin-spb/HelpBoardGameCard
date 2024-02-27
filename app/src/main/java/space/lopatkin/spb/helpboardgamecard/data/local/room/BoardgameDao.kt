package space.lopatkin.spb.helpboardgamecard.data.local.room

import androidx.lifecycle.LiveData;
import androidx.room.*
import space.lopatkin.spb.helpboardgamecard.domain.model.*

@Dao
interface BoardgameDao {

    /**
     * Получить карточку памяти по идентификатору настолки.
     *
     * @return Helpcard - если есть данные, Null - если данных нет.
     */
    @Query("SELECT * FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    suspend fun getHelpcardBy(boardgameId: Long): Helpcard?

    /**
     * Получить идентификатор карточки памяти по идентификатору настолки.
     */
    @Query("SELECT ${Helpcard.TABLE_NAME}.${Helpcard.COLUMN_ID} FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    suspend fun getHelpcardIdBy(boardgameId: Long): Long

    /**
     * Получить полные сырые данные настолки по идентификатору настолки.
     *
     * @return BoardgameRaw - если есть данные, Null - если данных нет.
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
    suspend fun getBoardgameRawBy(boardgameId: Long): BoardgameRaw?

    /**
     * Получить все настолки с мин данными с фильтром по убыванию приоритета.
     *
     * @return List - если есть данные, ListEmpty - если данных нет.
     */
    @Query("SELECT * FROM ${BoardgameInfo.TABLE_NAME} ORDER BY ${BoardgameInfo.COLUMN_PRIORITY} DESC")
    fun getAllBoardgamesInfo(): List<BoardgameInfo>

    /**
     * Удалить настолку с мин данными по идентификатору настолки.
     *
     * @return количество удаленных объектов.
     */
    @Query("DELETE FROM ${BoardgameInfo.TABLE_NAME} WHERE ${BoardgameInfo.COLUMN_ID} = :boardgameId")
    fun deleteBoardgameInfoBy(boardgameId: Long): Int

    /**
     * Удалить карточку памяти по идентификатору настолки.
     *
     * @return количество удаленных объектов.
     */
    @Query("DELETE FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_BOARDGAME_ID} = :boardgameId")
    fun deleteHelpcardBy(boardgameId: Long): Int

    /**
     * Удалить карточку памяти по идентификатору карточки памяти.
     *
     * @return количество удаленных объектов.
     */
    @Query("DELETE FROM ${Helpcard.TABLE_NAME} WHERE ${Helpcard.COLUMN_ID} = :helpcardId")
    suspend fun deleteHelpcardByOwn(helpcardId: Long): Int

    /**
     * Обновить мин данные настолки по идентификатору настолки.
     *
     * @return количество обновленных объектов.
     */
    @Update(entity = BoardgameInfo::class)
    fun update(boardgameInfo: BoardgameInfo): Int

    /**
     * Обновить карточку памяти по идентификатору карточки памяти.
     *
     * @return количество обновленных объектов.
     */
    @Update(entity = Helpcard::class)
    suspend fun update(helpcard: Helpcard): Int

    /**
     * Добавить мин данные о настолке.
     *
     * @return идентификатор добавленного объекта.
     */
    @Insert(entity = BoardgameInfo::class)
    suspend fun add(boardgameInfo: BoardgameInfo): Long

    /**
     * Добавить карточку памяти.
     *
     * @return идентификатор добавленного объекта.
     */
    @Insert(entity = Helpcard::class)
    suspend fun add(helpcard: Helpcard): Long

    /**
     * Получить идентификаторы всех незаблокированных настолок.
     *
     * @return List - если есть данные, ListEmpty - если данных нет.
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
