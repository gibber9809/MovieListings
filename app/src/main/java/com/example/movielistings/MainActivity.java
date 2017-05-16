package com.example.movielistings;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Class handles all of the different fragments that are swapped between dynamically.
 * The class by default launches the main menu, which allows users to navigate to other fragments,
 * (or the one other Activity).
 */
public class MainActivity extends AppCompatActivity implements
        MainMenuFragment.mainMenuListener,
        EnterTitleFragment.enterTitleListener,
        StoreMoviesFragment.StoreMoviesFragmentListener,
        LoadFileListFragment.LoadFileListFragmentListener {
    private static final String BMENU_KEY = "MENU_KEY", BENTER_KEY = "ENTER_KEY", BLOAD_KEY = "LOAD_KEY",
    BSTORE_KEY = "STORE_KEY", MOVIE_LIST_KEY = "MOVIE_LIST_KEY", FILE_SELECTED_KEY = "FILE_SELECTED_KEY";
    private boolean mMenu = true, mEnter = false, mStore = false, mLoad = false;
    private ArrayList<String> mMovieList = null;
    private String mFileSelected = null;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mMovieList = savedInstanceState.getStringArrayList(MOVIE_LIST_KEY);
            mFileSelected = savedInstanceState.getString(FILE_SELECTED_KEY);
            mMenu = savedInstanceState.getBoolean(BMENU_KEY);
            mEnter = savedInstanceState.getBoolean(BENTER_KEY);
            mLoad = savedInstanceState.getBoolean(BLOAD_KEY);
            mStore = savedInstanceState.getBoolean(BSTORE_KEY);
        }

        //control which fragment is displayed
        if (mMenu)
            showMainMenuFragment();
        else if (mEnter)
            showEnterTitleFragment(false);
        else if (mStore)
            showStoreMoviesFragment(false);
        else if (mLoad)
            showLoadFileListFragment(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(MOVIE_LIST_KEY, mMovieList);
        outState.putString(FILE_SELECTED_KEY, mFileSelected);
        outState.putBoolean(BMENU_KEY, mMenu);
        outState.putBoolean(BENTER_KEY, mEnter);
        outState.putBoolean(BLOAD_KEY, mLoad);
        outState.putBoolean(BSTORE_KEY, mStore);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void startEnterTitleFragment() {
        mEnter = true;
        mMenu = false;

        showEnterTitleFragment(true);
    }

    @Override
    public void startStoreMoviesFragment() {
        //Only executes if we have a movieList to store
        if (mMovieList != null) {
            mStore = true;
            mMenu = false;

            showStoreMoviesFragment(true);
        }
    }

    @Override
    public void startLoadFileListFragment() {
        mLoad = true;
        mMenu = false;

        showLoadFileListFragment(true);
    }

    @Override
    public void startViewMovieListActivity() {
        //only start if a file has been selected
        if (mFileSelected != null) {
            Intent intent = new Intent(this, ViewMovieListActivity.class);
            intent.putExtra(ViewMovieListActivity.FILENAME_KEY, mFileSelected);
            startActivity(intent);
        }
    }

    /**
     * Ensures that the user can return to the main menu with a back press, without
     * breaking any boolean flags
     */
    @Override
    public void onBackPressed() {
        resetBooleanFragmentFlags();

        super.onBackPressed();
    }

    @Override
    public void stopEnterTitleFragment() {
        resetBooleanFragmentFlags();
        getFragmentManager().popBackStack();
        showMainMenuFragment();
    }

    /**
     * Adds another movie to list
     * String array is in order Movie Title, Actor Name, Year Made
     */
    @Override
    public void addMovieToList(String[] movieData) {
        if (mMovieList == null) {
            mMovieList = new ArrayList<String>();
        }

        for (int i = 0; i < 3; i++) {
            mMovieList.add(movieData[i]);
        }
    }

    /**
     * Attempts to store movies in user provided filename, if
     * successful returns to main menu fragment, and deletes the
     * movieList that was in progress (because it has been stored)
     */
    @Override
    public boolean stopStoreMoviesFragmentAndSaveMovies(String filename) {
        if (writeMovies(filename)) {
            resetBooleanFragmentFlags();
            getFragmentManager().popBackStack();
            showMainMenuFragment();
            return true;
        }
        return false;
    }

    @Override
    public void stopLoadFileListFragment(String filename) {
        if (filename != null)
            mFileSelected = filename;
        resetBooleanFragmentFlags();
        getFragmentManager().popBackStack();
        showMainMenuFragment();
    }

    @Override
    public void exitApplication() {
        if (mMovieList == null) {
            finish();
        } else {
            startStoreMoviesFragment();
        }
    }

    private void resetBooleanFragmentFlags() {
        mMenu = true;
        mLoad = false;
        mEnter = false;
        mStore = false;
    }

    private void showMainMenuFragment() {
        MainMenuFragment fragmentToTransact = null;
        if (getFragmentManager().findFragmentByTag(MainMenuFragment.MAIN_MENU_TAG) == null) {
            fragmentToTransact = new MainMenuFragment();
        } else if (getFragmentManager().findFragmentByTag(MainMenuFragment.MAIN_MENU_TAG).isHidden()) {
            fragmentToTransact =(MainMenuFragment) getFragmentManager()
                    .findFragmentByTag(MainMenuFragment.MAIN_MENU_TAG);
        } else {
            return;
        }

        getFragmentManager().beginTransaction()
                .add(android.R.id.content,
                        fragmentToTransact, MainMenuFragment.MAIN_MENU_TAG)
                .commit();
    }

    private void showEnterTitleFragment(boolean replace) {
        EnterTitleFragment fragmentToTransact = null;
        if (getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG) == null) {
            fragmentToTransact = new EnterTitleFragment();
        } else if (getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG).isHidden()) {
            fragmentToTransact = (EnterTitleFragment) getFragmentManager()
                    .findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG);
        } else {
            return;
        }

        FragmentTransaction manager = getFragmentManager().beginTransaction();

        if (replace) {
            manager.replace(android.R.id.content,
                    fragmentToTransact, EnterTitleFragment.ENTER_TITLE_TAG);
        } else {
            manager.add(android.R.id.content,
                    fragmentToTransact, EnterTitleFragment.ENTER_TITLE_TAG);
        }
        manager.addToBackStack(null);
        manager.commit();
    }

    private void showLoadFileListFragment(boolean replace) {
        LoadFileListFragment fragmentToTransact = null;
        if (getFragmentManager().findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG) == null) {
            fragmentToTransact = new LoadFileListFragment();
        } else if (getFragmentManager().findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG).isHidden()) {
            fragmentToTransact =(LoadFileListFragment) getFragmentManager()
                    .findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG);
        } else {
            return;
        }

        FragmentTransaction manager = getFragmentManager().beginTransaction();

        if (replace) {
            manager.replace(android.R.id.content,
                    fragmentToTransact, LoadFileListFragment.LOAD_MOVIE_TAG);
        } else {
            manager.add(android.R.id.content,
                    fragmentToTransact, LoadFileListFragment.LOAD_MOVIE_TAG);
        }
        manager.addToBackStack(null);
        manager.commit();
    }

    private void showStoreMoviesFragment(boolean replace) {
        StoreMoviesFragment fragmentToTransact = null;
        if (getFragmentManager().findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG) == null) {
            fragmentToTransact = new StoreMoviesFragment();
        } else if (getFragmentManager().findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG).isHidden()) {
            fragmentToTransact = (StoreMoviesFragment) getFragmentManager()
                    .findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG);
        } else {
            return;
        }

        FragmentTransaction manager = getFragmentManager().beginTransaction();

        if (replace) {
            manager.replace(android.R.id.content,
                    fragmentToTransact, StoreMoviesFragment.STORE_MOVIES_TAG);
        } else {
            manager.add(android.R.id.content,
                    fragmentToTransact, StoreMoviesFragment.STORE_MOVIES_TAG);
        }
        manager.addToBackStack(null);
        manager.commit();
    }

    /**
     * Attempts to write current movie data into a user provided filename
     * Using the scheme of Movie Title (newline) Actor Name (newline) Movie Year (newline)
     * Returns false if it fails for any reason, true otherwise
     */
    private boolean writeMovies(String filename) {
        String[] files = fileList();
        OutputStreamWriter movieFileWriter = null;
        for (String file:files) {
            if (filename.equals(file)) {
                return false;
            }
        }

        try {
            movieFileWriter = new OutputStreamWriter(openFileOutput(filename,MODE_PRIVATE));
            for (String movieData: mMovieList) {
                movieFileWriter.write(movieData + "\n");
            }
            movieFileWriter.close();
        } catch (FileNotFoundException e1) {
            return false;
        } catch (IOException e2) {
            return false;
        }

        mMovieList = null;

        return true;
    }
}
