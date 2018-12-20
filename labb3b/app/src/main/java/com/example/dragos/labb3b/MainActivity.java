package com.example.dragos.labb3b;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ListView list;
    private List<String> listan;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        listan=new ArrayList<>();
        list=(ListView)findViewById(R.id.listan);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        bluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bluetoothAdapter = bluetoothManager.getAdapter();
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if(bluetoothAdapter==null || !bluetoothAdapter.isEnabled()){
            showToast("Sätt på bluetooth!");
        }else{
            listDevices();
        }
    }

    private void listDevices(){
        Set<BluetoothDevice> pairedDevices =bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device:pairedDevices){
                listan.add(device.getName());
            }
            adapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listan);
            list.setAdapter(adapter);
        }

    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
