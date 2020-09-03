package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.lifecycle.LiveData;
import androidx.room.*;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

@Dao
public interface HelpCardDao {

    //выбрать все
    @Query("SELECT * FROM helpCard")
    List<Helpcard> getAll();

    //обновление списка с данными через ливдата (недопонял)
    @Query("SELECT * FROM helpCard")
    LiveData<List<Helpcard>> getAllLiveData();

    //
    @Query("SELECT * FROM helpCard WHERE uniqueId IN (:userIds)")
    List<Helpcard> loadAllByIds(int[] userIds);

    //
    @Query("SELECT * FROM helpCard WHERE uniqueId = :uniqueId LIMIT 1")
    Helpcard findById(int uniqueId);

    //если при вставке сущности уже есть с таким названием
    // - сущность заменяется на новую
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Helpcard helpCard);

    //функция для обновления
    @Update
    void update(Helpcard helpCard);

    //
    @Delete
    void delete(Helpcard helpCard);


}
