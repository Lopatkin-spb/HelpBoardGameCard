package space.lopatkin.spb.helpboardgamecard.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "helpcard_table")
public class Helpcard {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "victory_condition")
    private String victoryCondition;

    @ColumnInfo(name = "end_game")
    private String endGame;

    @ColumnInfo(name = "preparation")
    private String preparation;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "player_turn")
    private String playerTurn;

    @ColumnInfo(name = "effects")
    private String effects;

    @ColumnInfo(name = "favorites")
    private boolean favorites;

    @ColumnInfo(name = "lock")
    private boolean lock;

//    @ColumnInfo(name = "timestamp")
//    private long timestamp;

    @ColumnInfo(name = "priority")
    private int priority;




    public Helpcard() {
    }




    public Helpcard(String title, String victoryCondition,
                    String endGame, String preparation,
                    String description, String playerTurn,
                    String effects, boolean favorites, boolean lock, int priority) {
        this.title = title;
        this.victoryCondition = victoryCondition;
        this.endGame = endGame;
        this.preparation = preparation;
        this.description = description;
        this.playerTurn = playerTurn;
        this.effects = effects;
        this.favorites = favorites;
        this.lock = lock;

        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVictoryCondition() {
        return victoryCondition;
    }

    public void setVictoryCondition(String victoryCondition) {
        this.victoryCondition = victoryCondition;
    }

    public String getEndGame() {
        return endGame;
    }

    public void setEndGame(String endGame) {
        this.endGame = endGame;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

//parcelable
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Helpcard helpCard = (Helpcard) o;
//        return id == helpCard.id &&
//                timestamp == helpCard.timestamp &&
//                favorites == helpCard.favorites &&
//                Objects.equals(description, helpCard.description);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, description, timestamp, favorites);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(id);
//        parcel.writeString(description);
//        parcel.writeLong(timestamp);
//        parcel.writeByte((byte) (favorites ? 1 : 0));
//    }
//
//
//    protected Helpcard(Parcel in) {
//        id = in.readInt();
//        description = in.readString();
//        timestamp = in.readLong();
//        favorites = in.readByte() != 0;
//    }
//
//    public static final Creator<Helpcard> CREATOR = new Creator<Helpcard>() {
//        @Override
//        public Helpcard createFromParcel(Parcel in) {
//            return new Helpcard(in);
//        }
//
//        @Override
//        public Helpcard[] newArray(int size) {
//            return new Helpcard[size];
//        }
//    };
//



}
