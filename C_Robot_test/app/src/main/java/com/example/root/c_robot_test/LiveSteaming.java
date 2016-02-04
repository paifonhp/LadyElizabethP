package com.example.root.c_robot_test;

import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LiveSteaming extends AppCompatActivity {


    Button btnShowLocation;
    TextView txtLocation;

        GPSTracker gps;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livebroadcast);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnShowLocation = (Button)findViewById(R.id.button);
        txtLocation = (TextView)findViewById(R.id.textView8);

        //Action ของ Button
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                gps = new GPSTracker(LiveSteaming.this);

                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    //เรียกใช้งานเมธอด getAddress
                    txtLocation.setText("ตำแหน่งของคุณคือ  \nLat: " + latitude + " Long: " + longitude + "\n" + getAddress(latitude, longitude));

                } else {
                    txtLocation.setText("อุปกรณ์์ของคุณ ปิด GPS");
                }

            }
        });


    }

    //get address
    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                //ซอย //ตำบล
                result.append(address.getAddressLine(0)).append("\n");
                //อำเภอ
                result.append(address.getLocality()).append("\n");
                //จังหวัด
                result.append(address.getAdminArea()).append("\n");
                //ชื่อประเทศ
                result.append(address.getCountryName()).append("\n");
                //รหัสประเทศ
                result.append(address.getCountryCode()).append("\n");
                //รหัสไปรษณีย์
                result.append(address.getPostalCode()).append("\n");
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }


}

