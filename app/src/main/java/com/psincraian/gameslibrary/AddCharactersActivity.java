package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.orm.SugarContext;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddCharactersActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_TITLE = "title";
    EditText characterName;
    AutoCompleteTextView characterStudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        characterName = (EditText) findViewById(R.id.input_character_name);
        characterStudio = (AutoCompleteTextView) findViewById(R.id.input_character_game_title);
        ArrayAdapter<String> studioAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getStudioNames()
        );

        characterStudio.setAdapter(studioAdapter);
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
        String studio = characterStudio.getText().toString();

        Game game = new Game(title, studio);
        game.save();
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_TITLE, game.getTitle());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private List<String> getStudioNames() {
        List<Game> games = Game.listAll(Game.class);
        Set<String> names = new HashSet<>();

        for (int i = 0; i < games.size(); i++) {
            names.add(games.get(i).getStudio());
        }

        return new ArrayList<>(names);
    }
}
