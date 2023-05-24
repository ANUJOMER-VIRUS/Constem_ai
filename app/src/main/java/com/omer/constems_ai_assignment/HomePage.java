package com.omer.constems_ai_assignment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.button.MaterialButton;

public class HomePage extends AppCompatActivity {
boolean recording_start=false;
MaterialButton recording,video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recording=findViewById(R.id.startRec);
        video=findViewById(R.id.recordedVideo);
        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               start_recording();

            }
        });
          video.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i=new Intent(getApplicationContext(),recorded_video.class);
                  startActivity(i);
              }
          });
     //     start_recording();
    }

    private void start_recording() {
        recording.setBackgroundColor(getColor(R.color.green));
        Toast.makeText(this, "Recording Start", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomePage.this,Recording_page.class));
        // Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10);

        // startActivityForResult(intent,1);
    }

    private void pause_recording() {
     //  recording.setBackgroundColor(getColor(R.color.red));
        Toast.makeText(this, "Recording End", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& requestCode==1){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            VideoView view=new VideoView(this);
            view.setVideoURI(data.getData());
            view.start();
            recording_start=false;
            recording.setBackgroundColor(getColor(R.color.red));
            builder.setView(view).show();
            Log.d("test", "onActivityResult: "+"test");
        }
    }
}