package com.example.movielistings;

import android.content.Intent;
import android.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ViewMovieListActivity extends AppCompatActivity {
    public static final String FILENAME_KEY = "FILENAME_KEY";
    private static final String MOVIE_NAME = "MOVIE_NAME", ACTOR_NAME = "ACTOR_NAME", YEAR = "YEAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_list);
        Intent intent = getIntent();
        String filename = intent.getStringExtra(FILENAME_KEY);

        ArrayList< ArrayMap<String, String> > movieDataList;
        movieDataList = getData(filename);

        ListView movieList =(ListView) findViewById(R.id.movie_list);
        ListAdapter adapter = new SimpleAdapter(
                this,
                movieDataList,
                R.layout.movie_list_element,
                new String[] {MOVIE_NAME, ACTOR_NAME, YEAR},
                new int[] {R.id.text1, R.id.text2, R.id.text3});

        movieList.setAdapter(adapter);
    }

    /**
     * Reads the movie list data from a file, and packages it so that it can be used
     * by a SimpleAdapter
     */
    private ArrayList< ArrayMap<String, String> > getData(String filename) {
        BufferedReader in = null;
        ArrayList< ArrayMap<String, String> > movieDataList = new ArrayList< ArrayMap<String, String> >();
        try {
            ArrayMap<String, String> movieData = new ArrayMap<>();
            in = new BufferedReader(new InputStreamReader(openFileInput(filename)));
            String line = in.readLine();

            for (int i = 0; line != null; i++) {
                if (i % 3 == 0) {
                    movieData.put(MOVIE_NAME, line);
                } else if (i % 3 == 1) {
                    movieData.put(ACTOR_NAME, line);
                } else if (i% 3 == 2) {
                    movieData.put(YEAR, line);
                    movieDataList.add(movieData);
                    movieData = new ArrayMap<>();
                }
                line = in.readLine();
            }
        } catch(FileNotFoundException e) {
            return movieDataList;
        }catch (IOException e2) {
            return movieDataList;
        }
        return movieDataList;
    }
}
