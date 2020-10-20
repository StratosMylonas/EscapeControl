package com.str.escapecontrol;

import androidx.appcompat.app.AppCompatActivity;

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

public class Atlantis extends AppCompatActivity {

    private Timer timer;
    private Atlantis.AsyncDataClass jsonAsync;
    TextView tritons_key, room_door_1_2, holy_molly, poseidons_chest, hexagon_cabinets;
    Button waterfall_btn, tritons_key_btn, column1_btn, column2_btn,
           column3_btn, room_door_1_2_btn, holy_molly_btn, poseidon_btn,
           hexagon_pattern_btn, hexagon_cabinets_btn, trident_unlock_btn,
           exit_btn, reset_btn;
    String  tritons_key_str = "off", room_door_1_2_str = "off", holy_molly_str = "off",
            poseidons_chest_str = "off", hexagon_cabinets_str = "off", waterfall_btn_str = "off",
            tritons_key_btn_str = "off", column1_btn_str = "off", column2_btn_str = "off",
            column3_btn_str = "off", room_door_1_2_btn_str = "off", holy_molly_btn_str = "off",
            poseidon_btn_str = "off", hexagon_pattern_btn_str = "off", hexagon_cabinets_btn_str = "off",
            trident_unlock_btn_str = "off", exit_btn_str = "off", reset_btn_str = "off";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atlantis);
        findIds();
        setRepeatingAsyncTask();
        onClickListeners();
    }

    void findIds(){
        //TextViews
        tritons_key = findViewById(R.id.tritons_key);
        room_door_1_2 = findViewById(R.id.room_door_1_2);
        holy_molly = findViewById(R.id.holy_molly);
        poseidons_chest = findViewById(R.id.poseidons_chest);
        hexagon_cabinets = findViewById(R.id.hexagon_cabinets);

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
    }

    void onClickListeners(){
        waterfall_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waterfall_btn_str.equals("on"))
                    waterfall_btn_str = "off";
                else
                    waterfall_btn_str = "on";
            }
        });

        tritons_key_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tritons_key_btn_str.equals("on"))
                    tritons_key_btn_str = "off";
                else
                    tritons_key_btn_str = "on";
            }
        });

        column1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column1_btn_str.equals("on"))
                    column1_btn_str = "off";
                else
                    column1_btn_str = "on";
            }
        });

        column2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column2_btn_str.equals("on"))
                    column2_btn_str = "off";
                else
                    column2_btn_str = "on";
            }
        });

        column3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (column3_btn_str.equals("on"))
                    column3_btn_str = "off";
                else
                    column3_btn_str = "on";
            }
        });

        room_door_1_2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (room_door_1_2_btn_str.equals("on"))
                    room_door_1_2_btn_str = "off";
                else
                    room_door_1_2_btn_str = "on";
            }
        });

        holy_molly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holy_molly_btn_str.equals("on"))
                    holy_molly_btn_str = "off";
                else
                    holy_molly_btn_str = "on";
            }
        });

        poseidon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (poseidon_btn_str.equals("on"))
                    poseidon_btn_str = "off";
                else
                    poseidon_btn_str = "on";
            }
        });

        hexagon_pattern_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hexagon_pattern_btn_str.equals("on"))
                    hexagon_pattern_btn_str = "off";
                else
                    hexagon_pattern_btn_str = "on";
            }
        });

        hexagon_cabinets_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hexagon_cabinets_btn_str.equals("on"))
                    hexagon_cabinets_btn_str = "off";
                else
                    hexagon_cabinets_btn_str = "on";
            }
        });

        trident_unlock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trident_unlock_btn_str.equals("on"))
                    trident_unlock_btn_str = "off";
                else
                    trident_unlock_btn_str = "on";
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exit_btn_str.equals("on"))
                    exit_btn_str = "off";
                else
                    exit_btn_str = "on";
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset_btn_str.equals("on"))
                    reset_btn_str = "off";
                else
                    reset_btn_str = "on";
            }
        });

    }

    @Override
    public void onBackPressed(){
        if (jsonAsync != null){
            jsonAsync.cancel(true);
        }
        timer.cancel();
        Atlantis.this.finish();
    }

    private class AsyncDataClass extends AsyncTask<String, Void, AtlantisRoomStatus> {

        @Override
        protected AtlantisRoomStatus doInBackground(String... params) {
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
            HttpPost httpPost = new HttpPost("http://192.168.1.199/EscapeControl/dbconnector_atlantis.php");
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

            tritons_key.setText("Triton's Key: " + roomObject.getTritons_key_status());
            room_door_1_2.setText("1st - 2nd Room Door: " + roomObject.getRoom_door_1_2_status());
            holy_molly.setText("Holy Molly: " + roomObject.getHoly_molly_status());
            poseidons_chest.setText("Poseidon's Chest: " + roomObject.getPoseidons_chest_status());
            hexagon_cabinets.setText("Hexagon Cabinets: " + roomObject.getHexagon_cabinet_status());
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
                    String tritons_key_str = jsonChildNode.getString("tritons_key");
                    String room_door_1_2_str = jsonChildNode.getString("room_door_1_2");
                    String holy_molly_str = jsonChildNode.getString("holy_molly");
                    String poseidons_chest_str = jsonChildNode.getString("poseidons_chest");
                    String hexagon_cabinets_str = jsonChildNode.getString("hexagon_cabinets");
                    newItemObject = new AtlantisRoomStatus(tritons_key_str, room_door_1_2_str, holy_molly_str, poseidons_chest_str, hexagon_cabinets_str);
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
                            jsonAsync = new Atlantis.AsyncDataClass();
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
            String urlStr = "http://192.168.1.199/updateRoom.php?id=" + 0 +
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