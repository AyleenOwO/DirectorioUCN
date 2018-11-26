/*
 * Copyright (c) 2018.  Diego Urrutia Astorga <durrutia@ucn.cl>
 * This work is licensed under a Creative Commons Attribution-NonCommercial 4.0 International License.
 * http://creativecommons.org/licenses/by-nc/4.0/
 *
 */

package cl.ucn.disc.dsm.directorio.activities;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.adapters.PersonaAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Activity principal.
 *
 * @author Diego Urrutia-Astorga.
 */
@Slf4j
public class MainActivity extends ListActivity {
    //extends AppCompatActivity

    /**
     * Adaptador de Personas
     */
    private PersonaAdapter personaAdapter;

    /**
     *
     */
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
        //setContentView(R.layout.activity_main);

        // Toolbar
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //ListView listView = findViewById(android.R.id.list);

        //TextView empty = findViewById(android.R.id.empty);
        //listView.setEmptyView(empty);

        // Si el adaptador es null, no se ha construido
        if (this.personaAdapter == null) {

            // .. lo contruyo ..
            this.personaAdapter = new PersonaAdapter(this);

        }

        // Adaptador a utilizar para mostrar los datos
        super.setListAdapter(this.personaAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Si el adaptador no es null y no hay datos ..
        if (this.personaAdapter != null && this.personaAdapter.isEmpty()) {

            // Mensaje para mostrar que se estan cargando los datos
            Snackbar.make(findViewById(android.R.id.content), "Loading data ..", Snackbar.LENGTH_LONG).show();

            // Ejecuto en segundo plano ..
            AsyncTask.execute(() -> {

                log.debug("Loading data ..");
                personaAdapter.load(this);

                // .. ejecuto en el hilo principal y vuelve dibujarse
                runOnUiThread(() -> {

                    // Notifico que cambio el conjunto de datos y vuelve a dibujarlos
                    personaAdapter.notifyDataSetChanged();

                });

            });
        }
    }
}
