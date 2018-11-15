/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import cl.ucn.disc.dsm.directorio.R;

/**
 * Activity principal.
 *
 * @author Diego Urrutia-Astorga.
 */
public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(android.R.id.list);

        TextView empty = findViewById(android.R.id.empty);
        listView.setEmptyView(empty);

    }

}
