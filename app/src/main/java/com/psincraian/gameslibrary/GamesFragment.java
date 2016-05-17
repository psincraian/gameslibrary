package com.psincraian.gameslibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GamesFragment extends Fragment implements MainActivity.MainActivityInterface {

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
                getContext(), android.R.layout.simple_list_item_1, new String[]{"Game 1", "Game 2", "Game 3"}
        );

        ListView listView = (ListView) view.findViewById(R.id.listview_games);
        listView.setAdapter(gamesAdapter);

        return view;
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddGameActivity.class);
        startActivity(intent);
    }
}
