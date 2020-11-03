package com.str.escapecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Settings extends AppCompatActivity {

    LinearLayout ipAddress, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ipAddress = findViewById(R.id.settings_ip_address);
        about = findViewById(R.id.settings_about);

        ipAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SQLiteDatabase database = openOrCreateDatabase("escape_control", MODE_PRIVATE, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.ip_address_dialog, null);

                final EditText editText = customLayout.findViewById(R.id.editText);
                Cursor cursor = database.rawQuery("SELECT ip FROM IPAddress WHERE id = 1", null);
                cursor.moveToFirst();
                String currentIpAddress = cursor.getString(0);
                cursor.close();

                editText.setHint("Current IP Address: "+ currentIpAddress);

                builder.setView(customLayout);
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendDialogDataToActivity(editText.getText().toString(), database);
                        database.close();
                    }
                });
                builder.setNegativeButton("Default", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendDialogDataToActivity("192.168.11.100", database);
                        database.close();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.about_custom_dialog, null);

                builder.setView(customLayout);
                builder.setCancelable(true);

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void sendDialogDataToActivity(String data, SQLiteDatabase database){
        if (validateIpAddress(data)) {

            database.execSQL("UPDATE IPAddress SET ip = '" + data + "' WHERE id = 1;");
            Toast.makeText(this, "Server IP Address set to " + data, Toast.LENGTH_SHORT).show();
            database.close();
        }
        else{
            Toast.makeText(this, "Invalid IP Address. Please re-enter", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean validateIpAddress(final String ip){
        final String PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}