package space.lopatkin.spb.helpboardgamecard.dataRoom;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

//import android.support.annotation.NonNull;

@Database(entities = {Helpcard.class}, version = 2)
public abstract class HelpcardDatabase extends RoomDatabase {

    private static HelpcardDatabase instance;

    public abstract HelpcardDao helpcardDao();

    public static synchronized HelpcardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HelpcardDatabase.class, "helpcard_database")
                    //начать новую бд либо можно сдеать миграцию,
                    // чтоб не потерять данные с прредыдущей бд
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private HelpcardDao helpcardDao;
        private PopulateDbAsyncTask(HelpcardDatabase db) {
            helpcardDao = db.helpcardDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            helpcardDao.insert(new Helpcard("Title 1", "Description 1", false, 1));
            helpcardDao.insert(new Helpcard("Title 2", "Description 2", false, 2));
            helpcardDao.insert(new Helpcard("Title 3", "Description 3", false, 3));
            return null;
        }
    }
}