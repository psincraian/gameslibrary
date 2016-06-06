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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.psincraian.gameslibrary.models.Game;
import com.psincraian.gameslibrary.models.Mission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddMissionActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_MISSION = "extra_character";
    public static final String INTENT_EXTRA_GAME_NAME = "extra_game";
    public static final int PICK_PHOTO_FOR_AVATAR = 2;

    EditText title;
    EditText description;
    EditText level;
    EditText score;
    Switch completed;
    Button delete;
    AutoCompleteTextView missionGame;
    Mission mission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.input_mission_title);
        description = (EditText) findViewById(R.id.input_mission_description);
        level = (EditText) findViewById(R.id.input_mission_level);
        score = (EditText) findViewById(R.id.input_mission_score);
        completed = (Switch) findViewById(R.id.completed);
        missionGame = (AutoCompleteTextView) findViewById(R.id.input_game_title);
        delete = (Button) findViewById(R.id.button_delete);
        ArrayAdapter<String> studioAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getGameTitles()
        );

        Intent arguments = getIntent();
        if (arguments != null && arguments.hasExtra(INTENT_EXTRA_GAME_NAME))
            missionGame.setText(arguments.getStringExtra(INTENT_EXTRA_GAME_NAME));
        if (arguments != null && arguments.hasExtra(INTENT_EXTRA_MISSION)) {
            mission = arguments.getParcelableExtra(INTENT_EXTRA_MISSION);
            fillMissionData();
            delete.setVisibility(View.VISIBLE);
        } else
            mission = new Mission();

        missionGame.setAdapter(studioAdapter);
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
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        int level = Integer.parseInt(this.level.getText().toString());
        int score = Integer.parseInt(this.score.getText().toString());
        boolean completed = this.completed.isChecked();
        String gameTitle = missionGame.getText().toString();
        Game game = Game.find(Game.class, "title = ?", gameTitle).get(0);

        mission.setTitle(title);
        mission.setLevel(level);
        mission.setDescription(description);
        mission.setScore(score);
        mission.setCompleted(completed);
        mission.setGame(game);
        mission.save();
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_MISSION, mission);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void delete(View view) {
        Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
    }

    private List<String> getGameTitles() {
        List<Game> games = Game.listAll(Game.class);
        Set<String> names = new HashSet<>();

        for (int i = 0; i < games.size(); i++) {
            names.add(games.get(i).getTitle());
        }

        return new ArrayList<>(names);
    }

    private void fillMissionData() {
        title.setText(mission.getTitle());
        description.setText(mission.getDescription());
        score.setText(Integer.toString(mission.getScore()));
        level.setText(Integer.toString(mission.getLevel()));
        completed.setChecked(mission.isCompleted());
        missionGame.setText(mission.getGame().getTitle());
    }
}
