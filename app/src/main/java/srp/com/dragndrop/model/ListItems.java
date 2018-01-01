package srp.com.dragndrop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Santosh on 12/31/2017.
 */

public class ListItems implements Parcelable{

    String title, content;

    public ListItems(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ListItems(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<ListItems> CREATOR = new Creator<ListItems>() {
        @Override
        public ListItems createFromParcel(Parcel in) {
            return new ListItems(in);
        }

        @Override
        public ListItems[] newArray(int size) {
            return new ListItems[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
    }
}
