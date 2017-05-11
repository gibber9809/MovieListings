package com.example.movielistings;



import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainMenuFragment extends Fragment {
    private mainMenuListener mParentContext = null;


    public MainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //assign local listeners for all buttons in this fragment
        //tweak this once I've got a bit more persistence running
        fragmentView.findViewById(R.id.enter_title_button).setOnClickListener(generateEnterTitleListener());
        fragmentView.findViewById(R.id.view_movies_button).setOnClickListener(generateViewMoviesListener());
        fragmentView.findViewById(R.id.store_button).setOnClickListener(generateStoreMoviesListener());
        fragmentView.findViewById(R.id.load_button).setOnClickListener(generateLoadMoviesListener());
        fragmentView.findViewById(R.id.exit_button).setOnClickListener(generateExitAppListener());

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mParentContext = (mainMenuListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement mainMenuListener");
        }
    }

    public interface mainMenuListener {
        //TODO
    }

    public View.OnClickListener generateEnterTitleListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getFragmentManager().beginTransaction();
                manager.replace(R.id.main_menu, new EnterTitleFragment());
                manager.addToBackStack(null);
                manager.commit();
            }
        };
    }

    public View.OnClickListener generateViewMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        };
    }

    public View.OnClickListener generateStoreMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        };
    }

    public View.OnClickListener generateLoadMoviesListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        };
    }

    public View.OnClickListener generateExitAppListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        };
    }

}
