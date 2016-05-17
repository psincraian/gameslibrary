package com.psincraian.gameslibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CharactersFragment extends Fragment implements MainActivity.MainActivityInterface {

    private ArrayAdapter<String> charactersAdapter;

    public CharactersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_characters, container, false);

        charactersAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, new String[]{"Character 1", "Character 2", "Character 3"}
        );

        ListView listView = (ListView) view.findViewById(R.id.listview_characters);
        listView.setAdapter(charactersAdapter);

        return view;
    }

    // TODO
    @Override
    public void addPressed() {
        Log.e("Implement this method");
    }
}
