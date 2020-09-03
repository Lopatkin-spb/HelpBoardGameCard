//package space.lopatkin.spb.helpboardgamecard;
//
//import android.app.Application;
//import androidx.room.Room;
//import space.lopatkin.spb.helpboardgamecard.dataRoom.AppDatabase;
//import space.lopatkin.spb.helpboardgamecard.dataRoom.HelpcardDao;
//
//public class App extends Application {
//
//    private AppDatabase database;
//    private HelpcardDao helpCardDao;
//
//
//    //допуск к классу из любого другого (антипаттерн синглтон - лучше его не использовать)
//    private static App instance;
//
//    public static App getInstance() {
//        return instance;
//    }
//
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        instance = this;
//
//        database = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class,
//                "app-db-name")
//                //сейчас работа с базой данных сделана в одном потоке с ЮаЙ,
//                // впоследствии надо сделать в другой поток. чтоб работа приложения была корректной
//                .allowMainThreadQueries()
//                .build();
//
//        helpCardDao =  database.helpCardDao();
//
//    }
//
//    public AppDatabase getDatabase () {
//        return database;
//    }
//
//    public void setDatabase (AppDatabase database) {
//        this.database = database;
//    }
//
//    public HelpcardDao getHelpCardDao() {
//        return helpCardDao;
//    }
//
//    public void setHelpCardDao(HelpcardDao helpCardDao) {
//        this.helpCardDao = helpCardDao;
//    }
//}
