package com.example.iot_project_java;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

  private Intent intentService;
  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
    GeneratedPluginRegistrant.registerWith(flutterEngine);

    intentService = new Intent(MainActivity.this,BleuthothService.class);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        startForegroundService(intentService);
    }else {
      startService(intentService);
    }
  }
}
