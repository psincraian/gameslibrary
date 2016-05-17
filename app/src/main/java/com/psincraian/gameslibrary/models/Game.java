package com.psincraian.gameslibrary.models;

import com.orm.SugarRecord;

/**
 * Created by petrusqui on 17/05/16.
 */
public class Game extends SugarRecord {

    public String title;

    public Game(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
