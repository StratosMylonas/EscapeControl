package com.str.escapecontrol;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ListView roomlist;
    private MainActivity.AsyncDataClass jsonAsync;
    private Timer timer;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkWifi(MainActivity.this)) {
                    if (jsonArray == null){
                        Toast toast = Toast.makeText(getApplicationContext(), "Connection Error. Please try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        if (jsonAsync.getStatus() == AsyncTask.Status.RUNNING) {
                            jsonAsync.cancel(true);
                        }
                        timer.cancel();
                        setRepeatingAsyncTask();
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Connection Error, please try again.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                pullToRefresh.setRefreshing(false);
            }
        });

        if (checkWifi(this)) {
            roomlist = findViewById(R.id.listView);
            setRepeatingAsyncTask();

            roomlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (jsonAsync.getStatus() == AsyncTask.Status.RUNNING) {
                        jsonAsync.cancel(true);
                    }
                    timer.cancel();
                    if (position == 0) {
                        if (jsonArray == null){
                            Toast toast = Toast.makeText(getApplicationContext(), "Connection Error. Please try again", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, Room1.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        if (jsonAsync.getStatus() == AsyncTask.Status.RUNNING) {
            jsonAsync.cancel(true);
        }
        timer.cancel();
        finish();
    }

    private boolean checkWifi(Activity context){
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("You need a WiFi connection to use this application. Please turn on WiFi")
                    .setTitle("Unable to connect")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MainActivity.this.finish();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
        return true;
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
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://192.168.1.199/dbconnector.php");
            String jsonResult = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                System.out.println("Returned Json object " + jsonResult);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);
            List<MainMenuRoomObject> parsedObject = returnParsedJsonObject(result);
            if (parsedObject != null){
                CustomAdapter jsonCustomAdapter = new CustomAdapter(MainActivity.this, parsedObject);
                int index = roomlist.getFirstVisiblePosition();
                View v = roomlist.getChildAt(0);
                int top = (v == null) ? 0 : (v.getTop() - roomlist.getPaddingTop());
                roomlist.setAdapter(jsonCustomAdapter);
                roomlist.setSelectionFromTop(index, top);
            }
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine;
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return answer;
        }
    }

    private List<MainMenuRoomObject> returnParsedJsonObject(String result){
        List<MainMenuRoomObject> jsonObject = new ArrayList<>();
        jsonArray = null;
        MainMenuRoomObject newItemObject;

        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray == null){
            return null;
        }

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonChildNode;
            try {
                jsonChildNode = jsonArray.getJSONObject(i);
                String roomName = jsonChildNode.getString("roomName");
                String roomStatus = jsonChildNode.getString("status");
                String doorLockState = jsonChildNode.getString("doorLockState");
                String relay1State = jsonChildNode.getString("relay1State");
                String relay2State = jsonChildNode.getString("relay2State");
                newItemObject = new MainMenuRoomObject(roomName, roomStatus, doorLockState, relay1State, relay2State);
                jsonObject.add(newItemObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private void setRepeatingAsyncTask(){

        final Handler handler = new Handler();
        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                try {
                    jsonAsync = new AsyncDataClass();
                    jsonAsync.execute("");
                } catch (Exception e){
                    e.printStackTrace();
                }
                }
            });
            }
        };

        timer.schedule(task, 0, 1000);
    }
}