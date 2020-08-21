package space.lopatkin.spb.helpboardgamecard.data;


import androidx.room.Database;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

@Database(entities = {HelpCard.class}, version = 1, exportSchema = false)
public abstract class AppDatabase {

    //в классе лежат все таблицы которые будут в базе
    public abstract HelpCardDao helpCardDao();

}
