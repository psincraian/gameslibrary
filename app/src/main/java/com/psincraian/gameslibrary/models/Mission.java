package com.psincraian.gameslibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by petrusqui on 6/06/16.
 */
public class Mission extends SugarRecord implements Parcelable {

    private String title;
    private String description;
    private int score;
    private int level;
    private boolean completed;
    private Game game;

    public Mission() {

    }

    public Mission(String title, String description, int score, int level, boolean completed) {
        this.title = title;
        this.description = description;
        this.score = score;
        this.completed = completed;
        this.level = level;
    }

    public Mission(Parcel in) {
        title = in.readString();
        description = in.readString();
        score = in.readInt();
        level = in.readInt();
        completed = in.readByte() != 0;
        game = in.readParcelable(Game.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeInt(score);
        parcel.writeInt(level);
        parcel.writeByte((byte) (completed ? 1 : 0));
        parcel.writeParcelable(game, 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Mission> CREATOR = new Parcelable.Creator<Mission>() {
        @Override
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        @Override
        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };
}
