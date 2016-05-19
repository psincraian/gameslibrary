package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.List;


public class GamesFragment extends Fragment implements MainActivity.MainActivityInterface {

    static final int ADD_GAME_REQUEST = 1;
    private ArrayAdapter<String> gamesAdapter;
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

        gamesAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, getGameNames()
        );

        ListView listView = (ListView) view.findViewById(R.id.listview_games);
        listView.setAdapter(gamesAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GAME_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String title = data.getStringExtra(AddGameActivity.INTENT_EXTRA_TITLE);
                gamesAdapter.add(title);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        startActivityForResult(intent, ADD_GAME_REQUEST);
    }

    private List<String> getGameNames() {
        List<Game> games = Game.listAll(Game.class);
        List<String> result = new ArrayList<>();

        for (int i = 0; i < games.size(); i++) {
            result.add(games.get(i).getTitle());
        }

        return result;
    }
}
