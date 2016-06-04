package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.psincraian.gameslibrary.adapters.GameViewHolder;
import com.psincraian.gameslibrary.adapters.GamesAdapter;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.List;


public class GamesFragment extends Fragment implements MainActivity.MainActivityInterface, GamesAdapter.OnGameClick {

    static final int ADD_GAME_REQUEST = 1;
    static final int EDIT_GAME_REQUEST = 2;
    private GamesAdapter gamesAdapter;
    private int editGamePosition;

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
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        gamesAdapter = new GamesAdapter(getGames(), this);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.listview_games);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(gamesAdapter);

        return view;
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
                gamesAdapter.set(editGamePosition, game);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        startActivityForResult(intent, ADD_GAME_REQUEST);
    }

    private List<Game> getGames() {
        List<Game> games = Game.find(Game.class, "deleted = ?", Integer.toString(0));
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
        game.delete();
        gamesAdapter.remove(position);
    }

    @Override
    public void onGameEditClick(int position, Game game) {
        editGamePosition = position;
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        intent.putExtra(AddGameActivity.INTENT_EXTRA_GAME, game);
        startActivityForResult(intent, EDIT_GAME_REQUEST);
    }
}
