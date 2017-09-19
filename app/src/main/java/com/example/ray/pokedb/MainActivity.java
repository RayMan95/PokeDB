package com.example.ray.pokedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/*
 * Provides the home page for the app
 */
public class MainActivity extends Activity {

    private boolean pressedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tracks if the soft button back was pressed
        pressedBack = false;
    }

    public void click_view_progress(View v){
        Intent intent = new Intent(this, DisplayCandyProgress.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        if(!pressedBack){
            Toast toast = Toast.makeText(getApplicationContext(), "Press again to exit",
                    Toast.LENGTH_SHORT);
            toast.show();
            pressedBack = true;
        }
        else{
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        }
    }
}
