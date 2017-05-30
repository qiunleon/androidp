package com.example.service.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图书类
 * Created by yunliangqiu on 2017/5/30.
 */
public class Book implements Parcelable {
    private String id;
    private String name;
    private String author;

    public Book(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.author);
    }

    public Book(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
