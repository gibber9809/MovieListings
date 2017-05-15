package com.example.movielistings;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


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

        //TODO generalize this
        if (mMenu && getFragmentManager().findFragmentByTag(MainMenuFragment.MAIN_MENU_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new MainMenuFragment(), MainMenuFragment.MAIN_MENU_TAG)
                    .commit();
        } else if (mEnter && getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new EnterTitleFragment(), EnterTitleFragment.ENTER_TITLE_TAG)
                    .addToBackStack(null)
                    .commit();
        } else if (mStore && getFragmentManager().findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new StoreMoviesFragment(), StoreMoviesFragment.STORE_MOVIES_TAG)
                    .addToBackStack(null)
                    .commit();
        } else if (mLoad && getFragmentManager().findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new LoadFileListFragment(), LoadFileListFragment.LOAD_MOVIE_TAG)
                    .addToBackStack(null)
                    .commit();
        }
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
    public void setFragmentEnterFromMenu() {
        mEnter = true;
        mMenu = false;

        if (getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG) == null) {
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            manager.replace(android.R.id.content, new EnterTitleFragment(), EnterTitleFragment.ENTER_TITLE_TAG);
            manager.addToBackStack(null);
            manager.commit();
        } else {
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            manager.replace(android.R.id.content,
                    getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG));
            manager.addToBackStack(null);
            manager.commit();

        }
    }

    @Override
    public void setFragmentStoreFromMenu() {
        if (mMovieList != null) {
            mStore = true;
            mMenu = false;

            if (getFragmentManager().findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG) == null) {
                FragmentTransaction manager = getFragmentManager().beginTransaction();
                manager.replace(android.R.id.content,
                        new StoreMoviesFragment(), StoreMoviesFragment.STORE_MOVIES_TAG);
                manager.addToBackStack(null);
                manager.commit();
            } else {
                FragmentTransaction manager = getFragmentManager().beginTransaction();
                manager.replace(android.R.id.content,
                        getFragmentManager().findFragmentByTag(StoreMoviesFragment.STORE_MOVIES_TAG));
                manager.addToBackStack(null);
                manager.commit();
            }
        }
        //Perhaps add a toast otherwise
    }

    @Override
    public void setFragmentLoadFromMenu() {
        mLoad = true;
        mMenu = false;

        if (getFragmentManager().findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG) == null) {
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            manager.replace(android.R.id.content, new LoadFileListFragment(), LoadFileListFragment.LOAD_MOVIE_TAG);
            manager.addToBackStack(null);
            manager.commit();
        } else {
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            manager.replace(android.R.id.content,
                    getFragmentManager().findFragmentByTag(LoadFileListFragment.LOAD_MOVIE_TAG));
            manager.addToBackStack(null);
            manager.commit();
        }
    }

    @Override
    public void startActivityViewMovieList() {
        if (mFileSelected != null) {
            Intent intent = new Intent(this, ViewMovieListActivity.class);
            intent.putExtra(ViewMovieListActivity.FILENAME_KEY, mFileSelected);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        mMenu = true;
        mStore = false;
        mEnter = false;
        mLoad = false;

        super.onBackPressed();
    }

    @Override
    public void setFragmentMenuFromEnter() {
        onBackPressed();
    }

    //Adds another movie to list
    //String array is in order Movie Title, Actor Name, Year Made
    @Override
    public void addMovieToList(String[] movieData) {
        if (mMovieList == null) {
            mMovieList = new ArrayList<String>();
        }
        for (int i = 0; i < 3; i++) {
            mMovieList.add(movieData[i]);
        }
    }

    //Attempts to store movies in user provided filename, if
    //successful returns to main menu fragment
    @Override
    public boolean returnAndSaveMovies(String filename) {
        if (writeMovies(filename)) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void setSelectedFile(String filename) {
        if (filename != null)
            mFileSelected = filename;
        onBackPressed();
    }

    @Override
    public void exitApplication() {
        if (mMovieList == null) {
            finish();
        } else {
            setFragmentStoreFromMenu();
        }
    }

    //Attempts to write current movie data into a user provided filename
    //Using the scheme of Movie Title (newline) Actor Name (newline) Movie Year (newline)
    //Returns false if it fails for any reason, true otherwise
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
