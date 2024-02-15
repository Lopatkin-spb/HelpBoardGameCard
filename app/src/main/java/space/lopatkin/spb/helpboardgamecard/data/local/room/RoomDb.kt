package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard

@Database(
    entities = [
        BoardgameInfo::class,
        Helpcard::class
    ], version = 37
)
abstract class RoomDb : RoomDatabase() {
    abstract fun boardgameDao(): BoardgameDao

    private class PopulateDbCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {

                    val boardgameDao: BoardgameDao = database.boardgameDao()

                    val example1: BoardgameRaw = BoardgameRaw(
                        name = "Title 1",
                        victoryCondition = "victory_condition 1",
                        endGame = "end_game 1",
                        preparation = "preparation 1",
                        description = "Description 1",
                        playerTurn = "player_turn 1",
                        effects = "effects 1",
                        favorite = false,
                        lock = true,
                        author = "example",
                        priority = 1
                    )
                    val boardgameInfo1: BoardgameInfo = example1.toBoardgameInfo()
                    val boardgameId1: Long = boardgameDao.add(boardgameInfo1)
                    val helpcard1: Helpcard = example1.toHelpcard(boardgameId1)
                    val helpcardId1: Long = boardgameDao.add(helpcard1)

                    val example2: BoardgameRaw = BoardgameRaw(
                        name = "Илос 3варик",
                        victoryCondition = "",
                        endGame = "",
                        preparation = "-#l0105#l0312#l0027\n-#l0027#l01081#l0031#l0102#l0006\n-#l0105#l0314#l0036\n-#l0313#l0015#l0101(#l0019+#l0018+10#l0005+5#l0007)/#l0117#l0305#l0015\n1)#l0107#l0306#l00362#l0031=\n(1:#l0031=4#l0032*#l0016)#l0102#l0006\n(2:#l0031)#l0115#l0017\n2) #l0031#l0036#l01023#l0204#l0035",
                        description = "безкризисная, управляемый рандом",
                        playerTurn = "",
                        effects = "",
                        favorite = false,
                        lock = true,
                        author = "example",
                        priority = 2
                    )
                    val boardgameInfo2: BoardgameInfo = example2.toBoardgameInfo()
                    val boardgameId2: Long = boardgameDao.add(boardgameInfo2)
                    val helpcard2: Helpcard = example2.toHelpcard(boardgameId2)
                    val helpcardId2: Long = boardgameDao.add(helpcard2)

                    val example3: BoardgameRaw = BoardgameRaw(
                        name = "Илос 1вар",
                        victoryCondition = "victory_condition 3",
                        endGame = "end_game 3",
                        preparation = "1) \uD83D\uDD03\uD83C\uDF9F️\uD83D\uDC49\uD83D\uDC655️⃣\uD83C\uDF9F️\uD83D\uDC64\n2) \uD83D\uDC655️⃣⛵\uD83D\uDC64\n3) \uD83D\uDC65\uD83D\uDD1F\uD83D\uDD74️\uD83D\uDC64\n4) \uD83D\uDD03\uD83E\uDDE9",
                        description = "евро, семейка",
                        playerTurn = "player_turn 3",
                        effects = "effects 3",
                        favorite = false,
                        lock = true,
                        author = "example",
                        priority = 3
                    )
                    val boardgameInfo3: BoardgameInfo = example3.toBoardgameInfo()
                    val boardgameId3: Long = boardgameDao.add(boardgameInfo3)
                    val helpcard3: Helpcard = example3.toHelpcard(boardgameId3)
                    val helpcardId3: Long = boardgameDao.add(helpcard3)

                    val example4: BoardgameRaw = BoardgameRaw(
                        name = "Илос 2вар",
                        victoryCondition = "victory_condition 4",
                        endGame = "end_game 4",
                        preparation = "1) \uD83D\uDD03\uD83C\uDF9F️➕\uD83E\uDDE9\n2) 4️⃣\uD83E\uDDE9✖️\uD83D\uDC65\n3) \uD83D\uDC655️⃣\uD83C\uDF9F️\n ➕5️⃣\uD83D\uDEA4\n ➕\uD83D\uDD1F\uD83D\uDD74️\n ➕\uD83D\uDDBC",
                        description = "средняя, семейка, евро",
                        playerTurn = "player_turn 4",
                        effects = "effects 4",
                        favorite = false,
                        lock = true,
                        author = "example",
                        priority = 4
                    )
                    val boardgameInfo4: BoardgameInfo = example4.toBoardgameInfo()
                    val boardgameId4: Long = boardgameDao.add(boardgameInfo4)
                    val helpcard4: Helpcard = example4.toHelpcard(boardgameId4)
                    val helpcardId4: Long = boardgameDao.add(helpcard4)
                }
            }
        }
    }

    companion object {
        private const val NAME_DATABASE = "room_boardgame_database"

        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): RoomDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    NAME_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PopulateDbCallback(scope))
                    .build()
                INSTANCE = instance
                instance // Return
            }
        }
    }

}