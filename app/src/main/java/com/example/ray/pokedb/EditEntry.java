package com.example.ray.pokedb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.HashMap;


public class EditEntry extends Activity {

    private EditText candiesEditText;
    private Spinner spinner;
    private final Context c = this;
    private DBHelper mydb;
    private Constants constants;
    private String CURR_POKE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        // Core field objects for use across class
        constants = new Constants(this);
        mydb = new DBHelper(this);
        candiesEditText = (EditText) findViewById(R.id.editEntryCandyInput);
        CURR_POKE = "Bulbasaur";
        spinner = (Spinner) findViewById(R.id.editEntrySpinner);
		// TODO add pokedex id to pokenames in arrayadapter resource
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.pokemon_no_evos,
                R.layout.edit_entry_pokemon_spinner);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CURR_POKE = parent.getItemAtPosition(position).toString(); // TODO substring text w/out pokedex id
                // TODO display current candy
//                candiesEditText.setText(constants.getAllCandiesToEvo()[constants.getPokemonId(poke.toString()) - 1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setVisibility(View.VISIBLE);
    }

    /**
     * Reacts to edit entry click. Uses the {@link DBHelper} to update the entry in the database and
     * requests a {@link android.app.Dialog} to ask if the user wishes to submit another entry
     * @param v parent {@link View}
     */
    public void buttonEditEntry(View v){
        int candies = Integer.parseInt(candiesEditText.getText().toString());

        boolean outcome = mydb.editEntry(CURR_POKE, candies); // TODO add dialog for fail (possibly)
        HashMap<String, String[]> evoMap = constants.getEvoMap();
        if (evoMap.containsKey(CURR_POKE)){
            for (String s : evoMap.get(CURR_POKE)){
                mydb.editEntry(s,candies);
            }
        }


        AlertDialog dialog = new AlertDialog.Builder(this).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        candiesEditText.setText("");
                        dialog.cancel();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToDisplay();
                    }
        }).setTitle("Success").setMessage("Edit another entry?").show();
        TextView dialogMessageTextView = (TextView)dialog.findViewById(android.R.id.message);
        // use custom font for dialog message
        Typeface face = Typeface.createFromAsset(getResources().getAssets(), "ComingSoon.ttf");
        dialogMessageTextView.setTypeface(face);
    }

    /**
     * Convenience method for starting a {@link DisplayCandyProgress} activity
     */
    private void goToDisplay(){
        Intent intent = new Intent(c, DisplayCandyProgress.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){ goToDisplay(); }
}
