package com.omer.constems_ai_assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.button.MaterialButton;


public class Recording_page extends AppCompatActivity implements SensorEventListener {
private SensorManager sensorManager;
private Sensor step_counter;

    LocationManager locationManager;
int firststeoCount=0;
int secondsteoCount;
int st=0;
int step_sum=0;
boolean first=true;
private boolean iscountersensorpresnt;
private String videourl,startcor,endcor;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
private MaterialButton save;
    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_page);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
save=findViewById(R.id.save);
        //viewmodal = ViewModelProviders.of(this).get(ViewModel.class);
        start_recording();
        start_Step_tracking();
        dbHandler = new DbHandler(Recording_page.this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("test save", "onClick: step"+(secondsteoCount-firststeoCount)+" Video url "+videourl+" start loc "+startcor+" end loc "+getLocation());
               // RecordModel recordModel=new RecordModel(videourl,String.valueOf(secondsteoCount-firststeoCount),startcor,endcor);
                dbHandler.addnewRecord(videourl,String.valueOf(secondsteoCount-firststeoCount),startcor,getLocation());
                Log.d("test data", "onClick: "+dbHandler.readRecord().size());
                Toast.makeText(Recording_page.this, "Record Saved ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Log.d("test_rec", "onActivityResult: "+"test");
    }

    private void start_Step_tracking() {
        Log.d("STEP_TRACK", "start_Step_tracking: ");
getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null) {
    step_counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    iscountersensorpresnt = true;
}
else {
    Toast.makeText(this, "Sensor Not available", Toast.LENGTH_SHORT).show();
    iscountersensorpresnt=false;
}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }

    private void start_recording() {
       //     recording.setBackgroundColor(getColor(R.color.green));
         //   Toast.makeText(this, "Recording Start", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
             startcor=getLocation();
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);

            startActivityForResult(intent,1);
        }

  
    private String getLocation() {
String latlon="";
       
        String latitude, longitude;
        if (ActivityCompat.checkSelfPermission(
                Recording_page.this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                Recording_page.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                latlon=latitude+","+longitude;
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
        return latlon;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& requestCode==1){
            videourl=data.getData().toString();
            Log.d("test video", "onActivityResult: "+videourl);

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
if(event.sensor==step_counter){


 if(first){
     firststeoCount=(int) event.values[0];
     first=false;
 }
 else {
     secondsteoCount=(int) event.values[0];
 }
    Log.d("test step", "onSensorChanged: "+firststeoCount+" "+secondsteoCount);




}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("resume", "onResume: ");
if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
    sensorManager.registerListener(this,step_counter,SensorManager.SENSOR_DELAY_NORMAL);

}
    }

    @Override
    protected void onPause() {
        Log.d("pause", "onPause: ");
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.unregisterListener(this,step_counter);
        }

    }
}
