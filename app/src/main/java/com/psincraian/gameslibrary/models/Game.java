package com.psincraian.gameslibrary.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * Created by petrusqui on 17/05/16.
 */
public class Game extends SugarRecord implements Parcelable {
    @Unique
    private String title;
    private String studio;
    private boolean playing;

    public Game() {
    }

    public Game(String title, String studio, boolean playing) {
        this.title = title;
        this.studio = studio;
        this.playing = playing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    @Override
    public boolean delete() {
        List<Character> characters = getCharacters();
        for (Character c : characters)
            c.delete();

        return super.delete();
    }

    protected Game(Parcel in) {
        setId(in.readLong());
        title = in.readString();
        studio = in.readString();
        playing = in.readByte() != 0;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean getPlaying() {
        return playing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(title);
        dest.writeString(studio);
        dest.writeByte((byte) (playing ? 1 : 0));
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

    public List<Character> getCharacters() {
        if (getId() == null)
            return null;

        return Character.find(Character.class, "game = ?", getId().toString());
    }
}