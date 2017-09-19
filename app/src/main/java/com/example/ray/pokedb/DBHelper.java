package com.example.ray.pokedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Rayaan Fakier on 2016/10/18.
 * An extension of {@link SQLiteOpenHelper} that manages all database queries for the application.
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "myDB.db";
    public static final String[] COLUMN_NAMES = {"name", "candies", "candies_to_evolution"};

    public DBHelper (Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table entries" +
                "(id integer primary key, name text, candies integer, candies_to_evolution integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL("DROP TABLE IF EXISTS entries");
        onCreate(db);
    }

    /**
     * Inserts and entry of given parameters into the database
     * @param name Pokemon name
     * @param candies number of candies
     * @param candiesToEvo number of candies for evolution
     * @return true if successfully inserted; false otherwise
     */
    protected boolean insertEntry(String name, int candies, int candiesToEvo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("candies", candies);
        contentValues.put("candies_to_evolution", candiesToEvo);

        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.insert("entries", null, contentValues);
        mydb.close();
        return true;
    }

    /**
     * Creates a {@link HashMap} of all entries in the database where the entry names are the keys
     * and their respective candies are the values and returns it
     * @return {@link HashMap} of all pokemon to candies
     */
    protected HashMap<String, Integer> getAllEntries(){
        HashMap<String, Integer> map = new HashMap<>();

        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor cursor = mydb.rawQuery("select * from entries", null);

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> vals = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            keys.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAMES[0])));
            cursor.moveToNext();
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            vals.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAMES[1])));
            cursor.moveToNext();
        }
        cursor.close();
        mydb.close();

        for (int i = 0; i < keys.size(); i++){
            map.put(keys.get(i), Integer.parseInt(vals.get(i)));
        }

        return map;
    }

    public boolean editEntry(String pokemonName, int candies){
        SQLiteDatabase mydb = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Constants c = new Constants(context);
        HashMap<String, Integer> map = c.getPokeToCandyForEvoMap();

        contentValues.put("name", pokemonName);
        contentValues.put("candies", candies);
        contentValues.put("candies_to_evolution",map.get(pokemonName));

        int id = c.getPokemonId(pokemonName);
        if (id < 0) return false;
        int dbResult = mydb.update("entries",contentValues, "id = ?",
                new String[] {Integer.toString(id)});
        mydb.close();
        return  dbResult > 0;
    }

    public Cursor getData(int id){
        SQLiteDatabase mydb = this.getReadableDatabase();
        Cursor cursor = mydb.rawQuery("select * from entries where id="+id+"", null);
        mydb.close();
        return cursor;
    }

    protected boolean clearData(){
        SQLiteDatabase mydb = this.getWritableDatabase();
        int returnVal = mydb.delete("entries", "1", null);
        mydb.close();
        return returnVal > 0;
    }
}
