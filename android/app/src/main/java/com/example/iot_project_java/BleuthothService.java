package com.example.iot_project_java;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;



public class BleuthothService extends Service {

    private BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    private Handler handler;
    private Runnable runnable;
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            /*switch (action){
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Toast.makeText(context, "Discovery started !!!", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Toast.makeText(context, "Discorvery finished !!!", Toast.LENGTH_SHORT).show();
                    bluetoothAdapter.startDiscovery();
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(context, device.getAddress()+"\n"+device.getName(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }*/
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                Toast.makeText(context, "Discovery started !!!", Toast.LENGTH_SHORT).show();
            }
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                Toast.makeText(context, "Discovery finished !!!", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.startDiscovery();
            }
            Toast.makeText(context, action, Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();

       if(bluetoothAdapter == null){
           Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
       }
       if (!bluetoothAdapter.isEnabled()){
           Toast.makeText(this, "Please enable your bluetooth", Toast.LENGTH_SHORT).show();
       }
       if (bluetoothAdapter.isDiscovering()){
           bluetoothAdapter.cancelDiscovery();
       }

       bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.EXTRA_DEVICE);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);



        this.registerReceiver(broadcastReceiver,filter);
        bluetoothAdapter.startDiscovery();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "bleuthoth")
                    .setContentText("Bleuthoth Services are Running in the background")
                    .setContentTitle("Bleuthoth Service")
                    .setSmallIcon(R.drawable.ic_baseline_bluetooth_24);

            startForeground(101,builder.build());
        }
    }

    public synchronized void startScan(){
        if(bluetoothAdapter.isDiscovering())
            this.bluetoothAdapter.cancelDiscovery();
        if (!bluetoothAdapter.isDiscovering())
            this.bluetoothAdapter.startDiscovery();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        bluetoothAdapter.cancelDiscovery();
        bluetoothAdapter.startDiscovery();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        runnable.run();

        return super.onStartCommand(intent, flags, startId);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
