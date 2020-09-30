package com.android.todoeasy.parcelable;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class TodoParcelable implements Parcelable {

    private String taskName;
    private String date;
    private String time;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected TodoParcelable(Parcel in) {

        taskName = in.readString();
        date = in.readString();
        time = in.readString();

    }

    public TodoParcelable(String name, String date, String time) {
        this.taskName = name;
        this.date = date;
        this.time = time;
    }


    public static final Creator<TodoParcelable> CREATOR = new Creator<TodoParcelable>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public TodoParcelable createFromParcel(Parcel in) {
            return new TodoParcelable(in);
        }

        @Override
        public TodoParcelable[] newArray(int size) {
            return new TodoParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static Creator<TodoParcelable> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(taskName);
        parcel.writeString(date);
        parcel.writeString(time);
    }
}