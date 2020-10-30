package com.str.escapecontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Philosopher extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Timer timer;
    private Philosopher.AsyncDataClass jsonAsync;
    Button coins_prop_btn, harp_btn, passage_btn, knight_video_btn, holding_keys_door_btn,
           books_open_btn, books_close_btn, mirror_btn, exit_btn, reset_btn;
    String coins_prop_btn_str = "0", harp_btn_str = "0", passage_btn_str, knight_video_btn_str = "0",
            holding_keys_door_btn_str = "0", books_open_btn_str = "0", books_close_btn_str = "0",
            mirror_btn_str = "0", exit_btn_str = "0", reset_btn_str = "0";
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_philosopher);
        findIds();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Philosopher.this);
        onClickListeners();
    }

    void findIds(){
        coins_prop_btn = findViewById(R.id.philosophers_coins_prop_btn);
        harp_btn = findViewById(R.id.philosophers_harp_btn);
        passage_btn = findViewById(R.id.philosophers_passage_btn);
        knight_video_btn = findViewById(R.id.philosophers_knight_video_btn);
        holding_keys_door_btn = findViewById(R.id.philosophers_holding_keys_door_btn);
        books_open_btn = findViewById(R.id.philosophers_books_open_btn);
        books_close_btn = findViewById(R.id.philosophers_books_close_btn);
        mirror_btn = findViewById(R.id.philosophers_mirror_btn);
        exit_btn = findViewById(R.id.exit_btn);
        reset_btn = findViewById(R.id.reset_btn);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    void onClickListeners(){
        coins_prop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coins_prop_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    coins_prop_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        harp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (harp_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    harp_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        passage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passage_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    passage_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        knight_video_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (knight_video_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    knight_video_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        holding_keys_door_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holding_keys_door_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    holding_keys_door_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        books_open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (books_open_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    books_open_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        books_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (books_close_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    books_close_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        mirror_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mirror_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    mirror_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exit_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    exit_btn_str = "1";
                    Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Philosopher.this);
                    alertDialogBuilder.setMessage("Do you want to reset?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_btn_str = "1";
                            Philosopher.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Philosopher.UpdateDatabaseAsyncTask();
                            updateDatabaseAsyncTask.execute("");
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh(){
        if (jsonAsync != null){
            jsonAsync.cancel(true);
        }
        if (timer != null) {
            timer.cancel();
        }
        if (dialog.isShowing()){
            dialog.dismiss();
        }
        setRepeatingAsyncTask();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed(){
        if (jsonAsync != null){
            jsonAsync.cancel(true);
        }
        timer.cancel();
        Philosopher.this.finish();
    }

    boolean checkWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Mansion.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            if (!networkInfo.isConnected()){
                AlertDialog.Builder builder = new AlertDialog.Builder(Philosopher.this);
                builder.setMessage("WiFi connection required. Do you want to enable WiFi?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Philosopher.this.finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }

            return networkInfo.isConnected();
        }
        return false;
    }

    private class AsyncDataClass extends AsyncTask<String, Void, PhilosopherRoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected PhilosopherRoomStatus doInBackground(String... params) {
            byte[] address = {(byte) 192, (byte) 168, (byte) 1, (byte) 199};
            try {
                InetAddress addr = InetAddress.getByAddress(address);
                if (!addr.isReachable(2000)) {
                    return null;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://192.168.1.199/EscapeControl/dbconnector.php?table_name=philosopher");
            String jsonResult = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                    System.out.println("Returned Json object " + jsonResult);
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (HttpHostConnectException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jsonResult.equals("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(getApplicationContext(), "Connection to server failed", Toast.LENGTH_SHORT);
                        toast.show();
                        if (jsonAsync != null){
                            jsonAsync.cancel(true);
                        }
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });
                return null;
            }

            System.out.println("Resulted Value: " + jsonResult);
            List<PhilosopherRoomStatus> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null) {
                return null;
            }
            PhilosopherRoomStatus roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(PhilosopherRoomStatus roomObject) {
            super.onPostExecute(roomObject);

            if (roomObject == null) {
                return;
            }

            coins_prop_btn_str = roomObject.getCoins_prop_btn();
            harp_btn_str = roomObject.getHarp_btn();
            passage_btn_str = roomObject.getPassage_btn();
            knight_video_btn_str = roomObject.getKnight_video_btn();
            holding_keys_door_btn_str = roomObject.getHolding_keys_door_btn();
            books_close_btn_str = roomObject.getBooks_close_btn();
            books_open_btn_str = roomObject.getBooks_open_btn();
            mirror_btn_str = roomObject.getMirror_btn();
            reset_btn_str = roomObject.getReset_btn();
            exit_btn_str = roomObject.getExit_btn();
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

        private List<PhilosopherRoomStatus> returnParsedJsonObject(String result) {
            List<PhilosopherRoomStatus> jsonObject = new ArrayList<>();
            JSONArray jsonArray = null;
            PhilosopherRoomStatus newItemObject;

            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonArray == null) {
                return null;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonChildNode;
                try {
                    jsonChildNode = jsonArray.getJSONObject(i);
                    String coins_prop = jsonChildNode.getString("coins_prop_btn");
                    String harp_btn = jsonChildNode.getString("harp_btn");
                    String passage_btn = jsonChildNode.getString("passage_btn");
                    String knight_video_btn = jsonChildNode.getString("knight_video_btn");
                    String holding_keys_door_btn = jsonChildNode.getString("holding_keys_door_btn");
                    String books_open_btn = jsonChildNode.getString("books_open_btn");
                    String books_close_btn = jsonChildNode.getString("books_close_btn");
                    String mirror_btn = jsonChildNode.getString("mirror_btn");
                    String exit_btn = jsonChildNode.getString("exit_btn");
                    String reset_btn = jsonChildNode.getString("reset_btn");
                    newItemObject = new PhilosopherRoomStatus(coins_prop, harp_btn, passage_btn, knight_video_btn, holding_keys_door_btn,
                                                          books_open_btn, books_close_btn, mirror_btn, exit_btn, reset_btn);
                    jsonObject.add(newItemObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (firstTimeLoading) {
                dialog.dismiss();
                firstTimeLoading = false;
            }
            return jsonObject;
        }
    }

    private void setRepeatingAsyncTask(){

        final Handler handler = new Handler();
        timer = new Timer();
        firstTimeLoading = true;

        if (checkWifi()) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jsonAsync = new Philosopher.AsyncDataClass();
                                jsonAsync.execute("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };

            timer.schedule(task, 0, 1000);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Connection with Server failed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class UpdateDatabaseAsyncTask extends AsyncTask<String, Void, Integer>{

        protected Integer doInBackground(String... params){
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            String urlStr = "http://192.168.1.199/EscapeControl/updatePhilosopher.php?id=" + 1 +
                    "&coins_prop_btn=" + coins_prop_btn_str +
                    "&harp_btn=" + harp_btn_str +
                    "&passage_btn=" + passage_btn_str +
                    "&knight_video_btn=" + knight_video_btn_str +
                    "&holding_keys_door_btn=" + holding_keys_door_btn_str +
                    "&books_open_btn=" + books_open_btn_str +
                    "&books_close_btn=" + books_close_btn_str +
                    "&mirror_btn=" + mirror_btn_str +
                    "&passage_btn=" + passage_btn_str +
                    "&exit_btn=" + exit_btn_str +
                    "&reset_btn=" + reset_btn_str;
            HttpPost httpPost = new HttpPost(urlStr);
            try {
                HttpResponse response = httpClient.execute(httpPost);
                System.out.println((response.getEntity().getContent()).toString());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Data Sent");

            return 0;
        }
    }
}