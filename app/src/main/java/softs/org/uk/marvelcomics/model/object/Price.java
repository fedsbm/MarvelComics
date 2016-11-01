package softs.org.uk.marvelcomics.model.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {
    public String type;
    public float price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeFloat(this.price);
    }

    public Price() {
    }

    protected Price(Parcel in) {
        this.type = in.readString();
        this.price = in.readFloat();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
