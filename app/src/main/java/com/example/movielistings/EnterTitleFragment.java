package com.example.movielistings;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterTitleFragment extends Fragment {
    public static final int CURRENT_YEAR = 2017;
    public static final int FIRST_YEAR = 1850;
    public static final String SAVED_YEAR_KEY = "SAVED_YEAR_KEY";

    public EnterTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enter_title, container, false);
    }

}
