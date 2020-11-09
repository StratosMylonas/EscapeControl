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

public class Mansion extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Timer timer;
    private Mansion.AsyncDataClass jsonAsync;
    String ipAddress;

    Button tarrot_combination_1_btn, tarrot_combination_2_btn, tarrot_combination_3_btn, radio_on_btn,
            doll_btn, shelf_1_btn, shelf_2_btn, window_doors_btn, exit_btn, reset_btn;

    String mirror_drawer_str = "0", mirror_cabinet_str = "0", living_room_door_str = "0",
            kids_room_door_str = "0", passage_str = "0", window_doors_str = "0", tarrot_combination_1_btn_str = "0",
            tarrot_combination_2_btn_str = "0", tarrot_combination_3_btn_str = "0", radio_on_btn_str = "0",
            doll_btn_str = "0", shelf_1_btn_str = "0", shelf_2_btn_str = "0",
            window_doors_btn_str = "0", exit_btn_str = "0", reset_btn_str = "0";

    TextView tarrot_combination_1_txt, tarrot_combination_2_txt, tarrot_combination_3_txt, radio_on_txt,
             doll_txt, shelf_1_txt, shelf_2_txt, window_doors_txt, exit_txt;

    String tarrot_combination_1_txt_str = "Off", tarrot_combination_2_txt_str = "Off", tarrot_combination_3_txt_str = "Off",
            radio_on_txt_str = "Off", doll_txt_str = "Off", shelf_1_txt_str = "Off", shelf_2_txt_str = "Off",
            window_doors_txt_str = "Off", exit_txt_str = "Off";

    TextView statusTxt;
    ImageView statusImg;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    boolean firstTimeLoading, endToast = false;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mansion);

        getIpAddress();
        findIds();
        animateStatusImage();
        setRepeatingAsyncTask();
        dialog = new ProgressDialog(Mansion.this);
        onClickListeners();
        updateTxts();
    }

    void animateStatusImage() {
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
        tarrot_combination_1_txt = findViewById(R.id.tarrot_combination_1_txt);
        tarrot_combination_2_txt = findViewById(R.id.tarrot_combination_2_txt);
        tarrot_combination_3_txt = findViewById(R.id.tarrot_combination_3_txt);
        radio_on_txt = findViewById(R.id.radio_on_txt);
        doll_txt = findViewById(R.id.doll_txt);
        shelf_1_txt = findViewById(R.id.shelf_1_txt);
        shelf_2_txt = findViewById(R.id.shelf_2_txt);
        window_doors_txt = findViewById(R.id.window_doors_txt);
        exit_txt = findViewById(R.id.exit_txt);

        //Buttons
        tarrot_combination_1_btn = findViewById(R.id.tarrot_combination_1_btn);
        tarrot_combination_2_btn = findViewById(R.id.tarrot_combination_2_btn);
        tarrot_combination_3_btn = findViewById(R.id.tarrot_combination_3_btn);
        radio_on_btn = findViewById(R.id.radio_on_btn);
        doll_btn = findViewById(R.id.doll_btn);
        shelf_1_btn = findViewById(R.id.shelf_1_btn);
        shelf_2_btn = findViewById(R.id.shelf_2_btn);
        window_doors_btn = findViewById(R.id.window_doors_btn);
        exit_btn = findViewById(R.id.exit_btn);
        reset_btn = findViewById(R.id.reset_btn);

        //Swipe Refrash
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
    }

    void onClickListeners(){
        tarrot_combination_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_1_btn_str.equals("1")) {
                    tarrot_combination_1_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    tarrot_combination_1_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        tarrot_combination_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_2_btn_str.equals("1")) {
                    tarrot_combination_2_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    tarrot_combination_2_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        tarrot_combination_3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_3_btn_str.equals("1")) {
                    tarrot_combination_3_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    tarrot_combination_3_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        radio_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_on_btn_str.equals("1")) {
                    radio_on_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    radio_on_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        doll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doll_btn_str.equals("1")) {
                    doll_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    doll_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        shelf_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shelf_1_btn_str.equals("1")) {
                    shelf_1_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    shelf_1_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        shelf_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shelf_2_btn_str.equals("1")) {
                    shelf_2_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    shelf_2_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        window_doors_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (window_doors_btn_str.equals("1")) {
                    window_doors_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    window_doors_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exit_btn_str.equals("1")) {
                    exit_btn_str = "0";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
                else {
                    exit_btn_str = "1";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mansion.this);
                alertDialogBuilder.setMessage("Do you want to reset?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reset_btn_str = "0";
                        tarrot_combination_1_btn_str = "0";
                        tarrot_combination_2_btn_str = "0";
                        tarrot_combination_3_btn_str = "0";
                        radio_on_btn_str = "0";
                        doll_btn_str = "0";
                        shelf_1_btn_str = "0";
                        shelf_2_btn_str = "0";
                        window_doors_btn_str = "0";
                        exit_btn_str = "0";

                        Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
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
        Mansion.this.finish();
    }

    boolean checkWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Mansion.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null) {
            if (!networkInfo.isConnected()){
                AlertDialog.Builder builder = new AlertDialog.Builder(Mansion.this);
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
                        Mansion.this.finish();
                    }
                });
                builder.setCancelable(false);
                builder.show();

            }

            return networkInfo.isConnected();
        }
        return false;
    }

    private class AsyncDataClass extends AsyncTask<Void, Void, MansionRoomStatus> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            if (firstTimeLoading) {
                dialog.setMessage("Loading...");
                dialog.show();
            }
        }

        @Override
        protected MansionRoomStatus doInBackground(Void... params) {
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
            HttpPost httpPost = new HttpPost("http://" + ipAddress + "/EscapeControl/dbconnector.php?table_name=haunted_mansion");
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
            List<MansionRoomStatus> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null) {
                return null;
            }
            MansionRoomStatus roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(MansionRoomStatus roomObject) {
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

            tarrot_combination_1_btn_str = roomObject.getTarrot_combination_1_btn();
            tarrot_combination_2_btn_str = roomObject.getTarrot_combination_2_btn();
            tarrot_combination_3_btn_str = roomObject.getTarrot_combination_3_btn();
            radio_on_btn_str = roomObject.getRadio_on_btn();
            doll_btn_str = roomObject.getDoll_btn();
            shelf_1_btn_str = roomObject.getShelf_1_btn();
            shelf_2_btn_str = roomObject.getShelf_2_btn();
            window_doors_btn_str = roomObject.getWindow_doors_btn();
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

        private List<MansionRoomStatus> returnParsedJsonObject(String result) {
            List<MansionRoomStatus> jsonObject = new ArrayList<>();
            JSONArray jsonArray = null;
            MansionRoomStatus newItemObject;

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
                    String mirror_drawer = jsonChildNode.getString("mirror_drawer");
                    String mirror_cabinet = jsonChildNode.getString("mirror_cabinet");
                    String living_room_door = jsonChildNode.getString("living_room_door");
                    String kids_room_door = jsonChildNode.getString("kids_room_door");
                    String passage = jsonChildNode.getString("passage");
                    String window_doors = jsonChildNode.getString("window_doors");
                    String tarrot_combination_1_btn = jsonChildNode.getString("tarrot_combination_1_btn");
                    String tarrot_combination_2_btn = jsonChildNode.getString("tarrot_combination_2_btn");
                    String tarrot_combination_3_btn = jsonChildNode.getString("tarrot_combination_3_btn");
                    String radio_on_btn = jsonChildNode.getString("radio_on_btn");
                    String doll_btn = jsonChildNode.getString("doll_btn");
                    String shelf_1_btn = jsonChildNode.getString("shelf_1_btn");
                    String shelf_2_btn = jsonChildNode.getString("shelf_2_btn");
                    String window_doors_btn = jsonChildNode.getString("window_doors_btn");
                    String exit_btn = jsonChildNode.getString("exit_btn");
                    String reset_btn = jsonChildNode.getString("reset_btn");
                    newItemObject = new MansionRoomStatus(mirror_drawer, mirror_cabinet, living_room_door, kids_room_door, passage,
                                                          window_doors, tarrot_combination_1_btn, tarrot_combination_2_btn,
                                                          tarrot_combination_3_btn, radio_on_btn, doll_btn, shelf_1_btn, shelf_2_btn,
                                                          window_doors_btn, exit_btn, reset_btn);
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
                                jsonAsync = new Mansion.AsyncDataClass();
                                jsonAsync.execute();
                                Mansion.checkIPAddress checkIPAddress = new Mansion.checkIPAddress();
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
            String urlStr = "http://" + ipAddress + "/EscapeControl/updateMansion.php?id=" + 1 +
                    "&mirror_drawer=" + mirror_drawer_str +
                    "&mirror_cabinet=" + mirror_cabinet_str +
                    "&living_room_door=" + living_room_door_str +
                    "&kids_room_door=" + kids_room_door_str +
                    "&passage=" + passage_str +
                    "&window_doors=" + window_doors_str +
                    "&tarrot_combination_1_btn=" + tarrot_combination_1_btn_str +
                    "&tarrot_combination_2_btn=" + tarrot_combination_2_btn_str +
                    "&tarrot_combination_3_btn=" + tarrot_combination_3_btn_str +
                    "&radio_on_btn=" + radio_on_btn_str +
                    "&doll_btn=" + doll_btn_str +
                    "&shelf_1_btn=" + shelf_1_btn_str +
                    "&shelf_2_btn=" + shelf_2_btn_str +
                    "&window_doors_btn=" + window_doors_btn_str +
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
        if (tarrot_combination_1_btn_str.equals("0")){
            tarrot_combination_1_txt_str = "Off";
        }
        else{
            tarrot_combination_1_txt_str = "On";
        }

        if (tarrot_combination_2_btn_str.equals("0")){
            tarrot_combination_2_txt_str = "Off";
        }
        else{
            tarrot_combination_2_txt_str = "On";
        }

        if (tarrot_combination_3_btn_str.equals("0")){
            tarrot_combination_3_txt_str = "Off";
        }
        else{
            tarrot_combination_3_txt_str = "On";
        }

        if (radio_on_btn_str.equals("0")){
            radio_on_txt_str = "Off";
        }
        else{
            radio_on_txt_str = "On";
        }

        if (doll_btn_str.equals("0")){
            doll_txt_str = "Off";
        }
        else{
            doll_txt_str = "On";
        }

        if (shelf_1_btn_str.equals("0")){
            shelf_1_txt_str = "Off";
        }
        else{
            shelf_1_txt_str = "On";
        }

        if (shelf_2_btn_str.equals("0")){
            shelf_2_txt_str = "Off";
        }
        else{
            shelf_2_txt_str = "On";
        }

        if (window_doors_btn_str.equals("0")){
            window_doors_txt_str = "Off";
        }
        else{
            window_doors_txt_str = "On";
        }

        if (exit_btn_str.equals("0")){
            exit_txt_str = "Off";
        }
        else{
            exit_txt_str = "On";
        }

        tarrot_combination_1_txt.setText(tarrot_combination_1_txt_str);
        tarrot_combination_2_txt.setText(tarrot_combination_2_txt_str);
        tarrot_combination_3_txt.setText(tarrot_combination_3_txt_str);
        radio_on_txt.setText(radio_on_txt_str);
        doll_txt.setText(doll_txt_str);
        shelf_1_txt.setText(shelf_1_txt_str);
        shelf_2_txt.setText(shelf_2_txt_str);
        window_doors_txt.setText(window_doors_txt_str);
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