package softs.org.uk.marvelcomics.model.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Creators implements Parcelable {
    public ArrayList<Item> items;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.items);
    }

    public Creators() {
    }

    protected Creators(Parcel in) {
        this.items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<Creators> CREATOR = new Creator<Creators>() {
        @Override
        public Creators createFromParcel(Parcel source) {
            return new Creators(source);
        }

        @Override
        public Creators[] newArray(int size) {
            return new Creators[size];
        }
    };
}
