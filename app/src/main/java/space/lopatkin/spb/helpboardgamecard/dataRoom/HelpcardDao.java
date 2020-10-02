package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.lifecycle.LiveData;
import androidx.room.*;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;

@Dao
public interface HelpcardDao {


        //обновление списка с данными через ливдата (недопонял)
    @Query("SELECT * FROM helpcard_table ORDER BY priority DESC")
    LiveData<List<Helpcard>> getAllHelpcards();


    @Query("SELECT * FROM helpcard_table WHERE favorites = 1 ORDER BY priority DESC")
    LiveData<List<Helpcard>> getAllFavoritesHelpcards();


//    //если при вставке сущности уже есть с таким названием
//    // - сущность заменяется на новую
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(Helpcard helpcard);


    //для вставки
    @Insert
    void insert(Helpcard helpcard);


    //функция для обновления
    @Update
    void update(Helpcard helpcard);


    //для стирания
    @Delete
    void delete(Helpcard helpcard);


    @Query("DELETE FROM helpcard_table")
    void deleteAllHelpcards();


    @Query("DELETE FROM helpcard_table WHERE lock = 0")
    void deleteAllUnlockHelpcards();
}
