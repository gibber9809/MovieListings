package com.example.movielistings;



import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment {
    public static final String MAIN_MENU_TAG = "MAIN_MENU_TAG";
    private mainMenuListener mParentListener = null;

    public interface mainMenuListener {
        void startEnterTitleFragment();
        void startStoreMoviesFragment();
        void startLoadFileListFragment();
        void startViewMovieListActivity();
        void exitApplication();
    }

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //assign local listeners for all buttons in this fragment
        fragmentView.findViewById(R.id.enter_title_button).setOnClickListener(generateEnterTitleListener());
        fragmentView.findViewById(R.id.view_movies_button).setOnClickListener(generateViewMoviesListener());
        fragmentView.findViewById(R.id.store_button).setOnClickListener(generateStoreMoviesListener());
        fragmentView.findViewById(R.id.load_button).setOnClickListener(generateLoadMoviesListener());
        fragmentView.findViewById(R.id.exit_button).setOnClickListener(generateExitAppListener());

        return fragmentView;
    }


    /**
     * Here we store the parent context, and make sure it has implemented all of our listeners
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof mainMenuListener) {
            mParentListener = (mainMenuListener) context;
        }
    }

    /**
     * All of these methods generate onClickListeners that call methods in MainActivity on clicks
     * In this implementation the main activity will ignore these calls when they are invalid
     * e.g. trying to store a movie list when no movies have been addd to it
     */
    public View.OnClickListener generateEnterTitleListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentListener.startEnterTitleFragment();
            }
        };
    }

    public View.OnClickListener generateViewMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentListener.startViewMovieListActivity();
            }
        };
    }

    public View.OnClickListener generateStoreMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentListener.startStoreMoviesFragment();
            }
        };
    }

    public View.OnClickListener generateLoadMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentListener.startLoadFileListFragment();
            }
        };
    }

    public View.OnClickListener generateExitAppListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentListener.exitApplication();
            }
        };
    }

}
