package space.lopatkin.spb.helpboardgamecard.dataRoom;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;


@Database(entities = {Helpcard.class}, version = 4)
public abstract class HelpcardDatabase extends RoomDatabase {

    private static HelpcardDatabase instance;

    public abstract HelpcardDao helpcardDao();

    //создание бд
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


    //заполнить бд 4 записи при первой установке приложения
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private HelpcardDao helpcardDao;

        private PopulateDbAsyncTask(HelpcardDatabase db) {
            helpcardDao = db.helpcardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            helpcardDao.insert(new Helpcard("Title 1", "victory_condition 1",
                    "end_game 1", "preparation 1", "Description 1",
                    "player_turn 1", "effects 1", false, false, 1));
            helpcardDao.insert(new Helpcard("Title 2", "victory_condition 2",
                    "end_game 2", "preparation 2", "Description 2",
                    "player_turn 2", "effects 2", false, false, 2));
            helpcardDao.insert(new Helpcard("Илос 1вар", "victory_condition 3",
                    "end_game 3", "1) \uD83D\uDD03\uD83C\uDF9F️\uD83D\uDC49\uD83D\uDC655️⃣\uD83C\uDF9F️\uD83D\uDC64\n2) \uD83D\uDC655️⃣⛵\uD83D\uDC64\n3) \uD83D\uDC65\uD83D\uDD1F\uD83D\uDD74️\uD83D\uDC64\n4) \uD83D\uDD03\uD83E\uDDE9",
                    "евро, семейка",
                    "player_turn 3", "effects 3", false, false, 3));
            helpcardDao.insert(new Helpcard("Илос 2вар", "victory_condition 4",
                    "end_game 4", "1) \uD83D\uDD03\uD83C\uDF9F️➕\uD83E\uDDE9\n2) 4️⃣\uD83E\uDDE9✖️\uD83D\uDC65\n3) \uD83D\uDC655️⃣\uD83C\uDF9F️\n ➕5️⃣\uD83D\uDEA4\n ➕\uD83D\uDD1F\uD83D\uDD74️\n ➕\uD83D\uDDBC",
                    "средняя, семейка, евро",
                    "player_turn 4", "effects 4", false, false, 4));
            return null;
        }
    }
}