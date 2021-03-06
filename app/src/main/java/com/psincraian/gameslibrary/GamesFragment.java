package com.psincraian.gameslibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.psincraian.gameslibrary.adapters.GamesAdapter;
import com.psincraian.gameslibrary.models.Game;

import java.util.List;


public class GamesFragment extends Fragment implements MainActivity.MainActivityInterface,
        GamesAdapter.OnGameClick,
        FilterByDialog.OnItemClick,
        ConfirmationDialog.ConfirmationInterface{

    static final int ADD_GAME_REQUEST = 1;
    static final int EDIT_GAME_REQUEST = 2;
    private static final String FILTER_PLAYING = "Playing";
    private static final String CLASS_NAME = GamesFragment.class.getSimpleName();
    private GamesAdapter gamesAdapter;
    private int selectedGame;
    private Dialog dialog;

    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        gamesAdapter = new GamesAdapter(getGames(), this);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.listview_games);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(gamesAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_games_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.filter_by:
                String[] items = getResources().getStringArray(R.array.filter_by);
                boolean[] checked = new boolean[] {gamesAdapter.isFilteredByPlaying()};
                FilterByDialog dialog = new FilterByDialog(getActivity(), items, checked, this);
                this.dialog = dialog.onCreateDialog(null);
                this.dialog.show();
                break;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GAME_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Game game = data.getParcelableExtra(AddGameActivity.INTENT_EXTRA_GAME);
                gamesAdapter.add(game);
            }
        } else if (requestCode == EDIT_GAME_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Game game = data.getParcelableExtra(AddGameActivity.INTENT_EXTRA_GAME);
                gamesAdapter.set(selectedGame, game);
            } else if (resultCode == AddGameActivity.RESULT_GAME_DELETE) {
                gamesAdapter.remove(selectedGame);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        startActivityForResult(intent, ADD_GAME_REQUEST);
    }

    private List<Game> getGames() {
        List<Game> games = Game.find(Game.class, "");
        return games;
    }

    @Override
    public void onGameClick(Game game) {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(GameActivity.EXTRA_GAME, game);
        startActivity(intent);
    }

    @Override
    public void onGameLongClick(int position, Game game) {
        selectedGame = position;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(getActivity(), this);
        dialog = confirmationDialog.onCreateDialog(null);
        dialog.show();
    }

    @Override
    public void onGameEditClick(int position, Game game) {
        selectedGame = position;
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        intent.putExtra(AddGameActivity.INTENT_EXTRA_GAME, game);
        startActivityForResult(intent, EDIT_GAME_REQUEST);
    }

    @Override
    public void onItemClick(List<String> selected) {
        if (selected.contains(FILTER_PLAYING))
            gamesAdapter.filterByPlaying(true);
        else
            gamesAdapter.filterByPlaying(false);
    }

    @Override
    public void yes() {
        Game game = gamesAdapter.get(selectedGame);
        game.delete();
        gamesAdapter.remove(selectedGame);
    }

    @Override
    public void cancel() {
        dialog.dismiss();
    }
}
