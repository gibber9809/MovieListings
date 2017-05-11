package com.example.movielistings;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.mainMenuListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getFragmentManager().findFragmentById(R.id.main_menu) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new MainMenuFragment())
                    .commit();
        }
    }

}
