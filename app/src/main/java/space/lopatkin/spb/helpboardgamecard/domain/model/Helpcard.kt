package space.lopatkin.spb.helpboardgamecard.domain.model

import androidx.room.*
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Helpcard(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val helpcardId: Long?,
    @ColumnInfo(name = COLUMN_BOARDGAME_ID)
    val boardgameId: Long,
    @ColumnInfo(name = "boardgame_name")
    val boardgameName: String,
    @ColumnInfo(name = COLUMN_AUTHOR)
    val helpcardAuthor: String,
    @ColumnInfo(name = COLUMN_VICTORY_COND)
    val helpcardVictoryCondition: String,
    @ColumnInfo(name = COLUMN_PLAYER_TURN)
    val helpcardPlayerTurn: String,
    @ColumnInfo(name = COLUMN_END_GAME)
    val helpcardEndGame: String,
    @ColumnInfo(name = COLUMN_EFFECTS)
    val helpcardEffects: String,
    @ColumnInfo(name = COLUMN_PREPARATION)
    val helpcardPreparation: String,
    @ColumnInfo(name = "boardgame_priority")
    val boardgamePriority: Int
) {

    companion object {
        const val TABLE_NAME = "helpcard_table"
        const val COLUMN_ID = "helpcard_id"
        const val COLUMN_BOARDGAME_ID = "boardgame_id"
        const val COLUMN_AUTHOR = "helpcard_author"
        const val COLUMN_VICTORY_COND = "helpcard_victory_condition"
        const val COLUMN_PLAYER_TURN = "helpcard_player_turn"
        const val COLUMN_END_GAME = "helpcard_end_game"
        const val COLUMN_EFFECTS = "helpcard_effects"
        const val COLUMN_PREPARATION = "helpcard_preparation"
    }

}

