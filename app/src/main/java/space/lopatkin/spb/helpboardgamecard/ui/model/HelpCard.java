package space.lopatkin.spb.helpboardgamecard.ui.model;


import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class HelpCard implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uniqueId;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @ColumnInfo(name = "favorites")
    public boolean favorites;

    public HelpCard() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpCard helpCard = (HelpCard) o;
        return uniqueId == helpCard.uniqueId &&
                timestamp == helpCard.timestamp &&
                favorites == helpCard.favorites &&
                Objects.equals(text, helpCard.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, text, timestamp, favorites);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uniqueId);
        parcel.writeString(text);
        parcel.writeLong(timestamp);
        parcel.writeByte((byte) (favorites ? 1 : 0));
    }


    protected HelpCard(Parcel in) {
        uniqueId = in.readInt();
        text = in.readString();
        timestamp = in.readLong();
        favorites = in.readByte() != 0;
    }

    public static final Creator<HelpCard> CREATOR = new Creator<HelpCard>() {
        @Override
        public HelpCard createFromParcel(Parcel in) {
            return new HelpCard(in);
        }

        @Override
        public HelpCard[] newArray(int size) {
            return new HelpCard[size];
        }
    };

}
