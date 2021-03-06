package com.example.movielistings;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Fragment that allows the user to enter data about movies for their list
 */
public class EnterTitleFragment extends Fragment {
    public static final String ENTER_TITLE_TAG = "ENTER_TITLE_TAG";
    public static final int CURRENT_YEAR = 2017;
    public static final int FIRST_YEAR = 1900;
    private Context mParentContext = null;
    private enterTitleListener mListener = null;
    private ArrayList<Integer> mYears;

    private EditText mMovieEdit = null;
    private EditText mActorEdit = null;
    private Spinner mYearSpinner = null;


    public interface enterTitleListener {
        void stopEnterTitleFragment();
        void addMovieToList(String[] movieData);
    }

    public EnterTitleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mYears = new ArrayList<Integer>();
        for (int i = FIRST_YEAR; i <= CURRENT_YEAR; i++ ) {
            mYears.add(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_enter_title, container, false);

        //Pass array of years into spinner
        mYearSpinner = (Spinner) fragmentView.findViewById(R.id.select_year_spinner);
        mYearSpinner.setAdapter(new ArrayAdapter<Integer>(mParentContext,R.layout.adapter_textview, mYears));

        //Set up callbacks for buttons
        fragmentView.findViewById(R.id.add_button).setOnClickListener(getAddOnClickListener());
        fragmentView.findViewById(R.id.done_button).setOnClickListener(getDoneOnClickListener());

        return fragmentView;
    }

    /**
     * Makes sure that parent context has implemented listener methods
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof enterTitleListener) {
            mListener = (enterTitleListener) context;
        }
        mParentContext = context;
    }

    public EditText getMovieEdit() {
        if (mMovieEdit == null) {
            mMovieEdit = (EditText) getActivity().findViewById(R.id.enter_movie_edit);
        }
        return mMovieEdit;
    }

    public EditText getActorEdit() {
        if (mActorEdit == null) {
            mActorEdit = (EditText) getActivity().findViewById(R.id.enter_actor_edit);
        }
        return mActorEdit;
    }

    public Spinner getYearSpinner() {
        if (mYearSpinner == null) {
            mYearSpinner = (Spinner) getActivity().findViewById(R.id.select_year_spinner);
        }
        return mYearSpinner;
    }

    /*
     * Adds a movie to a movie list, if all information has been entered correctly
     * Resets all of the fields when this happens
     */
    private View.OnClickListener getAddOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] movieData = new String[3];
                movieData[0] = getMovieEdit().getText().toString();
                movieData[1] = getActorEdit().getText().toString();
                movieData[2] = getYearSpinner().getSelectedItem().toString();

                //Inelegant way of forcing user to fill in empty fields
                if (movieData[0].length() == 0) {
                    getMovieEdit().requestFocus();
                } else if (movieData[1].length() == 0) {
                    getActorEdit().requestFocus();
                } else {
                    mListener.addMovieToList(movieData);

                    getMovieEdit().setText("");
                    getActorEdit().setText("");
                    getYearSpinner().setSelection(0);
                }
            }
        };
    }

    private View.OnClickListener getDoneOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.stopEnterTitleFragment();
            }
        };
    }
}
