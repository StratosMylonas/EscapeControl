package com.str.escapecontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Mansion extends AppCompatActivity {

    private Timer timer;
    private Mansion.AsyncDataClass jsonAsync;
    TextView mirror_drawer, mirror_cabinet, living_room_door, kids_room_door, passage, window_doors;
    Button tarrot_combination_1_btn, tarrot_combination_2_btn, tarrot_combination_3_btn, radio_on_btn,
           doll_btn, shelf_1_btn, shelf_2_btn, window_doors_btn, exit_btn, reset_btn;
    String mirror_drawer_str = "off", mirror_cabinet_str = "off", living_room_door_str = "off",
           kids_room_door_str = "off", passage_str = "off", window_doors_str = "off", tarrot_combination_1_btn_str = "off",
           tarrot_combination_2_btn_str = "off", tarrot_combination_3_btn_str = "off", radio_on_btn_str = "off",
           doll_btn_str = "off", shelf_1_btn_str = "off",  shelf_2_btn_str = "off",
           window_doors_btn_str = "off", exit_btn_str = "off", reset_btn_str = "off";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mansion);
        findIds();
        setRepeatingAsyncTask();
        onClickListeners();;
    }

    void findIds(){
        //TextViews
        mirror_drawer = findViewById(R.id.mirror_drawer);
        mirror_cabinet = findViewById(R.id.mirror_cabinet);
        living_room_door = findViewById(R.id.living_room_door);
        kids_room_door = findViewById(R.id.kids_room_door);
        passage = findViewById(R.id.passage);
        window_doors = findViewById(R.id.window_doors);

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
    }

    void onClickListeners(){
        tarrot_combination_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_1_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tarrot_combination_1_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        tarrot_combination_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_2_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tarrot_combination_2_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        tarrot_combination_3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tarrot_combination_3_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    tarrot_combination_3_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        radio_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_on_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    radio_on_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        doll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doll_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    doll_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        shelf_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shelf_1_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    shelf_1_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        shelf_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shelf_2_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    shelf_2_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        window_doors_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (window_doors_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    window_doors_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exit_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    exit_btn_str = "on";
                    Mansion.UpdateDatabaseAsyncTask updateDatabaseAsyncTask = new Mansion.UpdateDatabaseAsyncTask();
                    updateDatabaseAsyncTask.execute("");
                }
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset_btn_str.equals("on")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already pressed", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Mansion.this);
                    alertDialogBuilder.setMessage("Do you want to reset?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset_btn_str = "on";
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
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (jsonAsync != null){
            jsonAsync.cancel(true);
        }
        timer.cancel();
        Mansion.this.finish();
    }

    private class AsyncDataClass extends AsyncTask<String, Void, MansionRoomStatus> {

        @Override
        protected MansionRoomStatus doInBackground(String... params){
            byte[] address = {(byte) 192, (byte) 168, (byte) 1, (byte) 199};
            try {
                InetAddress addr = InetAddress.getByAddress(address);
                if (!addr.isReachable(2000)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Network server not reachable. Please make sure you're connected to the right network", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost = new HttpPost("http://192.168.1.199/EscapeControl/dbconnector_mansion.php");
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

            if (jsonResult.equals("")) {
                return null;
            }

            System.out.println("Resulted Value: " + jsonResult);
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
                return;
            }

            mirror_drawer.setText("Mirror Drawer: " + roomObject.getMirror_drawer());
            mirror_cabinet.setText("Mirror Cabinet: " + roomObject.getMirror_cabinet());
            living_room_door.setText("Living Room: " + roomObject.getLiving_room_door());
            kids_room_door.setText("Kid's Room Door: " + roomObject.getKids_room_door());
            passage.setText("Passage: " + roomObject.getPassage());
            window_doors.setText("Window Doors: " + roomObject.getWindow_doors());
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
                    newItemObject = new MansionRoomStatus(mirror_drawer, mirror_cabinet, living_room_door, kids_room_door,
                                                          passage, window_doors, tarrot_combination_1_btn, tarrot_combination_2_btn,
                                                          tarrot_combination_3_btn, radio_on_btn, doll_btn, shelf_1_btn, shelf_2_btn,
                                                          window_doors_btn, exit_btn, reset_btn);
                    jsonObject.add(newItemObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }
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
                            jsonAsync = new Mansion.AsyncDataClass();
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

    private class UpdateDatabaseAsyncTask extends AsyncTask<String, Void, Integer>{

        protected Integer doInBackground(String... params){
            HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            String urlStr = "http://192.168.1.199/EscapeControl/updateMansion.php?id=" + 1 +
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Data Sent");

            return 0;
        }
    }
}