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

public class Atlantis extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Timer timer;
    private Atlantis.AsyncDataClass jsonAsync;

    Button waterfall_btn, tritons_key_btn, column1_btn, column2_btn,
           column3_btn, room_door_1_2_btn, holy_molly_btn, poseidon_btn,
           hexagon_pattern_btn, hexagon_cabinets_btn, trident_unlock_btn,
           exit_btn, reset_btn;
    String  tritons_key_str = "0", room_door_1_2_str = "0", holy_molly_str = "0",
            poseidons_chest_str = "0", hexagon_cabinets_str = "0", waterfall_btn_str = "0",
            tritons_key_btn_str = "0", column1_btn_str = "0", column2_btn_str = "0",
            column3_btn_str = "0", room_door_1_2_btn_str = "0", holy_molly_btn_str = "0",
            poseidon_btn_str = "0", hexagon_pattern_btn_str = "0", hexagon_cabinets_btn_str = "0",
            trident_unlock_btn_str = "0", exit_btn_str = "0", reset_btn_str = "0";
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atlantis);
        findIds();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Atlantis.this);
        onClickListeners();
    }

    void findIds(){
        //TextViews
//        tritons_key = findViewById(R.id.tritons_key);
//        room_door_1_2 = findViewById(R.id.room_door_1_2);
//        holy_molly = findViewById(R.id.holy_molly);
//        poseidons_chest = findViewById(R.id.poseidons_chest);
//        hexagon_cabinets = findViewById(R.id.hexagon_cabinets);

        //Buttons
        waterfall_btn = findViewById(R.id.waterfall_btn);
        tritons_key_btn = findViewById(R.id.tritons_key_btn);
        column1_btn = findViewById(R.id.column1_btn);
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tritons_key_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        column1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column1_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    column1_btn_str = "1";
                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Atlantis.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        column2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column2_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
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
                if (reset_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Atlantis.this);
                    alertDialogBuilder.setMessage("Do you want to reset?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_btn_str = "1";
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
        Atlantis.this.finish();
    }

    boolean checkWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Mansion.CONNECTIVITY_SERVICE);
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

    private class AsyncDataClass extends AsyncTask<String, Void, AtlantisRoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected AtlantisRoomStatus doInBackground(String... params) {
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
            HttpPost httpPost = new HttpPost("http://192.168.1.199/EscapeControl/dbconnector.php?table_name=atlantis");
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
                return;
            }

//            tritons_key.setText("Triton's Key: " + roomObject.getTritons_key_status());
//            room_door_1_2.setText("1st - 2nd Room Door: " + roomObject.getRoom_door_1_2_status());
//            holy_molly.setText("Holy Molly: " + roomObject.getHoly_molly_status());
//            poseidons_chest.setText("Poseidon's Chest: " + roomObject.getPoseidons_chest_status());
//            hexagon_cabinets.setText("Hexagon Cabinets: " + roomObject.getHexagon_cabinets_status());
            waterfall_btn_str = roomObject.getWaterfall_btn();
            tritons_key_btn_str = roomObject.getTritons_key_btn();
            column1_btn_str = roomObject.getColumn1_btn();
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
            String urlStr = "http://192.168.1.199/EscapeControl/updateAtlantis.php?id=" + 1 +
                            "&tritons_key=" + tritons_key_str +
                            "&room_door_1_2=" + room_door_1_2_str +
                            "&holy_molly=" + holy_molly_str +
                            "&poseidons_chest=" + poseidons_chest_str +
                            "&hexagon_cabinets=" + hexagon_cabinets_str +
                            "&waterfall_btn=" + waterfall_btn_str +
                            "&tritons_key_btn=" + tritons_key_btn_str +
                            "&column1_btn=" + column1_btn_str +
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Data Sent");

            return 0;
        }
    }
}