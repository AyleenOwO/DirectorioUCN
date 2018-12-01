
package cl.ucn.disc.dsm.directorio.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import cl.ucn.disc.dsm.directorio.R;
import cl.ucn.disc.dsm.directorio.adapters.ListAdapter;
import cl.ucn.disc.dsm.directorio.models.Persona;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    /**
     *
     */
    private List<Persona> personas = new ArrayList<Persona>();

    /**
     *
     */
    private MaterialSearchView searchView;

    /**
     *
     */
    private ListView listView;

    /**
     *
     */
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        load();

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        listView = (ListView)findViewById(android.R.id.list);

        ListAdapter adapter = new ListAdapter(this,R.layout.row_persona,personas);

        clickItem(adapter, personas);

        queryText();




    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;

    }

    private void clickItem(ListAdapter adapter, List<Persona> personas){
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_persona,null);

                TextView nombre = (TextView)dialogView.findViewById(R.id.rp_tv_nombre);
                TextView cargo = (TextView)dialogView.findViewById(R.id.rp_tv_cargo);
                TextView unidad = (TextView)dialogView.findViewById(R.id.rp_tv_unidad);
                TextView email = (TextView)dialogView.findViewById(R.id.rp_tv_email);
                TextView telefono = (TextView)dialogView.findViewById(R.id.rp_tv_telefono);
                TextView oficina = (TextView)dialogView.findViewById(R.id.rp_tv_oficina);

                nombre.setText(personas.get(position).getNombre());
                cargo.setText(personas.get(position).getCargo().equals("null") ? "" : personas.get(position).getCargo());
                unidad.setText(personas.get(position).getUnidad().equals("null") ? "" : personas.get(position).getUnidad());
                email.setText(personas.get(position).getEmail().equals("null") ? "" : personas.get(position).getEmail());
                telefono.setText(personas.get(position).getTelefono().equals("null") ? "" : personas.get(position).getTelefono());
                oficina.setText(personas.get(position).getOficina().equals("null") ? "" : personas.get(position).getOficina());

                dialogBuilder.setView(dialogView);
                dialogBuilder.create().show();
            }
        });

    }

    private void queryText(){
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<Persona> lstFound = new ArrayList<>();
                    for(Persona item:personas){
                        if(item.getNombre().toLowerCase().contains(newText.toLowerCase())){
                            lstFound.add(item);
                        }
                    }
                    ListAdapter adapter = new ListAdapter(MainActivity.this,R.layout.row_persona,lstFound);
                    clickItem(adapter, lstFound);

                }
                else {
                    ListAdapter adapter = new ListAdapter(MainActivity.this,R.layout.row_persona,personas);
                    clickItem(adapter, personas);
                }
                return true;
            }
        });

    }

    private void load(){
        try {
            InputStream is = getAssets().open("ucn.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject object = jsonArray.getJSONObject(i);
                personas.add(Persona.builder()
                        .id(object.getString("id"))
                        .nombre(object.getString("nombre"))
                        .cargo(object.getString("cargo"))
                        .unidad(object.getString("unidad"))
                        .email(object.getString("email"))
                        .telefono(object.getString("telefono"))
                        .oficina(object.getString("oficina"))
                        .build());

            }

        } catch (IOException exception) {
            exception.printStackTrace();

        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        Collections.sort(this.personas, (p1, p2) -> p1.getNombre().compareTo(p2.getNombre()));
    }

}
