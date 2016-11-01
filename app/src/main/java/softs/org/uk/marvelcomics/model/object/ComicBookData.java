package softs.org.uk.marvelcomics.model.object;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import softs.org.uk.marvelcomics.R;

/**
 * Created by Fernando Bonet on 30/10/2016.
 */
public class ComicBookData implements Parcelable {
    public int id;
    public String title;
    public String description;
    public Thumbnail thumbnail;
    public ArrayList<Image> images;
    public ArrayList<Price> prices;
    public Creators creators;
    public int pageCount;

    public String getThumbnailUrl(Context context) {
        return thumbnail.path + context.getString(R.string.image_size_path) + thumbnail.extension;
    }

    public String getImageUrl(Context context) {
        if (images.isEmpty()) {
            return null;
        }
        return images.get(0).path + context.getString(R.string.image_size_path) + images.get(0).extension;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.prices);
        dest.writeParcelable(this.creators, flags);
        dest.writeInt(this.pageCount);
    }

    public ComicBookData() {
    }

    protected ComicBookData(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.prices = in.createTypedArrayList(Price.CREATOR);
        this.creators = in.readParcelable(Creators.class.getClassLoader());
        this.pageCount = in.readInt();
    }

    public static final Parcelable.Creator<ComicBookData> CREATOR = new Parcelable.Creator<ComicBookData>() {
        @Override
        public ComicBookData createFromParcel(Parcel source) {
            return new ComicBookData(source);
        }

        @Override
        public ComicBookData[] newArray(int size) {
            return new ComicBookData[size];
        }
    };
}


