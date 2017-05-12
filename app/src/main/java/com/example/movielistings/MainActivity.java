package com.example.movielistings;


import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements MainMenuFragment.mainMenuListener,
        EnterTitleFragment.enterTitleListener{
    private boolean mMenu = true, mEnter = false, mStore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mMenu && getFragmentManager().findFragmentByTag(MainMenuFragment.MAIN_MENU_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content , new MainMenuFragment(), MainMenuFragment.MAIN_MENU_TAG)
                    .commit();
        } else if (mEnter && getFragmentManager().findFragmentByTag(EnterTitleFragment.ENTER_TITLE_TAG) == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new EnterTitleFragment(), EnterTitleFragment.ENTER_TITLE_TAG)
                    .commit();
        } else if (mStore){
            //pass for now
        }
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
    public void setFragmentMenuFromEnter() {
        mMenu = true;
        mEnter = false;

        onBackPressed();
    }
}
