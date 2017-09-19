package com.example.ray.pokedb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.HashMap;


public class DisplayCandyProgress extends Activity {

    private DBHelper mydb;
    private String[] ALL_POKEMON;
    private HashMap<String, Integer> POKEMON_CANDIES_MAP;
    private HashMap<String, Integer> POKEMON_CANDIES_TO_EVO_MAP;
    private TextView nameTextView,candyTextView,candyToEvoTextView,numEvoTextView, evoPercentTextView;
    private int CURR_NAME_ORDER, CURR_CANDY_ORDER, CURR_CANDYTOEVO_ORDER, CURR_NUMEVOS_ORDER;
    private ProgressBar evoProgressBar;
    Constants c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_candy_progress);

        // Core field objects for use across class
        c = new Constants(this);
        mydb = new DBHelper(this);
        ALL_POKEMON = c.getAllPokemon(); // complete list from res
        POKEMON_CANDIES_TO_EVO_MAP = c.getPokeToCandyForEvoMap();

        // View objects
        nameTextView = (TextView) findViewById(R.id.display_all_names);
        candyTextView = (TextView) findViewById(R.id.display_all_candies);
        candyToEvoTextView = (TextView) findViewById(R.id.display_all_candies_to_evo);
        numEvoTextView = (TextView) findViewById(R.id.display_all_num_of_evo);
        evoProgressBar = (ProgressBar) findViewById(R.id.progressBarEvos);
        evoPercentTextView = (TextView) findViewById(R.id.textViewEvoPercent);

        // Order constants
        CURR_NAME_ORDER = 0;
        CURR_CANDY_ORDER = 0;
        CURR_CANDYTOEVO_ORDER = 0;
        CURR_NUMEVOS_ORDER = 0;

        checkFirstRun(); // for seeding

        POKEMON_CANDIES_MAP = mydb.getAllEntries(); // from database
        displayEntries(-1,0); // display unordered initial display
    }


    protected void checkFirstRun(){
        SharedPreferences preferences = getSharedPreferences("com.example.ray.pokedb", MODE_PRIVATE);
        if(preferences.getBoolean("firstrun", true)){
            seed();

            preferences.edit().putBoolean("firstrun", false).apply();
        }
    }

    // TODO fix sorting
    /**
     * Displays the entries in the database by populating the relevant {@link TextView} objects
     * with text.
     * @param column id of column to be sorted: {-1, none}, [0, name}, {1, C}, {2, CTE}, {3, #E}
     * @param order integer representing type of order to be used: {-1, ascending}, {0, unordered},
     *              {1, descending}
     */
    private void displayEntries(int column, int order){
//        Comparator<String> stringComparator = getStringComparator();
//        Comparator<Integer> integerComparator = getIntegerComparator();
//        ArrayList<Object> al = new ArrayList<>();
//        al.add(ALL_POKEMON);
//        al.add(POKEMON_CANDIES_MAP.values());
//        al.add(ALL_CANDIES_TO_EVO);
        // #E arraylist

//        switch (order){
//            case -1:
//                Collections.sort(al.get(0), Collections.reverseOrder());
//                break;
//            case 1:
//                Collections.sort(map.get(column));
//                break;
//        }

        String name = "", candies = "", candiesToEvo = "", numEvos="";
        int numEvosInt = 0;

        for (String s: ALL_POKEMON){
            // name column
            name += s+"\n";
            // candies column
            int numCandies = POKEMON_CANDIES_MAP.get(s);
            candies += numCandies+"\n";
            int toEvo = POKEMON_CANDIES_TO_EVO_MAP.get(s);
            // candies to evo column
            if (toEvo == 0) candiesToEvo += "----\n";
            else candiesToEvo += toEvo+"\n";
            // # evos column
            if (toEvo == 0) numEvos += "0\n";
            // TODO ignore evos (possibly)
            else{
                numEvosInt += (numCandies/toEvo);
                numEvos += (numCandies/toEvo)+"\n";
            }
        }

        // sets progress bar
        evoProgressBar.setProgress((numEvosInt*100)/80);
        evoPercentTextView.setText(numEvosInt+"/80");

        // sets name column with all pokemon names
        nameTextView.setText(name);
        nameTextView.setFocusable(false);
        nameTextView.setClickable(false);

        // sets candies column with relevant candies
        candyTextView.setText(candies);
        candyTextView.setFocusable(false);
        candyTextView.setClickable(false);

        // sets candiesToEvo column with relevant candiesToEvo
        candyToEvoTextView.setText(candiesToEvo);
        candyToEvoTextView.setFocusable(false);
        candyToEvoTextView.setClickable(false);

        // sets evos column with relevant evos
        numEvoTextView.setText(numEvos);
        numEvoTextView.setFocusable(false);
        numEvoTextView.setClickable(false);
    }

    /**
     * Reacts to edit entry click. Starts an {@code EditEntry} activity
     * @param v parent {@link View}
     */
    public void clickEditEntry(View v){
        Intent intent = new Intent(this, EditEntry.class);
        startActivity(intent);
    }

    /**
     * Reacts to clear entries click. Uses the {@link DBHelper} object to remove all entries in
     * the database and re-seeds it.
     * @param v parent {@code View}
     */
    public void clickClearEntries(View v){
        AlertDialog dialog = new AlertDialog.Builder(this).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.clearData(); // clear entire database
                        seed(); // re-seed database
                        evoProgressBar.setProgress(0);
                        recreate(); // recreates the activity to update display
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
            }
        }).setTitle("Warning: action is irreversible").setMessage("Are you ABSOLUTELY sure?").show();
        TextView dialogMessageTextView = (TextView)dialog.findViewById(android.R.id.message);
        // use custom font for dialog message
        Typeface face = Typeface.createFromAsset(getResources().getAssets(), "ComingSoon.ttf");
        dialogMessageTextView.setTypeface(face);
    }

    public void clickCandyProgressName(View v){
//        if (CURR_NAME_ORDER == 0) CURR_NAME_ORDER = 1;
//        else CURR_NAME_ORDER *= -1;
//        displayEntries(0,CURR_NAME_ORDER);
    }

    public void clickCandyProgressCandy(View v){
//        if (CURR_CANDY_ORDER == 0) CURR_CANDY_ORDER = 1;
//        else CURR_CANDY_ORDER *= -1;
//        displayEntries(1,CURR_NAME_ORDER);
    }

    public void clickCandyProgressCandyToEvo(View v){
//        if (CURR_CANDYTOEVO_ORDER == 0) CURR_CANDYTOEVO_ORDER = 1;
//        else CURR_CANDYTOEVO_ORDER *= -1;
//        displayEntries(2,CURR_CANDYTOEVO_ORDER);
    }

    public void clickCandyProgressNumEvos(View v){
        // TODO do num evos
    }

    // TODO refactor all code into DBHelper
    /**
     * Seeds database with all pokemon: having 0 candies and their static candies to evo amount
     */
    private void seed(){
        int[] candiesToEvoList = c.getAllCandiesToEvo(); // complete list from res
        for (int i = 0; i < 151; i++){
            mydb.insertEntry(ALL_POKEMON[i],0,candiesToEvoList[i]);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
