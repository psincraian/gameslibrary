package com.psincraian.gameslibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by petrusqui on 17/05/16.
 */
public class Game extends SugarRecord implements Parcelable {

    String title;
    String studio;

    public Game() {

    }

    public Game(String title, String studio) {
        this.title = title;
        this.studio = studio;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }


    protected Game(Parcel in) {
        title = in.readString();
        studio = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(studio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}