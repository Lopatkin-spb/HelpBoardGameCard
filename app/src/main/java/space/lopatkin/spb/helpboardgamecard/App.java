package space.lopatkin.spb.helpboardgamecard;

import android.app.Application;
import androidx.room.Room;
import space.lopatkin.spb.helpboardgamecard.data.AppDatabase;
import space.lopatkin.spb.helpboardgamecard.data.HelpCardDao;
import space.lopatkin.spb.helpboardgamecard.model.HelpCard;

public class App extends Application {

    private AppDatabase database;
    private HelpCardDao helpCardDao;


    //допуск к классу из любого другого (антипаттерн синглтон - лучше его не использовать)
    private static App instance;

    public static App getInstance() {
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                "app-db-name")
                //сейчас работа с базой данных сделана в одном потоке с ЮаЙ,
                // впоследствии надо сделать в другой поток. чтоб работа приложения была корректной
                .allowMainThreadQueries()
                .build();

        helpCardDao =  database.helpCardDao();

    }

    public AppDatabase getDatabase () {
        return database;
    }

    public void setDatabase (AppDatabase database) {
        this.database = database;
    }

    public HelpCardDao getHelpCardDao() {
        return helpCardDao;
    }

    public void setHelpCardDao(HelpCardDao helpCardDao) {
        this.helpCardDao = helpCardDao;
    }
}
