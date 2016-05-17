package com.psincraian.gameslibrary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GamesFragment extends Fragment {

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
}
