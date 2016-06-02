package com.psincraian.gameslibrary.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.orm.SugarRecord;

/**
 * Created by petrusqui on 20/05/16.
 */
public class Character extends SugarRecord implements Parcelable{

    private static final String CLASS_NAME = Character.class.getSimpleName();

    String name;
    String race;
    int level;
    Game game;

    public Character() {

    }

    public Character(String name, String race, int level, Game game) {
        this.name = name;
        this.race = race;
        this.level = level;
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected Character(Parcel in) {
        setId(in.readLong());
        name = in.readString();
        race = in.readString();
        level = in.readInt();
        game = in.readParcelable(Game.class.getClassLoader());
        Log.d(CLASS_NAME, "ID: " + getId());
        Log.d(CLASS_NAME, "NAME: " + name);
        Log.d(CLASS_NAME, "LEVEL: " + level);
        Log.d(CLASS_NAME, "GAME: " + game.getTitle());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(name);
        dest.writeString(race);
        dest.writeInt(level);
        dest.writeParcelable(game, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}
