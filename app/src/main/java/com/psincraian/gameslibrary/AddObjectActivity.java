package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;
import com.psincraian.gameslibrary.models.Object;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddObjectActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INTENT_EXTRA_CHARACTER = "extra_character";
    public static final String INTENT_EXTRA_GAME_NAME = "extra_game";
    public static final int PICK_PHOTO_FOR_AVATAR = 2;

    EditText characterName;
    EditText characterLevel;
    ImageButton imageButton;
    Button delete;
    Bitmap avatar;
    AutoCompleteTextView characterGame;
    Object character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_settings_black_24dp);
        imageButton = (ImageButton) findViewById(R.id.avatar);
        imageButton.setImageBitmap(avatar);
        characterName = (EditText) findViewById(R.id.input_character_name);
        characterLevel = (EditText) findViewById(R.id.input_character_level);
        characterGame = (AutoCompleteTextView) findViewById(R.id.input_character_game_title);
        delete = (Button) findViewById(R.id.button_delete);
        ArrayAdapter<String> studioAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getGameTitles()
        );

        imageButton.setOnClickListener(this);

        Intent arguments = getIntent();
        if (arguments != null && arguments.hasExtra(INTENT_EXTRA_GAME_NAME))
            characterGame.setText(arguments.getStringExtra(INTENT_EXTRA_GAME_NAME));
        if (arguments != null && arguments.hasExtra(INTENT_EXTRA_CHARACTER)) {
            character = arguments.getParcelableExtra(INTENT_EXTRA_CHARACTER);
            fillCharacterData();
            delete.setVisibility(View.VISIBLE);
        } else
            character = new Object();

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
        String gameTitle = characterGame.getText().toString();
        Game game = Game.find(Game.class, "title = ?", gameTitle).get(0);

        character.setName(title);
        character.setLevel(level);
        character.setGame(game);
        character.setImage(avatar);
        character.save();
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_CHARACTER, character);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                avatar = BitmapFactory.decodeStream(inputStream);
                imageButton.setImageBitmap(avatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
                break;
        }
    }

    private void fillCharacterData() {
        imageButton.setImageBitmap(character.getImage());
        characterName.setText(character.getName());
        characterLevel.setText(Integer.toString(character.getLevel()));
        characterGame.setText(character.getGame().getTitle());
    }
}
