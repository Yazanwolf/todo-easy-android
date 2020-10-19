package com.android.todoeasy;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Todo implements Parcelable {

    private final String name;
    private final String expiryDate;
    private final String expiryTime;
    private final boolean finished;
    private final String description;

    public Todo(String name, String expiryDate, String expiryTime, boolean finished, String description) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.expiryTime = expiryTime;
        this.finished = finished;
        this.description = description;
    }

    protected Todo(Parcel in) {
        name = in.readString();
        expiryDate = in.readString();
        expiryTime = in.readString();
        finished = in.readByte() != 0;
        description = in.readString();
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(expiryDate);
        dest.writeString(expiryTime);
        dest.writeByte((byte) (finished ? 1 : 0));
        dest.writeString(description);
    }
}


