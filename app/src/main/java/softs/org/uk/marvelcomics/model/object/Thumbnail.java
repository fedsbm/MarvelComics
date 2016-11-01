package softs.org.uk.marvelcomics.model.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Thumbnail implements Parcelable {
    public String path;
    public String extension;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.extension);
    }

    public Thumbnail() {
    }

    protected Thumbnail(Parcel in) {
        this.path = in.readString();
        this.extension = in.readString();
    }

    public static final Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel source) {
            return new Thumbnail(source);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };
}
