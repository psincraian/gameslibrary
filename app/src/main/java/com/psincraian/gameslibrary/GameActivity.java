package com.psincraian.gameslibrary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.psincraian.gameslibrary.models.Game;
import com.psincraian.gameslibrary.models.Object;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_GAME = "extra_game";
    private static final String CLASS_NAME = GameActivity.class.getName();

    private Game game;
    private MainActivity.MainActivityInterface characters;
    private MainActivity.MainActivityInterface objects;
    private MainActivity.MainActivityInterface missions;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_GAME)) {
            game = intent.getParcelableExtra(EXTRA_GAME);
            setTitle(game.getTitle());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), game);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = mViewPager.getCurrentItem();
                if (id == 0)
                    characters.addPressed();
                else if (id == 1)
                    objects.addPressed();
                else
                    missions.addPressed();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Game game;
        public SectionsPagerAdapter(FragmentManager fm, Game game) {
            super(fm);
            this.game = game;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args;
            switch (position) {
                case 0:
                    Log.i(CLASS_NAME, "Case 0");
                    CharactersFragment charactersFragment = new CharactersFragment();
                    characters = charactersFragment;
                    args = new Bundle();
                    args.putParcelable(CharactersFragment.EXTRA_GAME, game);
                    charactersFragment.setArguments(args);
                    return charactersFragment;
                case 1:
                    Log.i(CLASS_NAME, "Case 1");
                    ObjectsFragment objectsFragment = new ObjectsFragment();
                    objects = objectsFragment;
                    args = new Bundle();
                    args.putParcelable(ObjectsFragment.EXTRA_GAME, game);
                    objectsFragment.setArguments(args);
                    return objectsFragment;
                case 2:
                    Log.i(CLASS_NAME, "Case 2");
                    MissionFragment fragment = new MissionFragment();
                    missions = fragment;
                    args = new Bundle();
                    args.putParcelable(MissionFragment.EXTRA_GAME, game);
                    fragment.setArguments(args);
                    return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getText(R.string.characters);
                case 1:
                    return getText(R.string.objects);
                case 2:
                    return getText(R.string.missions);
            }
            return null;
        }
    }
}
