package com.psincraian.gameslibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.orm.SugarContext;
import com.psincraian.gameslibrary.models.Game;

public class AddGameActivity extends AppCompatActivity {

    EditText gameTitle;
    EditText gameStudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SugarContext.init(this);

        gameTitle = (EditText) findViewById(R.id.input_game_title);
        gameStudio = (EditText) findViewById(R.id.input_game_studio);
    }

    public void save(View view) {
        String title = gameTitle.getText().toString();
        String studio = gameStudio.getText().toString();

        Game game = new Game(title, studio);
        game.save();
        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }
}
