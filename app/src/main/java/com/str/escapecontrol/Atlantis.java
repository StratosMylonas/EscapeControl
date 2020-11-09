package com.str.escapecontrol;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class Atlantis extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Timer timer;
    private Atlantis.AsyncDataClass jsonAsync;
    String ipAddress;

    Button waterfall_btn, tritons_key_btn, column2_btn,
           column3_btn, room_door_1_2_btn, holy_molly_btn, poseidon_btn,
           hexagon_pattern_btn, hexagon_cabinets_btn, trident_unlock_btn,
           exit_btn, reset_btn;

    String  tritons_key_str = "0", room_door_1_2_str = "0", holy_molly_str = "0",
            poseidons_chest_str = "0", hexagon_cabinets_str = "0", waterfall_btn_str = "0",
            tritons_key_btn_str = "0", column2_btn_str = "0",
            column3_btn_str = "0", room_door_1_2_btn_str = "0", holy_molly_btn_str = "0",
            poseidon_btn_str = "0", hexagon_pattern_btn_str = "0", hexagon_cabinets_btn_str = "0",
            trident_unlock_btn_str = "0", exit_btn_str = "0", reset_btn_str = "0";

    TextView waterfall_txt, tritons_key_txt, column2_txt, column3_txt, room_door_1_2_txt,
             holy_molly_txt, poseidon_txt, hexagon_pattern_txt, hexagon_cabinets_txt, trident_unlock_txt,
             exit_txt;

    String  waterfall_txt_str = "Off", tritons_key_txt_str = "Off",
            column2_txt_str = "Off", column3_txt_str = "Off", room_door_1_2_txt_str = "Off",
            holy_molly_txt_str = "Off", poseidon_txt_str = "Off", hexagon_pattern_txt_str = "Off",
            hexagon_cabinets_txt_str = "Off", trident_unlock_txt_str = "Off", exit_txt_str = "Off";

    TextView statusTxt;
    ImageView statusImg;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading, endToast = false;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atlantis);

        getIpAddress();
        findIds();
        animateStatusImage();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Atlantis.this);
        onClickListeners();
        updateTxts();
    }

    void animateStatusImage(){
        statusTxt.setText("Status: Offline");
        statusImg = findViewById(R.id.statusImg);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(statusImg, "alpha", 1f, .3f);
        fadeOut.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(statusImg, "alpha", .3f, 1f);
        fadeIn.setDuration(1000);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn).after(fadeOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }
        });
        animatorSet.start();
    }

    void setStatus(boolean state){
        if (state){
            statusTxt.setText("Status: Online");
            statusImg.setImageResource(R.drawable.online);
        }
        else{
            statusTxt.setText("Status: Offline");
            statusImg.setImageResource(R.drawable.offline);
        }
    }

    void getIpAddress(){
        final SQLiteDatabase database = openOrCreateDatabase("escape_control", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT ip FROM IPAddress WHERE id = 1", null);
        cursor.moveToFirst();
        ipAddress = cursor.getString(0);
        cursor.close();
        database.close();
    }

    void findIds(){
        //TextViews
        statusTxt = findViewById(R.id.serverStatus);
        waterfall_txt = findViewById(R.id.waterfall_txt);
        tritons_key_txt = findViewById(R.id.tritons_key_txt);
        column2_txt = findViewById(R.id.column2_txt);
        column3_txt = findViewById(R.id.column3_txt);
        room_door_1_2_txt = findViewById(R.id.room_door_1_2_txt);
        holy_molly_txt = findViewById(R.id.holy_molly_txt);
        poseidon_txt = findViewById(R.id.poseidon_txt);
        hexagon_pattern_txt = findViewById(R.id.hexagon_pattern_txt);
        hexagon_cabinets_txt = findViewById(R.id.hexagon_cabinets_txt);
        trident_unlock_txt = findViewById(R.id.trident_unlock_txt);
        exit_txt = findViewById(R.id.exit_txt);

        //Buttons
        waterfall_btn = findViewById(R.id.waterfall_btn);
        tritons_key_btn = findViewById(R.id.tritons_key_btn);
        column2_btn = findViewById(R.id.column2_btn);
        column3_btn = findViewById(R.id.column3_btn);
        room_door_1_2_btn = findViewById(R.id.room_door_1_2_btn);
        holy_molly_btn = findViewById(R.id.holy_molly_btn);
        poseidon_btn = findViewById(R.id.poseidon_btn);
        hexagon_pattern_btn = findViewById(R.id.hexagon_pattern_btn);
        hexagon_cabinets_btn = findViewById(R.id.hexagon_cabinets_btn);
        trident_unlock_btn = findViewById(R.id.trident_unlock_btn);
        exit_btn = findViewById(R.id.exit_btn);
        reset_btn = findViewById(R.id.reset_btn);

        //Swipe Refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    void onClickListeners(){
        waterfall_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterfall_btn_str.equals("1")) {
                    waterfall_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    waterfall_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        tritons_key_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tritons_key_btn_str.equals("1")) {
                    tritons_key_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    tritons_key_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        column2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column2_btn_str.equals("1")) {
                    column2_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    column2_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        column3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column3_btn_str.equals("1")) {
                    column3_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    column3_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        room_door_1_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (room_door_1_2_btn_str.equals("1")) {
                    room_door_1_2_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    room_door_1_2_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        holy_molly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holy_molly_btn_str.equals("1")) {
                    holy_molly_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    holy_molly_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        poseidon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (poseidon_btn_str.equals("1")) {
                    poseidon_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    poseidon_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        hexagon_pattern_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hexagon_pattern_btn_str.equals("1")) {
                    hexagon_pattern_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    hexagon_pattern_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        hexagon_cabinets_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hexagon_cabinets_btn_str.equals("1")) {
                    hexagon_cabinets_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    hexagon_cabinets_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        trident_unlock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trident_unlock_btn_str.equals("1")) {
                    trident_unlock_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    trident_unlock_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exit_btn_str.equals("1")) {
                    exit_btn_str = "0";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    exit_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Atlantis.this);
            alertDialogBuilder.setMessage("Do you want to reset?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                reset_btn_str = "0";
                waterfall_btn_str = "0";
                tritons_key_btn_str = "0";
                column2_btn_str = "0";
                column3_btn_str = "0";
                room_door_1_2_btn_str = "0";
                holy_molly_btn_str = "0";
                poseidon_btn_str = "0";
                hexagon_pattern_btn_str = "0";
                hexagon_cabinets_btn_str = "0";
                trident_unlock_btn_str = "0";
                exit_btn_str = "0";

                UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
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
        endToast = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed(){
        if (jsonAsync != null){
            jsonAsync.cancel(true);
        }
        timer.cancel();
        Atlantis.this.finish();
    }

    boolean checkWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Atlantis.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            if (!networkInfo.isConnected()){
                AlertDialog.Builder builder = new AlertDialog.Builder(Atlantis.this);
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
                        Atlantis.this.finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }

            return networkInfo.isConnected();
        }
        return false;
    }

    private class AsyncDataClass extends AsyncTask<Void, Void, AtlantisRoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected AtlantisRoomStatus doInBackground(Void... params) {
            try {
                InetAddress addr = InetAddress.getByName(ipAddress);
                if (!addr.isReachable(2000)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showAToast("Server not reachable\nCheck IP Address from settings");
                        }
                    });
                    return null;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAToast("Connection to server failed\nCheck IP Address from settings");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://" + ipAddress + "/EscapeControl/dbconnector.php?table_name=atlantis");
            String jsonResult = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                    //System.out.println("Returned Json object " + jsonResult);
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAToast("Connection to server failed");
                    }
                });
            } catch (HttpHostConnectException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAToast("Connection to server failed\nCheck IP Address from settings");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jsonResult.equals("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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

            //System.out.println("Resulted Value: " + jsonResult);
            List<AtlantisRoomStatus> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null) {
                return null;
            }
            AtlantisRoomStatus roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(AtlantisRoomStatus roomObject) {
            super.onPostExecute(roomObject);

            if (roomObject == null) {
                if (jsonAsync != null){
                    jsonAsync.cancel(true);
                }
                if (timer != null) {
                    timer.cancel();
                }
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setStatus(false);
                    }
                });

                return;
            }

            waterfall_btn_str = roomObject.getWaterfall_btn();
            tritons_key_btn_str = roomObject.getTritons_key_btn();
            column2_btn_str = roomObject.getColumn2_btn();
            column3_btn_str = roomObject.getColumn3_btn();
            room_door_1_2_btn_str = roomObject.getRoom_door_1_2_btn();
            holy_molly_btn_str = roomObject.getHoly_molly_btn();
            poseidon_btn_str = roomObject.getPoseidon_btn();
            hexagon_pattern_btn_str = roomObject.getHexagon_pattern_btn();
            hexagon_cabinets_btn_str = roomObject.getHexagon_cabinets_btn();
            trident_unlock_btn_str = roomObject.getTrident_unlock_btn();
            exit_btn_str = roomObject.getExit_btn();
            reset_btn_str = roomObject.getReset_btn();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    endToast = false;
                    setStatus(true);
                    updateTxts();
                }
            });
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

        private List<AtlantisRoomStatus> returnParsedJsonObject(String result) {
            List<AtlantisRoomStatus> jsonObject = new ArrayList<>();
            JSONArray jsonArray = null;
            AtlantisRoomStatus newItemObject;

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
                    String tritons_key = jsonChildNode.getString("tritons_key");
                    String room_door_1_2 = jsonChildNode.getString("room_door_1_2");
                    String holy_molly = jsonChildNode.getString("holy_molly");
                    String poseidons_chest = jsonChildNode.getString("poseidons_chest");
                    String hexagon_cabinets = jsonChildNode.getString("hexagon_cabinets");
                    String waterfall_btn = jsonChildNode.getString("waterfall_btn");
                    String tritons_key_btn = jsonChildNode.getString("tritons_key_btn");
                    String column1_btn = jsonChildNode.getString("column1_btn");
                    String column2_btn = jsonChildNode.getString("column2_btn");
                    String column3_btn = jsonChildNode.getString("column3_btn");
                    String room_door_1_2_btn = jsonChildNode.getString("room_door_1_2_btn");
                    String holy_molly_btn = jsonChildNode.getString("holy_molly_btn");
                    String poseidon_btn = jsonChildNode.getString("poseidon_btn");
                    String hexagon_pattern_btn = jsonChildNode.getString("hexagon_pattern_btn");
                    String hexagon_cabinets_btn = jsonChildNode.getString("hexagon_cabinets_btn");
                    String trident_unlock_btn = jsonChildNode.getString("trident_unlock_btn");
                    String exit_btn = jsonChildNode.getString("exit_btn");
                    String reset_btn = jsonChildNode.getString("reset_btn");
                    newItemObject = new AtlantisRoomStatus(tritons_key, room_door_1_2, holy_molly, poseidons_chest, hexagon_cabinets, waterfall_btn,
                                                           tritons_key_btn, column1_btn, column2_btn, column3_btn, room_door_1_2_btn, holy_molly_btn,
                                                           poseidon_btn, hexagon_pattern_btn, hexagon_cabinets_btn, trident_unlock_btn, exit_btn, reset_btn);
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
                                jsonAsync = new Atlantis.AsyncDataClass();
                                jsonAsync.execute();
                                Atlantis.checkIPAddress checkIPAddress = new Atlantis.checkIPAddress();
                                checkIPAddress.execute();
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
            showAToast("Connection to server failed");
        }
    }

    private class UpdateDatabaseAsyncTask extends AsyncTask<String, Void, Integer>{

        protected Integer doInBackground(String... params){
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            String urlStr = "http://" + ipAddress + "/EscapeControl/updateAtlantis.php?id=" + 1 +
                            "&tritons_key=" + tritons_key_str +
                            "&room_door_1_2=" + room_door_1_2_str +
                            "&holy_molly=" + holy_molly_str +
                            "&poseidons_chest=" + poseidons_chest_str +
                            "&hexagon_cabinets=" + hexagon_cabinets_str +
                            "&waterfall_btn=" + waterfall_btn_str +
                            "&tritons_key_btn=" + tritons_key_btn_str +
                            "&column1_btn=0"+
                            "&column2_btn=" + column2_btn_str +
                            "&column3_btn=" + column3_btn_str +
                            "&room_door_1_2_btn=" + room_door_1_2_btn_str +
                            "&holy_molly_btn=" + holy_molly_btn_str +
                            "&poseidon_btn=" + poseidon_btn_str +
                            "&hexagon_pattern_btn=" + hexagon_pattern_btn_str +
                            "&hexagon_cabinets_btn=" + hexagon_cabinets_btn_str +
                            "&trident_unlock_btn=" + trident_unlock_btn_str +
                            "&exit_btn=" + exit_btn_str +
                            "&reset_btn=" + reset_btn_str;
            HttpPost httpPost = new HttpPost(urlStr);
            try {
                HttpResponse response = httpClient.execute(httpPost);
                System.out.println((response.getEntity().getContent()).toString());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAToast("Failed. Try Again");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            endToast = false;
            System.out.println("Data Sent");

            return 0;
        }
    }

    private class checkIPAddress extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params){
            try {
                InetAddress addr = InetAddress.getByName(ipAddress);
                if (!addr.isReachable(1000)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!endToast) {
                                showAToast("Connection to server failed\nCheck IP Address from settings");
                                endToast = true;
                            }
                        }
                    });
                    return false;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    public void updateTxts(){
        if (waterfall_btn_str.equals("0")){
            waterfall_txt_str = "Off";
        }
        else{
            waterfall_txt_str = "On";
        }

        if (tritons_key_btn_str.equals("0")){
            tritons_key_txt_str = "Off";
        }
        else{
            tritons_key_txt_str = "On";
        }

        if (column2_btn_str.equals("0")){
            column2_txt_str = "Off";
        }
        else{
            column2_txt_str = "On";
        }

        if (column3_btn_str.equals("0")){
            column3_txt_str = "Off";
        }
        else{
            column3_txt_str = "On";
        }

        if (room_door_1_2_btn_str.equals("0")){
            room_door_1_2_txt_str = "Off";
        }
        else{
            room_door_1_2_txt_str = "On";
        }

        if (holy_molly_btn_str.equals("0")){
            holy_molly_txt_str = "Off";
        }
        else{
            holy_molly_txt_str = "On";
        }

        if (poseidon_btn_str.equals("0")){
            poseidon_txt_str = "Off";
        }
        else{
            poseidon_txt_str = "On";
        }

        if (hexagon_pattern_btn_str.equals("0")){
            hexagon_pattern_txt_str = "Off";
        }
        else{
            hexagon_pattern_txt_str = "On";
        }

        if (hexagon_cabinets_btn_str.equals("0")){
            hexagon_cabinets_txt_str = "Off";
        }
        else{
            hexagon_cabinets_txt_str = "On";
        }

        if (trident_unlock_btn_str.equals("0")){
            trident_unlock_txt_str = "Off";
        }
        else{
            trident_unlock_txt_str = "On";
        }

        if (exit_btn_str.equals("0")){
            exit_txt_str = "Off";
        }
        else{
            exit_txt_str = "On";
        }

        waterfall_txt.setText(waterfall_txt_str);
        tritons_key_txt.setText(tritons_key_txt_str);
        column2_txt.setText(column2_txt_str);
        column3_txt.setText(column3_txt_str);
        room_door_1_2_txt.setText(room_door_1_2_txt_str);
        holy_molly_txt.setText(holy_molly_txt_str);
        poseidon_txt.setText(poseidon_txt_str);
        hexagon_pattern_txt.setText(hexagon_pattern_txt_str);
        hexagon_cabinets_txt.setText(hexagon_cabinets_txt_str);
        trident_unlock_txt.setText(trident_unlock_txt_str);
        exit_txt.setText(exit_txt_str);
    }

    public void showAToast (String message){
        if (!endToast) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
            endToast = true;
        }
    }
}