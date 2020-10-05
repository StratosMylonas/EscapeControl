package com.str.escapecontrol;

import android.app.ProgressDialog;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class UpdateRoom extends AppCompatActivity {
    String HttpURL = "192.168.1.199/updateRoom.php";
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String idHolder, doorLockStateHolder;

    public void UpdateDoorLockState(int id, final String state){
        class RoomUpdateRecord extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("id",params[0]);
                hashMap.put("doorLockState",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }

            RoomUpdateRecord roomUpdateRecord = new RoomUpdateRecord();
        }
    }
}
