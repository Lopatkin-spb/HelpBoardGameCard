package space.lopatkin.spb.helpboardgamecard.data.storage.database

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;

@Dao
interface HelpcardDao {

    /**
     * Получить карточку памяти по идентификатору настольной игры.
     */
    @Query("SELECT * FROM helpcard_table WHERE id=:boardGameId")
    fun getHelpcard(boardGameId: Int): LiveData<Helpcard>

    /**
     * Получить все карточки памяти и применить фильтр по убыванию.
     */
    @Query("SELECT * FROM helpcard_table ORDER BY priority DESC")
    fun getAllHelpcards(): LiveData<List<Helpcard>>

    /**
     * Удалить карточку памяти.
     */
    @Delete
    fun delete(helpcard: Helpcard)

    /**
     * Удалить карточку памяти по идентификационному номеру.
     */
    @Query("DELETE FROM helpcard_table WHERE id=:boardGameId")
    fun delete(boardGameId: Int)

    /**
     * Обновить карточку памяти.
     */
    @Update
    fun update(helpcard: Helpcard)

    /**
     * Добавить новую карточку памяти.
     */
    @Insert
    fun insert(helpcard: Helpcard)

    /**
     * Удалить все незаблокированные карточки памяти.
     */
    @Query("DELETE FROM helpcard_table WHERE lock = 0")
    fun deleteAllUnlockHelpcards()

    //todo: do work -------
    @Query("SELECT * FROM helpcard_table WHERE favorites = 1 ORDER BY priority DESC")
    fun getAllFavoritesHelpcards(): LiveData<List<Helpcard>>

    @Query("DELETE FROM helpcard_table")
    fun deleteAllHelpcards()

}
