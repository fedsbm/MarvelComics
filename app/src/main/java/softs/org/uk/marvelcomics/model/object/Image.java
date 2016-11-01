package softs.org.uk.marvelcomics.model.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
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

    public Image() {
    }

    protected Image(Parcel in) {
        this.path = in.readString();
        this.extension = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
