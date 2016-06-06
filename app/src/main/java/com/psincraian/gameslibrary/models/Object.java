package com.psincraian.gameslibrary.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.io.ByteArrayOutputStream;

/**
 * Created by petrusqui on 20/05/16.
 */
public class Object extends SugarRecord implements Parcelable{

    private static final String CLASS_NAME = Object.class.getSimpleName();

    private String name;
    private int level;
    private Game game;
    private byte[] image;

    public Object() {
    }

    public Object(String name, String race, int level, Game game, Bitmap avatar) {
        this.name = name;
        this.level = level;
        this.game = game;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.WEBP, 90, stream);
        this.image = stream.toByteArray();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void setImage(Bitmap avatar) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.WEBP, 90, stream);
        this.image = stream.toByteArray();
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

    protected Object(Parcel in) {
        setId(in.readLong());
        name = in.readString();
        level = in.readInt();
        image = new byte[in.readInt()];
        in.readByteArray(image);
        game = in.readParcelable(Game.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(name);
        dest.writeInt(level);
        dest.writeInt(image.length);
        dest.writeByteArray(image);
        dest.writeParcelable(game, 0);
    }

    @SuppressWarnings("unused")
    public static final Creator<Object> CREATOR = new Creator<Object>() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new Object(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };
}
