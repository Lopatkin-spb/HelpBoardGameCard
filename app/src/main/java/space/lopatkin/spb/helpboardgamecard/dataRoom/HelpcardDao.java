package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.lifecycle.LiveData;
import androidx.room.*;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

@Dao
public interface HelpcardDao {

    /**
     * Получить карточку памяти по идентификатору настольной игры.
     */
    @Query("SELECT * FROM helpcard_table WHERE id=:boardGameId")
    LiveData<Helpcard> getHelpcard(int boardGameId);

    /**
     * Получить все карточки памяти и применить фильтр по убыванию.
     */
    @Query("SELECT * FROM helpcard_table ORDER BY priority DESC")
    LiveData<List<Helpcard>> getAllHelpcards();

    /**
     * Удалить карточку памяти.
     */
    @Delete
    void delete(Helpcard helpcard);

    /**
     * Удалить карточку памяти по идентификационному номеру.
     */
    @Query("DELETE FROM helpcard_table WHERE id=:boardGameId")
    void delete(int boardGameId);

    /**
     * Обновить карточку памяти.
     */
    @Update
    void update(Helpcard helpcard);

    /**
     * Добавить новую карточку памяти.
     */
    @Insert
    void insert(Helpcard helpcard);

    /**
     * Удалить все незаблокированные карточки памяти.
     */
    @Query("DELETE FROM helpcard_table WHERE lock = 0")
    void deleteAllUnlockHelpcards();

    //todo: do work -------
    @Query("SELECT * FROM helpcard_table WHERE favorites = 1 ORDER BY priority DESC")
    LiveData<List<Helpcard>> getAllFavoritesHelpcards();

    @Query("DELETE FROM helpcard_table")
    void deleteAllHelpcards();

}
