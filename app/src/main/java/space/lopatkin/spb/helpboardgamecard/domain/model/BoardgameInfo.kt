package space.lopatkin.spb.helpboardgamecard.domain.model

import androidx.room.*
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class BoardgameInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val boardgameId: Long?,
    @ColumnInfo(name = COLUMN_NAME)
    val boardgameName: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val boardgameDescription: String,
    @ColumnInfo(name = COLUMN_FAVORITE)
    var boardgameFavorite: Boolean,
    @ColumnInfo(name = COLUMN_LOCK)
    var boardgameLock: Boolean,
    @ColumnInfo(name = COLUMN_PRIORITY)
    val boardgamePriority: Int,
    @ColumnInfo(name = COLUMN_HELPCARD_AUTHOR)
    val helpcardAuthor: String
) {

    companion object {
        const val TABLE_NAME = "boardgame_info_table"
        const val COLUMN_ID = "boardgame_id"
        const val COLUMN_NAME = "boardgame_name"
        const val COLUMN_DESCRIPTION = "boardgame_description"
        const val COLUMN_FAVORITE = "boardgame_favorite"
        const val COLUMN_LOCK = "boardgame_lock"
        const val COLUMN_PRIORITY = "boardgame_priority"
        const val COLUMN_HELPCARD_AUTHOR = "helpcard_author"
    }

}

