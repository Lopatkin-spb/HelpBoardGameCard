package space.lopatkin.spb.helpboardgamecard.dataRoom;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

@Database(entities = {Helpcard.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    //в классе лежат все таблицы которые будут в базе
    public abstract HelpcardDao helpCardDao();

}
