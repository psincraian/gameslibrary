package com.psincraian.gameslibrary.models;

import com.orm.SugarRecord;

/**
 * Created by petrusqui on 20/05/16.
 */
public class Character extends SugarRecord {

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
}
