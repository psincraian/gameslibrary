package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddCharactersActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_CHARACTER_NAME = "name";
    EditText characterName;
    EditText characterLevel;
    EditText characterRace;
    AutoCompleteTextView characterGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        characterName = (EditText) findViewById(R.id.input_character_name);
        characterLevel = (EditText) findViewById(R.id.input_character_level);
        characterRace = (EditText) findViewById(R.id.input_character_race);
        characterGame = (AutoCompleteTextView) findViewById(R.id.input_character_game_title);
        ArrayAdapter<String> studioAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getGameTitles()
        );

        characterGame.setAdapter(studioAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save(View view) {
        String title = characterName.getText().toString();
        int level = Integer.parseInt(characterLevel.getText().toString());
        String race = characterRace.getText().toString();
        String gameTitle = characterGame.getText().toString();
        Game game = Game.find(Game.class, "title = ?", gameTitle).get(0);

        Character character = new Character(title, race, level, game);
        character.save();
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_CHARACTER_NAME, character.getName());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private List<String> getGameTitles() {
        List<Game> games = Game.listAll(Game.class);
        Set<String> names = new HashSet<>();

        for (int i = 0; i < games.size(); i++) {
            names.add(games.get(i).getTitle());
        }

        return new ArrayList<>(names);
    }
}
