package com.psincraian.gameslibrary.models;

import com.orm.SugarRecord;

/**
 * Created by petrusqui on 17/05/16.
 */
public class Game extends SugarRecord {

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

}
