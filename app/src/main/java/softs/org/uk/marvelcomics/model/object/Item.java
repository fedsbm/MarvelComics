package softs.org.uk.marvelcomics.model.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public String name;
    public String role;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.role);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.name = in.readString();
        this.role = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
