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

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "favorites")
    private boolean favorites;

//    @ColumnInfo(name = "timestamp")
//    private long timestamp;

    @ColumnInfo(name = "priority")
    private int priority;




    public Helpcard() {
    }



    public Helpcard(String title, String description, boolean favorites,  int priority) {
        this.title = title;
        this.description = description;
        this.favorites = favorites;
//        this.timestamp = timestamp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

//    public long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(long timestamp) {
//        this.timestamp = timestamp;
//    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
