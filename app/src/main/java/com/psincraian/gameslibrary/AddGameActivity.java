package com.psincraian.gameslibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.orm.SugarContext;
import com.psincraian.gameslibrary.models.Game;

public class AddGameActivity extends AppCompatActivity implements ConfirmationDialog.ConfirmationInterface {

    private static final String CLASS_NAME = AddGameActivity.class.getSimpleName();
    public static final int RESULT_GAME_DELETE = 100;
    public static final String INTENT_EXTRA_GAME = "game";
    EditText gameTitle;
    EditText gameStudio;
    TextInputLayout layoutGameTitle;
    TextInputLayout layoutGameStudio;
    Switch gamePlaying;
    Game game;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutGameTitle = (TextInputLayout) findViewById(R.id.input_layout_game_title);
        layoutGameStudio = (TextInputLayout) findViewById(R.id.input_layout_game_studio);
        gameTitle = (EditText) findViewById(R.id.input_game_title);
        gameStudio = (EditText) findViewById(R.id.input_game_studio);
        gamePlaying = (Switch) findViewById(R.id.playing);

        gameTitle.addTextChangedListener(new TextValidator(gameTitle));
        gameStudio.addTextChangedListener(new TextValidator(gameStudio));

        Intent arguments = getIntent();
        if (arguments != null && arguments.hasExtra(INTENT_EXTRA_GAME)) {
            game = arguments.getParcelableExtra(INTENT_EXTRA_GAME);
            loadGameData();
            (findViewById(R.id.button_delete)).setVisibility(View.VISIBLE);
        } else
            game = new Game();
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
        if (!validData())
            return;

        String title = gameTitle.getText().toString();
        String studio = gameStudio.getText().toString();
        boolean playing = gamePlaying.isChecked();

        game.setTitle(title);
        game.setStudio(studio);
        game.setPlaying(playing);
        game.save();
        Log.i(CLASS_NAME, "(1) Is playing: " + game.getPlaying());
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_GAME, game);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void loadGameData() {
        gameTitle.setText(game.getTitle());
        gameStudio.setText(game.getStudio());
        Log.i(CLASS_NAME, "INIT - is playing: " + game.getPlaying());
        gamePlaying.setChecked(game.getPlaying());
    }

    private boolean validData() {
        return validateGameName() &&
                validateGameStudio();
    }

    private boolean validateGameName() {
        String newTitle = gameTitle.getText().toString();
        long count;

        if (newTitle.trim().isEmpty()) {
            layoutGameTitle.setError(getString(R.string.error_game_title_empty));
            requestFocus(gameTitle);
            return false;
        } else {
            layoutGameTitle.setError(null);
            layoutGameTitle.setErrorEnabled(false);
        }

        if (game.getTitle() != null && game.getTitle().equals(newTitle))
            count = 0;
        else
            count = Game.count(Game.class, "title = ?", new String[] {newTitle});

        if (count > 0) {
            layoutGameTitle.setError(getString(R.string.error_game_title_exists));
            requestFocus(gameTitle);
            return false;
        } else {
            layoutGameTitle.setError(null);
            layoutGameTitle.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateGameStudio() {
        if (gameStudio.getText().toString().trim().isEmpty()) {
            layoutGameStudio.setError(getString(R.string.error_game_studio_empty));
            requestFocus(gameStudio);
            return false;
        } else {
            layoutGameStudio.setError(null);
            layoutGameStudio.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void delete(View view) {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(this, this);
        dialog = confirmationDialog.onCreateDialog(null);
        dialog.show();
    }

    @Override
    public void yes() {
        game.delete();
        setResult(RESULT_GAME_DELETE);
        finish();
    }

    @Override
    public void cancel() {
        dialog.dismiss();
    }

    private class TextValidator implements TextWatcher {

        private View view;

        public TextValidator(View view) {
            this.view = view;
            Log.i(CLASS_NAME, "TextValidator contructor with " + view.getTransitionName());
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_game_title:
                    validateGameName();
                    break;
                case R.id.input_game_studio:
                    validateGameStudio();
                    break;
            }
        }
    }
}
