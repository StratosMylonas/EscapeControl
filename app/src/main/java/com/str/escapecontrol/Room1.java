package com.str.escapecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
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

public class Room1 extends AppCompatActivity {

    private Timer timer;
    private Room1.AsyncDataClass jsonAsync;
    TextView roomName, roomStatus, doorLockState, relay1State, relay2State;
    SwitchCompat doorLockSwitch, relay1Switch, relay2Switch;
    String doorLockStr = "locked", relay1Str = "off", relay2Str = "off";
    String roomId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);
        setRepeatingAsyncTask();

        doorLockSwitch = findViewById(R.id.doorLockSwitch);
        relay1Switch = findViewById(R.id.relay1Switch);
        relay2Switch = findViewById(R.id.relay2Switch);

        doorLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                timer.cancel();
                if (jsonAsync != null) {
                    if (jsonAsync.getStatus() == AsyncTask.Status.RUNNING) {
                        jsonAsync.cancel(true);
                    }
                    if (doorLockSwitch.isChecked()) {
                        doorLockStr = "locked";
                    } else {
                        doorLockStr = "unlocked";
                    }
                    new UpdateDatabaseAsyncTask().execute();
                    setRepeatingAsyncTask();
                }
            }
        });

        relay1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                timer.cancel();
                if (jsonAsync != null) {
                    if (jsonAsync.getStatus() == AsyncTask.Status.RUNNING) {
                        jsonAsync.cancel(true);
                    }
                    if (relay1Switch.isChecked()) {
                        relay1Str = "on";
                    } else {
                        relay1Str = "off";
                    }
                    new UpdateDatabaseAsyncTask().execute();
                    setRepeatingAsyncTask();
                }
            }
        });

        relay2Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                timer.cancel();
                if (jsonAsync != null) {
                    jsonAsync.cancel(true);
                    if (relay2Switch.isChecked()) {
                        relay2Str = "on";
                    } else {
                        relay2Str = "off";
                    }
                    new UpdateDatabaseAsyncTask().execute();
                    setRepeatingAsyncTask();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        if (jsonAsync!= null){
            jsonAsync.cancel(true);
        }
        timer.cancel();
        Room1.this.finish();
    }

    private class AsyncDataClass extends AsyncTask<String, Void, MainMenuRoomObject> {

        @Override
        protected MainMenuRoomObject doInBackground(String... params) {
            byte[] address = {(byte)192, (byte)168, (byte) 1, (byte) 199};
            try {
                InetAddress addr = InetAddress.getByAddress(address);
                if (!addr.isReachable(2000)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Network server not reachable. Please make sure you're connected to the right network", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (UnknownHostException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

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

            if (jsonResult.equals("")){
                return null;
            }

            System.out.println("Resulted Value: " + jsonResult);
            List<MainMenuRoomObject> parsedObject = returnParsedJsonObject(jsonResult);
            if (parsedObject == null){
                return null;
            }
            MainMenuRoomObject roomObject;
            roomObject = parsedObject.get(0);

            return roomObject;
        }

        @Override
        protected void onPostExecute(MainMenuRoomObject roomObject) {
            super.onPostExecute(roomObject);

            if (roomObject == null){
                doorLockSwitch.setChecked(false);
                relay1Switch.setChecked(false);
                relay2Switch.setChecked(false);
                return;
            }

            roomName = findViewById(R.id.roomNameTextView);
            roomStatus = findViewById(R.id.roomStatusTextView);
            doorLockState = findViewById(R.id.doorLockStateTextView);
            relay1State = findViewById(R.id.relay1StateTextView);
            relay2State = findViewById(R.id.relay2StateTextView);

            doorLockSwitch = findViewById(R.id.doorLockSwitch);
            relay1Switch = findViewById(R.id.relay1Switch);
            relay2Switch = findViewById(R.id.relay2Switch);

            roomName.setText(roomObject.getRoomName());
            roomStatus.setText("Room Status: " + roomObject.getRoomStatus());
            doorLockState.setText("Door Lock: " + roomObject.getDoorLockState());
            relay1State.setText("Relay 1: " + roomObject.getRelay1State());
            relay2State.setText("Relay 2: " + roomObject.getRelay2State());

            if (roomObject.getDoorLockState().equals("locked")){
                doorLockSwitch.setChecked(true);
            }
            else{
                doorLockSwitch.setChecked(false);
            }

            if (roomObject.getRelay1State().equals("on")){
                relay1Switch.setChecked(true);
            }
            else{
                relay1Switch.setChecked(false);
            }

            if (roomObject.getRelay2State().equals("on")){
                relay2Switch.setChecked(true);
            }
            else{
                relay2Switch.setChecked(false);
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
        JSONArray jsonArray = null;
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
                    jsonAsync = new Room1.AsyncDataClass();
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
            String urlStr = "http://192.168.1.199/updateRoom.php?id=" + roomId + "&doorLockState=" + doorLockStr + "&relay1State=" + relay1Str + "&relay2State=" + relay2Str;
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