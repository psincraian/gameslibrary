package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.List;


public class CharactersFragment extends Fragment implements MainActivity.MainActivityInterface {

    public static final int ADD_CHARACTER_REQUEST = 1;
    public static final String EXTRA_GAME = "extra_game_id";
    private static final String CLASS_NAME = CharactersFragment.class.getName();

    private ArrayAdapter<String> charactersAdapter;
    private Game game;

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

        Bundle args = getArguments();
        if (args != null)
            game = args.getParcelable(EXTRA_GAME);
        else
            game = null;

        charactersAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, getCharactersNames()
        );

        ListView listView = (ListView) view.findViewById(R.id.listview_characters);
        listView.setAdapter(charactersAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CHARACTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra(AddCharactersActivity.INTENT_EXTRA_CHARACTER_NAME);
                charactersAdapter.add(name);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddCharactersActivity.class);
        if (game != null)
            intent.putExtra(AddCharactersActivity.INTENT_EXTRA_GAME_NAME, game.getTitle());
        startActivityForResult(intent, ADD_CHARACTER_REQUEST);
    }

    private List<String> getCharactersNames() {
        List<Character> characters = null;

        if (game == null)
            characters = Character.listAll(Character.class);
        else
            characters = Character.find(Character.class, "game = ?", Long.toString(game.getId()));

        List<String> result = new ArrayList<>();

        for (int i = 0; i < characters.size(); i++) {
            result.add(characters.get(i).getName());
        }

        return result;
    }
}
