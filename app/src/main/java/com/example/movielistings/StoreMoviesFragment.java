package com.example.movielistings;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Fragment that handles asking the user for the filename of where to store their movie list
 */
public class StoreMoviesFragment extends Fragment {
    public static final String STORE_MOVIES_TAG = "STORE_MOVIES_TAG";
    private EditText mFileNameEdit = null;
    private StoreMoviesFragmentListener mParentListener = null;


    public interface StoreMoviesFragmentListener {
        boolean stopStoreMoviesFragmentAndSaveMovies(String filename);
    }

    public StoreMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_store_movies, container, false);
        parentView.findViewById(R.id.save_button).setOnClickListener(getSubmitListener());
        return parentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof StoreMoviesFragmentListener) {
            mParentListener = (StoreMoviesFragmentListener) context;
        }
    }

    public EditText getFileNameEdit() {
        if (mFileNameEdit == null) {
            mFileNameEdit = (EditText) getActivity().findViewById(R.id.enter_filename_edit);
        }
        return mFileNameEdit;
    }

    /**
     * If a filename has been submitted, saves into that filename and returns to main menu, unless any
     * exceptions are thrown in the writing process.
     */
    public View.OnClickListener getSubmitListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (getFileNameEdit().getText().toString().length() == 0) {
                    getFileNameEdit().requestFocus();
                } else {
                    if (!mParentListener.stopStoreMoviesFragmentAndSaveMovies(getFileNameEdit().getText().toString())) {
                        getFileNameEdit().requestFocus();
                    }
                }
            }
        };
    }

}
