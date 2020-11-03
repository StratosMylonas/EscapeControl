package com.str.escapecontrol;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout AtlantisFrameLayout = findViewById(R.id.atlantis_button);
        AtlantisFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Atlantis.class);
                startActivity(intent);
            }
        });

        FrameLayout MansionFrameLayout = findViewById(R.id.mansion_button);
        MansionFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Mansion.class);
                startActivity(intent);
            }
        });

        FrameLayout MissionFrameLayout = findViewById(R.id.mission_button);
        MissionFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Mission.class);
                startActivity(intent);
            }
        });

        FrameLayout PhilosopherFrameLayout = findViewById(R.id.philosophers_button);
        PhilosopherFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Philosopher.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.settings_gear);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        SQLiteDatabase database = openOrCreateDatabase("escape_control", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS IPAddress(id INT, ip VARCHAR(20));");
        //database.execSQL("INSERT INTO IPAddress VALUES ('192.168.11.100');");
        Cursor cursor = database.rawQuery("SELECT ip FROM IPAddress", null);
        if (cursor.getCount() == 0){
            database.execSQL("INSERT INTO IPAddress VALUES (1, '192.168.11.100');");
            cursor = database.rawQuery("SELECT ip FROM IPAddress WHERE id = 1", null);
        }
        cursor.moveToFirst();
        cursor.close();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.aboutUs) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView((R.layout.about_custom_dialog));
            try{
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}