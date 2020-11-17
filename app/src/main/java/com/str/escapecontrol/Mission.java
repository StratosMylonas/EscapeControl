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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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

public class Mission extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Timer timer;
    private Mission.AsyncDataClass jsonAsync;

    String ipAddress;
    static int numberOfButtons = 16;

    SwitchCompat[] switchCompats = new SwitchCompat[numberOfButtons];
    TextView[] textViews = new TextView[numberOfButtons];
    String[] switchCombat_str = new String[numberOfButtons];
    String[] textViews_str = new String[numberOfButtons];

    Button resetBtn;
    String resetStr;

    TextView statusTxt;
    ImageView statusImg;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading, endToast = false, paused = false;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        getIpAddress();
        setup();
        animateStatusImage();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Mission.this);
        onClickListeners();
        updateSwitches();
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

    void setup(){
        //find ids
        switchCompats[0] = findViewById(R.id.lasersSwitch);
        switchCompats[1] = findViewById(R.id.mobilePhoneSwitch);
        switchCompats[2] = findViewById(R.id.officeDoorSwitch);
        switchCompats[3] = findViewById(R.id.deskCabinetSwitch);
        switchCompats[4] = findViewById(R.id.bookcaseCabinetSwitch);
        switchCompats[5] = findViewById(R.id.banskyPaintingSwitch);
        switchCompats[6] = findViewById(R.id.frameRfidSwitch);
        switchCompats[7] = findViewById(R.id.controlRoomDoorSwitch);
        switchCompats[8] = findViewById(R.id.vaultDoorSwitch);
        switchCompats[9] = findViewById(R.id.ventilationSwitch);
        switchCompats[10] = findViewById(R.id.moneyDropSwitch);
        switchCompats[11] = findViewById(R.id.panelNumbersSwitch);
        switchCompats[12] = findViewById(R.id.consoleLargeButtonsSwitch);
        switchCompats[13] = findViewById(R.id.consoleMetalButtonsSwitch);
        switchCompats[14] = findViewById(R.id.passageSwitch);
        switchCompats[15] = findViewById(R.id.exitSwitch);

        resetBtn = findViewById(R.id.reset_btn);

        //TextViews
        textViews[0] = findViewById(R.id.lasersStatusTxt);
        textViews[1] = findViewById(R.id.mobilePhoneStatusTxt);
        textViews[2] = findViewById(R.id.officeDoorStatusTxt);
        textViews[3] = findViewById(R.id.deskCabinetStatusTxt);
        textViews[4] = findViewById(R.id.bookcaseCabinetStatusTxt);
        textViews[5] = findViewById(R.id.banskyPaintingStatusTxt);
        textViews[6] = findViewById(R.id.frameRfidStatusTxt);
        textViews[7] = findViewById(R.id.controlRoomDoorStatusTxt);
        textViews[8] = findViewById(R.id.vaultDoorStatusTxt);
        textViews[9] = findViewById(R.id.ventilationStatusTxt);
        textViews[10] = findViewById(R.id.moneyDropStatusTxt);
        textViews[11] = findViewById(R.id.panelNumbersStatusTxt);
        textViews[12] = findViewById(R.id.consoleLargeButtonsStatusTxt);
        textViews[13] = findViewById(R.id.consoleMetalButtonsStatusTxt);
        textViews[14] = findViewById(R.id.passageStatusTxt);
        textViews[15] = findViewById(R.id.exitStatusTxt);

        statusTxt = findViewById(R.id.serverStatus);

        //Swipe Refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        //initialise arrays of texts
        for (int i=0; i<numberOfButtons; i++){
            switchCombat_str[i] = "0";
            textViews_str[i] = "Off";
        }

        resetStr = "0";
    }

    void onClickListeners(){

        for (int i=0; i<numberOfButtons; i++){
            switchCompats[i].setOnCheckedChangeListener(new MySwitchOnChangeStateListener(i) {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (switchCombat_str[index].equals("1")){
                        switchCombat_str[index] = "0";
                    }
                    else{
                        switchCombat_str[index] = "1";
                    }

                    UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mission.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            });

            switchCompats[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN)
                        paused = true;
                    else if (motionEvent.getAction() == android.view.MotionEvent.ACTION_UP) {
                        paused = false;
                    }
                    return false;
                }
            });
        }

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resetStr.equals("0")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mission.this, R.style.MyAlertDialogStyle);
                    alertDialogBuilder.setMessage("Do you want to reset?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int index = 0; index < numberOfButtons; index++) {
                                switchCombat_str[index] = "0";
                            }
                            resetStr = "1";
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
                else{
                    showAToast("Already pressed");
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
        endToast = false;
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Mission.CONNECTIVITY_SERVICE);
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

    private class AsyncDataClass extends AsyncTask<Void, Void, RoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected RoomStatus doInBackground(Void... params) {
            try {
                InetAddress addr = InetAddress.getByName(ipAddress);
                if (!addr.isReachable(2000)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showAToast("Server not reachable\nCheck IP Address from settings");
                            setStatus(false);
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
                        setStatus(false);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://" + ipAddress + "/EscapeControl/dbconnector.php?table_name=mission");
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
                        setStatus(false);
                    }
                });
            } catch (HttpHostConnectException e){
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAToast("Connection to server failed\nCheck IP Address from settings");
                        setStatus(false);
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
            List<RoomStatus> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null) {
                return null;
            }
            RoomStatus roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(RoomStatus roomObject) {
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

            for (int i=0; i<numberOfButtons; i++) {
                switchCombat_str[i] = roomObject.getBtnString(i);
            }
            resetStr = roomObject.getBtnString(numberOfButtons);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    endToast = false;
                    setStatus(true);
                    updateSwitches();
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

        private List<RoomStatus> returnParsedJsonObject(String result) {
            List<RoomStatus> jsonObject = new ArrayList<>();
            JSONArray jsonArray = null;
            RoomStatus newItemObject;

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
                    String[] strings = new String[numberOfButtons+1];
                    strings[0] = jsonChildNode.getString("lasers_btn");
                    strings[1] = jsonChildNode.getString("mobile_phone_btn");
                    strings[2] = jsonChildNode.getString("office_door_btn");
                    strings[3] = jsonChildNode.getString("desk_cabinet_btn");
                    strings[4] = jsonChildNode.getString("bookcage_cabinet_btn");
                    strings[5] = jsonChildNode.getString("bansky_painting_btn");
                    strings[6] = jsonChildNode.getString("frame_rfid_btn");
                    strings[7] = jsonChildNode.getString("control_room_door_btn");
                    strings[8] = jsonChildNode.getString("vault_door_btn");
                    strings[9] = jsonChildNode.getString("ventilation_btn");
                    strings[10] = jsonChildNode.getString("money_drop_btn");
                    strings[11] = jsonChildNode.getString("panel_numbers_btn");
                    strings[12] = jsonChildNode.getString("console_large_buttons_btn");
                    strings[13] = jsonChildNode.getString("console_metal_buttons_btn");
                    strings[14] = jsonChildNode.getString("passage_btn");
                    strings[15] = jsonChildNode.getString("exit_btn");
                    strings[16] = jsonChildNode.getString("reset_btn");

                    newItemObject = new RoomStatus(strings, numberOfButtons);
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
                                if (!paused) {
                                    jsonAsync = new Mission.AsyncDataClass();
                                    jsonAsync.execute();
                                    Mission.checkIPAddress checkIPAddress = new Mission.checkIPAddress();
                                    checkIPAddress.execute();
                                }
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
            setStatus(false);
        }
    }

    private class UpdateDatabaseAsyncTask extends AsyncTask<String, Void, Integer>{

        protected Integer doInBackground(String... params){
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            String urlStr = "http://" + ipAddress + "/EscapeControl/updateMission.php?id=" + 1 +
                    "&lasers_btn=" + switchCombat_str[0] +
                    "&mobile_phone_btn=" + switchCombat_str[1] +
                    "&office_door_btn=" + switchCombat_str[2] +
                    "&desk_cabinet_btn=" + switchCombat_str[3] +
                    "&bookcage_cabinet_btn=" + switchCombat_str[4] +
                    "&bansky_painting_btn=" + switchCombat_str[5] +
                    "&frame_rfid_btn=" + switchCombat_str[6] +
                    "&control_room_door_btn=" + switchCombat_str[7] +
                    "&vault_door_btn=" + switchCombat_str[8] +
                    "&ventilation_btn=" + switchCombat_str[9] +
                    "&money_drop_btn=" + switchCombat_str[10] +
                    "&panel_numbers_btn=" + switchCombat_str[11] +
                    "&console_large_buttons_btn=" + switchCombat_str[12] +
                    "&console_metal_buttons_btn=" + switchCombat_str[13] +
                    "&passage_btn=" + switchCombat_str[14] +
                    "&exit_btn=" + switchCombat_str[15] +
                    "&reset_btn=" + resetStr;
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
                                setStatus(false);
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

    public void updateSwitches(){
        for (int i=0; i<numberOfButtons; i++){
            switchCompats[i].setOnCheckedChangeListener(null);
            if (switchCombat_str[i].equals("0")){
                textViews_str[i] = "Off";
                switchCompats[i].setChecked(false);
            }
            else{
                textViews_str[i] = "On";
                switchCompats[i].setChecked(true);
            }
            onClickListeners();
            textViews[i].setText(textViews_str[i]);
        }
    }

    public void showAToast (String message){
        if (!endToast) {
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
            endToast = true;
        }
    }
}