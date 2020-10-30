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

public class Mission extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Timer timer;
    private Mission.AsyncDataClass jsonAsync;
    Button lasers_btn, mobile_phone_btn, office_door_btn, desk_cabinet_btn, bookcase_cabinet_btn,
           bansky_painting_btn, frame_rfid_btn, control_room_door_btn, vault_door_btn, ventilation_btn,
           money_drop_btn, panel_numbers_btn, console_large_buttons_btn, console_metal_buttons_btn,
           passage_btn, exit_btn, reset_btn;
    String lasers_btn_str = "0", mobile_phone_btn_str = "0", office_door_btn_str = "0", desk_cabinet_btn_str = "0",
           bookcase_cabinet_btn_str = "0", bansky_painting_btn_str = "0", frame_rfid_btn_str = "0",
           control_room_door_btn_str = "0", vault_door_btn_str = "0", ventilation_btn_str = "0",
           money_drop_btn_str = "0", panel_numbers_btn_str = "0", console_large_buttons_btn_str = "0",
           console_metal_buttons_btn_str = "0", passage_btn_str = "0", exit_btn_str = "0", reset_btn_str = "0";
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        findIds();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Mission.this);
        onClickListeners();
    }

    void findIds(){
        lasers_btn = findViewById(R.id.mission_lasers_btn);
        mobile_phone_btn = findViewById(R.id.mission_mobile_phone_btn);
        office_door_btn = findViewById(R.id.mission_office_door_btn);
        desk_cabinet_btn = findViewById(R.id.mission_desk_cabinet_btn);
        bookcase_cabinet_btn = findViewById(R.id.mission_bookcase_cabinet_btn);
        bansky_painting_btn = findViewById(R.id.mission_bansky_painting_btn);
        frame_rfid_btn = findViewById(R.id.mission_frame_rfid_btn);
        control_room_door_btn = findViewById(R.id.mission_control_room_door_btn);
        vault_door_btn = findViewById(R.id.mission_vault_door_btn);
        ventilation_btn = findViewById(R.id.mission_ventilation_btn);
        money_drop_btn = findViewById(R.id.mission_money_drop_btn);
        panel_numbers_btn = findViewById(R.id.mission_panel_numbers_btn);
        console_large_buttons_btn = findViewById(R.id.mission_console_large_buttons_btn);
        console_metal_buttons_btn = findViewById(R.id.mission_console_metal_buttons_btn);
        passage_btn = findViewById(R.id.passage_btn);
        exit_btn = findViewById(R.id.exit_btn);
        reset_btn = findViewById(R.id.reset_btn);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    void onClickListeners(){
        lasers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lasers_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    lasers_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        mobile_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobile_phone_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    mobile_phone_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        office_door_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (office_door_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    office_door_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        desk_cabinet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (desk_cabinet_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    desk_cabinet_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        bookcase_cabinet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookcase_cabinet_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    bookcase_cabinet_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        bansky_painting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bansky_painting_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    bansky_painting_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        frame_rfid_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frame_rfid_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    frame_rfid_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        control_room_door_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (control_room_door_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    control_room_door_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        vault_door_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vault_door_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    vault_door_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        ventilation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ventilation_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    ventilation_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        money_drop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money_drop_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    money_drop_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        panel_numbers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (panel_numbers_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    panel_numbers_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        console_large_buttons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (console_large_buttons_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    console_large_buttons_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        console_metal_buttons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (console_metal_buttons_btn_str.equals("1")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    console_metal_buttons_btn_str = "1";
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
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
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
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
                    Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mission.this);
                    alertDialogBuilder.setMessage("Do you want to reset?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_btn_str = "1";
                            Mission.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
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
        Mission.this.finish();
    }

    boolean checkWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Mansion.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            if (!networkInfo.isConnected()){
                AlertDialog.Builder builder = new AlertDialog.Builder(Mission.this);
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
                        Mission.this.finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }

            return networkInfo.isConnected();
        }
        return false;
    }

    private class AsyncDataClass extends AsyncTask<String, Void, MissionRoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected MissionRoomStatus doInBackground(String... params) {
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
            HttpPost httpPost = new HttpPost("http://192.168.1.199/EscapeControl/dbconnector.php?table_name=mission");
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
            List<MissionRoomStatus> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null) {
                return null;
            }
            MissionRoomStatus roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(MissionRoomStatus roomObject) {
            super.onPostExecute(roomObject);

            if (roomObject == null) {
                return;
            }

            lasers_btn_str = roomObject.getLasers_btn();
            mobile_phone_btn_str = roomObject.getMobile_phone_btn();
            office_door_btn_str = roomObject.getOffice_door_btn();
            desk_cabinet_btn_str = roomObject.getDesk_cabinet_btn();
            bookcase_cabinet_btn_str = roomObject.getBookcase_cabinet_btn();
            bansky_painting_btn_str = roomObject.getBansky_painting_btn();
            frame_rfid_btn_str = roomObject.getFrame_rfid_btn();
            control_room_door_btn_str = roomObject.getControl_room_door_btn();
            vault_door_btn_str = roomObject.getVault_door_btn();
            ventilation_btn_str = roomObject.getVentilation_btn();
            money_drop_btn_str = roomObject.getMoney_drop_btn();
            panel_numbers_btn_str = roomObject.getPanel_numbers_btn();
            console_large_buttons_btn_str = roomObject.getConsole_large_buttons_btn();
            console_metal_buttons_btn_str = roomObject.getConsole_metal_buttons_btn();
            passage_btn_str = roomObject.getPassage_btn();
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

        private List<MissionRoomStatus> returnParsedJsonObject(String result) {
            List<MissionRoomStatus> jsonObject = new ArrayList<>();
            JSONArray jsonArray = null;
            MissionRoomStatus newItemObject;

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
                    String lasers_btn = jsonChildNode.getString("lasers_btn");
                    String mobile_phone_btn = jsonChildNode.getString("mobile_phone_btn");
                    String office_door_btn = jsonChildNode.getString("office_door_btn");
                    String desk_cabinet_btn = jsonChildNode.getString("desk_cabinet_btn");
                    String bookcage_cabinet_btn = jsonChildNode.getString("bookcage_cabinet_btn");
                    String bansky_painting_btn = jsonChildNode.getString("bansky_painting_btn");
                    String frame_rfid_btn = jsonChildNode.getString("frame_rfid_btn");
                    String control_room_door_btn = jsonChildNode.getString("control_room_door_btn");
                    String vault_door_btn = jsonChildNode.getString("vault_door_btn");
                    String ventilation_btn = jsonChildNode.getString("ventilation_btn");
                    String money_drop_btn = jsonChildNode.getString("money_drop_btn");
                    String panel_numbers_btn = jsonChildNode.getString("panel_numbers_btn");
                    String console_large_buttons_btn = jsonChildNode.getString("console_large_buttons_btn");
                    String console_metal_buttons_btn = jsonChildNode.getString("console_metal_buttons_btn");
                    String passage_btn = jsonChildNode.getString("passage_btn");
                    String exit_btn = jsonChildNode.getString("exit_btn");
                    String reset_btn = jsonChildNode.getString("reset_btn");
                    newItemObject = new MissionRoomStatus(lasers_btn, mobile_phone_btn, office_door_btn, desk_cabinet_btn,
                                                          bookcage_cabinet_btn, bansky_painting_btn, frame_rfid_btn, control_room_door_btn,
                                                          vault_door_btn, ventilation_btn, money_drop_btn, panel_numbers_btn,
                                                          console_large_buttons_btn, console_metal_buttons_btn, passage_btn, exit_btn, reset_btn);
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
                                jsonAsync = new Mission.AsyncDataClass();
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
            String urlStr = "http://192.168.1.199/EscapeControl/updateMission.php?id=" + 1 +
                    "&lasers_btn=" + lasers_btn_str +
                    "&mobile_phone_btn=" + mobile_phone_btn_str +
                    "&office_door_btn=" + office_door_btn_str +
                    "&desk_cabinet_btn=" + desk_cabinet_btn_str +
                    "&bookcage_cabinet_btn=" + bookcase_cabinet_btn_str +
                    "&bansky_painting_btn=" + bansky_painting_btn_str +
                    "&frame_rfid_btn=" + frame_rfid_btn_str +
                    "&control_room_door_btn=" + control_room_door_btn_str +
                    "&vault_door_btn=" + vault_door_btn_str +
                    "&ventilation_btn=" + ventilation_btn_str +
                    "&money_drop_btn=" + money_drop_btn_str +
                    "&panel_numbers_btn=" + panel_numbers_btn_str +
                    "&console_large_buttons_btn=" + console_large_buttons_btn_str +
                    "&console_metal_buttons_btn=" + console_metal_buttons_btn_str +
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