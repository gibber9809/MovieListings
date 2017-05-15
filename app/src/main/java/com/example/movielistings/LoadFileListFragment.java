package com.example.movielistings;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//TODO Change this to a ListFragment

/**
 * Fragment that allows the user to select which file to load
 */
public class LoadFileListFragment extends ListFragment {
    public static final String LOAD_MOVIE_TAG = "LOAD_MOVIE_TAG";
    private static final String SELECTED_INDEX_KEY = "SELECTED_INDEX_KEY";
    private Context mParentContext = null;
    private LoadFileListFragmentListener mParentListener = null;
    private String[] mFileList = null;
    private int mSelectedIndex = -1;


    public interface LoadFileListFragmentListener {
        void setSelectedFile(String filename);
    }

    public LoadFileListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSelectedIndex = savedInstanceState.getInt(SELECTED_INDEX_KEY);
        }

        View parentView = inflater.inflate(R.layout.fragment_load_movie_list, container, false);
        parentView.findViewById(R.id.done_selecting_button).setOnClickListener(getDoneOnClickListener());

        //Create and set list adapter for the listview based on and array of filenames
        mFileList = mParentContext.fileList();
        setListAdapter(new ArrayAdapter<String>(mParentContext,
                android.R.layout.simple_list_item_1, mFileList));

        return parentView;
    }

    //Gets parent context, and makes sure that it has implemented all listener methods
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mParentListener = (LoadFileListFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoadFileListFragmentListener");
        }
        mParentContext = context;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(SELECTED_INDEX_KEY, mSelectedIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mSelectedIndex = position;

    }

    //Submits the name of the file to load, if one has been selected
    private View.OnClickListener getDoneOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (mSelectedIndex >= 0) {
                    mParentListener.setSelectedFile(mFileList[mSelectedIndex]);
                } else {
                    mParentListener.setSelectedFile(null);
                }
            }
        };
    }
}
