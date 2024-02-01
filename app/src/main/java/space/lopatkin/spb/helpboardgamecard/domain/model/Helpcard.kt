package space.lopatkin.spb.helpboardgamecard.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "helpcard_table")
data class Helpcard(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "victory_condition")
    var victoryCondition: String?,
    @ColumnInfo(name = "end_game")
    var endGame: String?,
    @ColumnInfo(name = "preparation")
    var preparation: String?,
    @ColumnInfo(name = "player_turn")
    var playerTurn: String?,
    @ColumnInfo(name = "effects")
    var effects: String?,
    @ColumnInfo(name = "favorites")
    var isFavorites: Boolean?,
    @ColumnInfo(name = "lock")
    var isLock: Boolean?,
    @ColumnInfo(name = "priority")
    var priority: Int?
) : Parcelable {

    @Ignore
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(victoryCondition)
        parcel.writeString(endGame)
        parcel.writeString(preparation)
        parcel.writeString(playerTurn)
        parcel.writeString(effects)
        parcel.writeValue(isFavorites)
        parcel.writeValue(isLock)
        parcel.writeValue(priority)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Helpcard> {
        override fun createFromParcel(parcel: Parcel): Helpcard {
            return Helpcard(parcel)
        }

        override fun newArray(size: Int): Array<Helpcard?> {
            return arrayOfNulls(size)
        }
    }

}
