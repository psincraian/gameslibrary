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

import com.psincraian.gameslibrary.adapters.CharactersAdapter;
import com.psincraian.gameslibrary.adapters.ObjectAdapter;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;
import com.psincraian.gameslibrary.models.Object;

import java.util.List;


public class ObjectsFragment extends Fragment implements MainActivity.MainActivityInterface, ObjectAdapter.OnCharacterClick, SortByDialog.OnItemClick, ConfirmationDialog.ConfirmationInterface {

    public static final int ADD_CHARACTER_REQUEST = 1;
    public static final int EDIT_CHARACTER_REQUESt = 2;
    public static final String EXTRA_GAME = "extra_game_id";

    private static final String CLASS_NAME = ObjectsFragment.class.getName();
    private static final String SORT_BY_NAME = "Name";
    private static final String SORT_BY_LEVEL = "Level";

    private ObjectAdapter charactersAdapter;
    private Game game;
    private int editPositionCharacter;
    private int selectedPosition;
    private Dialog dialog;

    public ObjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_objects, container, false);

        Bundle args = getArguments();
        if (args != null)
            game = args.getParcelable(EXTRA_GAME);
        else
            game = null;

        charactersAdapter = new ObjectAdapter(getCharactersNames(), this);

        RecyclerView listView = (RecyclerView) view.findViewById(R.id.listview_characters);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(charactersAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_characters_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sort_by:
                String[] items = getResources().getStringArray(R.array.sort_by);
                SortByDialog dialog = new SortByDialog(getActivity(), items, this);
                dialog.onCreateDialog(null).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CHARACTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Object character = data.getParcelableExtra(AddObjectActivity.INTENT_EXTRA_CHARACTER);
                charactersAdapter.add(character);
            }
        } else if (requestCode == EDIT_CHARACTER_REQUESt) {
            if (resultCode == Activity.RESULT_OK) {
                Object character = data.getParcelableExtra(AddObjectActivity.INTENT_EXTRA_CHARACTER);
                charactersAdapter.edit(character, editPositionCharacter);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddObjectActivity.class);
        Log.v(CLASS_NAME, "FLOAT PRESSED");
        if (game != null)
            intent.putExtra(AddObjectActivity.INTENT_EXTRA_GAME_NAME, game.getTitle());
        startActivityForResult(intent, ADD_CHARACTER_REQUEST);
    }

    private List<Object> getCharactersNames() {
        List<Object> characters;

        if (game == null)
            characters = Object.find(Object.class, "");
        else
            characters = Object.find(Object.class, "game = ?",
                    Long.toString(game.getId()));

        return characters;
    }

    @Override
    public void onCharacterClick(int position, Object character) {
        editPositionCharacter = position;
        Intent intent = new Intent(getActivity(), AddObjectActivity.class);
        intent.putExtra(AddObjectActivity.INTENT_EXTRA_CHARACTER, character);
        startActivityForResult(intent, EDIT_CHARACTER_REQUESt);
    }

    @Override
    public void onCharacterLongClick(int position, Object character) {
        selectedPosition = position;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(getActivity(), this);
        dialog = confirmationDialog.onCreateDialog(null);
        dialog.show();
    }

    @Override
    public void onItemClick(String item) {
        switch (item) {
            case SORT_BY_NAME:
                charactersAdapter.orderBy(ObjectAdapter.ORDER_BY_NAME);
                break;
            case SORT_BY_LEVEL:
                charactersAdapter.orderBy(ObjectAdapter.ORDER_BY_LEVEL);
                break;
        }
    }

    @Override
    public void yes() {
        Object object = charactersAdapter.get(selectedPosition);
        object.delete();
        charactersAdapter.remove(selectedPosition);
    }

    @Override
    public void cancel() {
        dialog.dismiss();
    }
}
