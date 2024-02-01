package space.lopatkin.spb.helpboardgamecard.data.storage.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard

@Database(entities = [Helpcard::class], version = 16)
abstract class AppDatabase : RoomDatabase() {
    abstract fun helpcardDao(): HelpcardDao

    companion object {
        private const val NAME_DATABASE = "helpcard_database"
        private var instance: AppDatabase? = null

        //создание бд
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {

                instance = databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, NAME_DATABASE
                ) //начать новую бд либо можно сдеать миграцию,
                    // чтоб не потерять данные с прредыдущей бд
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            PopulateDbAsyncTask(db = instance!!).execute()
                        }
                    })
                    .build()
            }
            return instance!!
        }

        //заполнить бд 4 записи при первой установке приложения
        private class PopulateDbAsyncTask(private val db: AppDatabase) : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                val helpcardDao: HelpcardDao = db.helpcardDao()

                helpcardDao.insert(
                    Helpcard(
                        id = 0,
                        title = "Title 1",
                        victoryCondition = "victory_condition 1",
                        endGame = "end_game 1",
                        preparation = "preparation 1",
                        description = "Description 1",
                        playerTurn = "player_turn 1",
                        effects = "effects 1",
                        isFavorites = false,
                        isLock = true,
                        priority = 1
                    )
                )
                helpcardDao.insert(
                    Helpcard(
                        id = 0,
                        title = "Илос 3варик",
                        victoryCondition = "",
                        endGame = "",
                        preparation = "-#l0105#l0312#l0027\n-#l0027#l01081#l0031#l0102#l0006\n-#l0105#l0314#l0036\n-#l0313#l0015#l0101(#l0019+#l0018+10#l0005+5#l0007)/#l0117#l0305#l0015\n1)#l0107#l0306#l00362#l0031=\n(1:#l0031=4#l0032*#l0016)#l0102#l0006\n(2:#l0031)#l0115#l0017\n2) #l0031#l0036#l01023#l0204#l0035",
                        description = "безкризисная, управляемый рандом",
                        playerTurn = "",
                        effects = "",
                        isFavorites = false,
                        isLock = true,
                        priority = 2
                    )
                )
                helpcardDao.insert(
                    Helpcard(
                        id = 0,
                        title = "Илос 1вар",
                        victoryCondition = "victory_condition 3",
                        endGame = "end_game 3",
                        preparation = "1) \uD83D\uDD03\uD83C\uDF9F️\uD83D\uDC49\uD83D\uDC655️⃣\uD83C\uDF9F️\uD83D\uDC64\n2) \uD83D\uDC655️⃣⛵\uD83D\uDC64\n3) \uD83D\uDC65\uD83D\uDD1F\uD83D\uDD74️\uD83D\uDC64\n4) \uD83D\uDD03\uD83E\uDDE9",
                        description = "евро, семейка",
                        playerTurn = "player_turn 3",
                        effects = "effects 3",
                        isFavorites = false,
                        isLock = true,
                        priority = 3
                    )
                )
                helpcardDao.insert(
                    Helpcard(
                        id = 0,
                        title = "Илос 2вар",
                        victoryCondition = "victory_condition 4",
                        endGame = "end_game 4",
                        preparation = "1) \uD83D\uDD03\uD83C\uDF9F️➕\uD83E\uDDE9\n2) 4️⃣\uD83E\uDDE9✖️\uD83D\uDC65\n3) \uD83D\uDC655️⃣\uD83C\uDF9F️\n ➕5️⃣\uD83D\uDEA4\n ➕\uD83D\uDD1F\uD83D\uDD74️\n ➕\uD83D\uDDBC",
                        description = "средняя, семейка, евро",
                        playerTurn = "player_turn 4",
                        effects = "effects 4",
                        isFavorites = false,
                        isLock = true,
                        priority = 4
                    )
                )
                return null
            }
        }

    }

}