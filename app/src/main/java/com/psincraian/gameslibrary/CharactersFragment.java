package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.psincraian.gameslibrary.adapters.CharactersAdapter;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.List;


public class CharactersFragment extends Fragment implements MainActivity.MainActivityInterface, CharactersAdapter.OnCharacterClick {

    public static final int ADD_CHARACTER_REQUEST = 1;
    public static final int EDIT_CHARACTER_REQUESt = 2;

    public static final String EXTRA_GAME = "extra_game_id";
    private static final String CLASS_NAME = CharactersFragment.class.getName();

    private CharactersAdapter charactersAdapter;
    private Game game;
    private int editPositionCharacter;

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
        View view = inflater.inflate(R.layout.fragment_characters, container, false);

        Bundle args = getArguments();
        if (args != null)
            game = args.getParcelable(EXTRA_GAME);
        else
            game = null;

        charactersAdapter = new CharactersAdapter(getCharactersNames(), this);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.listview_characters);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(charactersAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CHARACTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Character character = data.getParcelableExtra(AddCharactersActivity.INTENT_EXTRA_CHARACTER);
                charactersAdapter.add(character);
            }
        } else if (requestCode == EDIT_CHARACTER_REQUESt) {
            if (resultCode == Activity.RESULT_OK) {
                Character character = data.getParcelableExtra(AddCharactersActivity.INTENT_EXTRA_CHARACTER);
                charactersAdapter.edit(character, editPositionCharacter);
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

    private List<Character> getCharactersNames() {
        List<Character> characters;

        if (game == null)
            characters = Character.find(Character.class, "deleted = ?", Integer.toString(0));
        else
            characters = Character.find(Character.class, "game = ? and deleted = ?",
                    Long.toString(game.getId()),
                    Integer.toString(0));

        return characters;
    }

    @Override
    public void onCharacterClick(int position, Character character) {
        editPositionCharacter = position;
        Intent intent = new Intent(getActivity(), AddCharactersActivity.class);
        intent.putExtra(AddCharactersActivity.INTENT_EXTRA_CHARACTER, character);
        startActivityForResult(intent, EDIT_CHARACTER_REQUESt);
    }

    @Override
    public void onCharacterLongClick(int position, Character character) {
        character.delete();
        charactersAdapter.remove(position);
    }
}
