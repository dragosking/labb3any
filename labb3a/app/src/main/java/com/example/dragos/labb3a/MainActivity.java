package com.example.dragos.labb3a;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private double x,y,z;
    private double preX,preY;
    private TextView textX,textY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textX=(TextView) findViewById(R.id.outputX);
        textY=(TextView) findViewById(R.id.outputY);

        manager=(SensorManager) getSystemService(SENSOR_SERVICE);
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),10000);
    }

    private void filterData(double x,double y){
        float kFilteringFactor = 0.01f;
        double Xvalue = (x*kFilteringFactor)+(preX*(1.0-kFilteringFactor));
        preX=Xvalue;
        double testX=preX*9.56;
        int test=(int) Math.round(testX);

        double Yvalue = (y*kFilteringFactor)+(preY*(1.0-kFilteringFactor));
        preY=Yvalue;


        z=Math.toDegrees(preX);
        double pitch = Math.atan(x/Math.sqrt(Math.pow(y,2) + Math.pow(z,2)));
       // int test=(int) Math.round(preX);
        textX.setText(Double.toString(test));
        textY.setText(Double.toString(preY));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            x=event.values[0];
            y=event.values[1];
            filterData(x,y);
            //int test=(int) Math.round(z);
            //showToast(Integer.toString(test));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        manager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume(){
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }


    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
