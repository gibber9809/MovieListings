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


public class EnterTitleFragment extends Fragment {
    public static final String ENTER_TITLE_TAG = "ENTER_TITLE_TAG";
    public static final int CURRENT_YEAR = 2017;
    public static final int FIRST_YEAR = 1900;
    private Context mParentContext = null;
    private enterTitleListener mListener = null;
    private ArrayList mYears;

    private EditText mMovieEdit = null;
    private EditText mActorEdit = null;
    private Spinner mYearSpinner = null;


    public interface enterTitleListener {
        void setFragmentMenuFromEnter();
    }

    public EnterTitleFragment() {
        // Required empty public constructor
    }

    //Add some boilerplate setup to oncreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mYears = new ArrayList();
        for (int i = FIRST_YEAR; i <= CURRENT_YEAR; i++ ) {
            mYears.add(i);
        }
    }

    //Add setup for spinner and buttons
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_enter_title, container, false);

        //Pass array of years into spinner
        mYearSpinner = (Spinner) fragmentView.findViewById(R.id.select_year_spinner);
        mYearSpinner.setAdapter(new ArrayAdapter(mParentContext,R.layout.adapter_textview, mYears));
        //yearSpinner.getSelectedItem();

        //Set up callbacks for buttons
        fragmentView.findViewById(R.id.add_button).setOnClickListener(getAddOnClickListener());
        fragmentView.findViewById(R.id.done_button).setOnClickListener(getDoneOnClickListener());

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (enterTitleListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement enterTitleListener Interface");
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

    private View.OnClickListener getAddOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First add movie to some data structure
                //getYearSpinner().getSelectedItem()
                getMovieEdit().setText("");
                getActorEdit().setText("");
            }
        };
    }

    private View.OnClickListener getDoneOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do what must be done
                mListener.setFragmentMenuFromEnter();
            }
        };
    }
}
