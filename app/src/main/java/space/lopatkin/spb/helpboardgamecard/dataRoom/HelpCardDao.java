package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.lifecycle.LiveData;
import androidx.room.*;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

import java.util.List;

@Dao
public interface HelpCardDao {

    //выбрать все
    @Query("SELECT * FROM helpCard")
    List<HelpCard> getAll();

    //обновление списка с данными через ливдата (недопонял)
    @Query("SELECT * FROM helpCard")
    LiveData<List<HelpCard>> getAllLiveData();

    //
    @Query("SELECT * FROM helpCard WHERE uniqueId IN (:userIds)")
    List<HelpCard> loadAllByIds(int[] userIds);

    //
    @Query("SELECT * FROM helpCard WHERE uniqueId = :uniqueId LIMIT 1")
    HelpCard findById(int uniqueId);

    //если при вставке сущности уже есть с таким названием
    // - сущность заменяется на новую
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HelpCard helpCard);

    //функция для обновления
    @Update
    void update(HelpCard helpCard);

    //
    @Delete
    void delete(HelpCard helpCard);


}
