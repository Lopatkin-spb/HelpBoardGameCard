package space.lopatkin.spb.helpboardgamecard.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "helpcard_table")
public class Helpcard implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "victory_condition")
    private String victoryCondition;
    @ColumnInfo(name = "end_game")
    private String endGame;
    @ColumnInfo(name = "preparation")
    private String preparation;
    @ColumnInfo(name = "player_turn")
    private String playerTurn;
    @ColumnInfo(name = "effects")
    private String effects;
    @ColumnInfo(name = "favorites")
    private boolean favorites;
    @ColumnInfo(name = "lock")
    private boolean lock;
    @ColumnInfo(name = "priority")
    private int priority;

    //конструктор (если рум есть - остальные конструкторы должны быть подписаны игнором)
    public Helpcard(String title, String victoryCondition,
                    String endGame, String preparation,
                    String description, String playerTurn,
                    String effects, boolean favorites,
                    boolean lock, int priority) {
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

    //игнор для рума только, чтобы игнорировала его (во избежание ошибок)
    @Ignore
    public Helpcard(
            int id, String title, String victoryCondition,
            String endGame, String preparation,
            String description, String playerTurn,
            String effects, boolean favorites, boolean lock, int priority) {
        this.id = id;
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

    //для парселя
    protected Helpcard(Parcel in) {
        id = in.readInt();
        title = in.readString();
        victoryCondition = in.readString();
        endGame = in.readString();
        preparation = in.readString();
        description = in.readString();
        playerTurn = in.readString();
        effects = in.readString();
        favorites = in.readByte() != 0;
        lock = in.readByte() != 0;
        priority = in.readInt();
    }

    //для парселя
    public static final Creator<Helpcard> CREATOR = new Creator<Helpcard>() {
        @Override
        public Helpcard createFromParcel(Parcel in) {
            return new Helpcard(in);
        }

        @Override
        public Helpcard[] newArray(int size) {
            return new Helpcard[size];
        }
    };

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

    @Override
    public String toString() {
        return "Helpcard{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", victoryCondition='" + victoryCondition + '\'' +
                ", endGame='" + endGame + '\'' +
                ", preparation='" + preparation + '\'' +
                ", description='" + description + '\'' +
                ", playerTurn='" + playerTurn + '\'' +
                ", effects='" + effects + '\'' +
                ", favorites=" + favorites +
                ", lock=" + lock +
                ", priority=" + priority +
                '}';
    }

    //для парселя
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(victoryCondition);
        parcel.writeString(endGame);
        parcel.writeString(preparation);
        parcel.writeString(description);
        parcel.writeString(playerTurn);
        parcel.writeString(effects);
        parcel.writeByte((byte) (favorites ? 1 : 0));
        parcel.writeByte((byte) (lock ? 1 : 0));
        parcel.writeInt(priority);
    }

    //для парселя
    @Override
    public int describeContents() {
        return 0;
    }

}
