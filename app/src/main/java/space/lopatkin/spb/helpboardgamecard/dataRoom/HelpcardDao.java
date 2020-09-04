package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.lifecycle.LiveData;
import androidx.room.*;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

@Dao
public interface HelpcardDao {

//    //выбрать все
//    @Query("SELECT * FROM helpcard_table")
//    List<Helpcard> getAll();
//
//    //обновление списка с данными через ливдата (недопонял)
//    @Query("SELECT * FROM helpcard_table")
//    LiveData<List<Helpcard>> getAllLiveData();


    @Query("SELECT * FROM helpcard_table ORDER BY priority DESC")
    LiveData<List<Helpcard>> getAllHelpcards();

//    //
//    @Query("SELECT * FROM helpcard_table WHERE id IN (:userIds)")
//    List<Helpcard> loadAllByIds(int[] userIds);
//
//    //
//    @Query("SELECT * FROM helpcard_table WHERE id = :uniqueId LIMIT 1")
//    Helpcard findById(int uniqueId);


//    //если при вставке сущности уже есть с таким названием
//    // - сущность заменяется на новую
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(Helpcard helpcard);


    @Insert
    void insert(Helpcard helpcard);


    //функция для обновления
    @Update
    void update(Helpcard helpcard);


    //
    @Delete
    void delete(Helpcard helpcard);


    @Query("DELETE FROM helpcard_table")
    void deleteAllHelpcards();
}
