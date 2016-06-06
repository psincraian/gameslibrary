package com.psincraian.gameslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.psincraian.gameslibrary.adapters.CharactersAdapter;
import com.psincraian.gameslibrary.adapters.MissionAdapter;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;
import com.psincraian.gameslibrary.models.Mission;

import java.util.List;


public class MissionFragment extends Fragment implements MainActivity.MainActivityInterface, MissionAdapter.OnCharacterClick, SortByDialog.OnItemClick {

    public static final int ADD_MISSION_REQUEST = 1;
    public static final int EDIT_MISSION_REQUEST = 2;
    public static final String EXTRA_GAME = "extra_game_id";

    private static final String CLASS_NAME = MissionFragment.class.getName();
    private static final String SORT_BY_NAME = "Name";
    private static final String SORT_BY_LEVEL = "Level";

    private MissionAdapter charactersAdapter;
    private Game game;
    private int editPositionMission;

    public MissionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_characters, container, false);

        Bundle args = getArguments();
        if (args != null)
            game = args.getParcelable(EXTRA_GAME);
        else
            game = null;

        charactersAdapter = new MissionAdapter(getCharactersNames(), this);

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

        if (requestCode == ADD_MISSION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Mission character = data.getParcelableExtra(AddMissionActivity.INTENT_EXTRA_MISSION);
                charactersAdapter.add(character);
            }
        } else if (requestCode == EDIT_MISSION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Mission character = data.getParcelableExtra(AddMissionActivity.INTENT_EXTRA_MISSION);
                charactersAdapter.edit(character, editPositionMission);
            }
        }
    }

    @Override
    public void addPressed() {
        Intent intent = new Intent(getActivity(), AddMissionActivity.class);
        if (game != null)
            intent.putExtra(AddMissionActivity.INTENT_EXTRA_GAME_NAME, game.getTitle());
        startActivityForResult(intent, ADD_MISSION_REQUEST);
    }

    private List<Mission> getCharactersNames() {
        List<Mission> characters;

        if (game == null)
            characters = Character.find(Mission.class, "");
        else
            characters = Character.find(Mission.class, "game = ?",
                    Long.toString(game.getId()));

        return characters;
    }

    @Override
    public void onCharacterClick(int position, Mission character) {
        editPositionMission = position;
        Intent intent = new Intent(getActivity(), AddMissionActivity.class);
        intent.putExtra(AddMissionActivity.INTENT_EXTRA_MISSION, character);
        startActivityForResult(intent, EDIT_MISSION_REQUEST);
    }

    @Override
    public void onCharacterLongClick(int position, Mission character) {
        character.delete();
        charactersAdapter.remove(position);
    }

    @Override
    public void onItemClick(String item) {
        switch (item) {
            case SORT_BY_NAME:
                charactersAdapter.orderBy(CharactersAdapter.ORDER_BY_NAME);
                break;
            case SORT_BY_LEVEL:
                charactersAdapter.orderBy(CharactersAdapter.ORDER_BY_LEVEL);
                break;
        }
    }
}
