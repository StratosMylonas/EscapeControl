package com.str.escapecontrol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

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