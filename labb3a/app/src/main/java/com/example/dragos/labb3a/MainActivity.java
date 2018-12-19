package com.example.dragos.labb3a;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private double x,y,z;
    private double preX,preY,preZ;
    private TextView textX,textY,textZ;
    private double shakePre,shakeAft,shake;
    private Display display;
    private String color;
    private int shakeCount;
    private long timeNow,timeBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color="#FF3F51B5";
        display = ((WindowManager) getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();

        textX=(TextView) findViewById(R.id.outputX);
       // textY=(TextView) findViewById(R.id.outputY);
        //textZ=(TextView) findViewById(R.id.outputZ);

        manager=(SensorManager) getSystemService(SENSOR_SERVICE);
        manager.registerListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),10000);
    }

    private void filterData(double x,double y,double z){
        float faktor = 0.01f;
        double Xvalue = (x*faktor)+(preX*(1.0-faktor));
        preX=Xvalue;
        double testX=preX*9.56;
        int outX=(int) Math.round(testX);

        double Yvalue = (y*faktor)+(preY*(1.0-faktor));
        preY=Yvalue;
        double testY=preY*9.56;
        int outY=(int) Math.round(testY);

        double Zvalue = (z*faktor)+(preZ*(1.0-faktor));
        preZ=Zvalue;
        double testZ=preZ*9.56;
        int outZ=(int) Math.round(testZ);

        outX=Math.abs(outX);
        outY=Math.abs(outY);
        outZ=Math.abs(outZ);
        calculateAngle(outX,outY,outZ);
    }

    private void detectShake(double x, double y, double z){
        float faktor = 0.01f;
        shakePre=shakeAft;
        shakeAft= Math.sqrt((x*x)+(y*y)+(z*z));
        double del=shakeAft-shakePre;
        shake=shake*faktor+del;
        long time=System.currentTimeMillis();


        if(shake>20){
            if(time-timeNow<1000){
                shakeCount++;
                if(shakeCount==1){
                    timeBefore=System.currentTimeMillis();
                }

                timeNow=System.currentTimeMillis();
                if(timeNow-timeBefore>1000) {
                    textX.setTextColor(Color.parseColor(color));
                }
            }else{
                timeBefore=0;
                shakeCount=0;
                timeNow=System.currentTimeMillis();
            }
        }
    }

    private void calculateAngle(int x, int y, int z){
        if(x>z){
            textX.setText(Integer.toString(x)+(char) 0x00B0);
        }else{
            textX.setText(Integer.toString(z)+(char) 0x00B0);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            switch (display.getRotation()){
                case Surface.ROTATION_0:
                    x=event.values[0];
                    y=event.values[1];
                    break;
                case Surface.ROTATION_90:
                    x=-event.values[1];
                    y=event.values[0];
                    break;
                case Surface.ROTATION_180:
                    x=-event.values[0];
                    y=-event.values[1];
                    break;
                case Surface.ROTATION_270:
                    x=event.values[1];
                    y=-event.values[0];
                    break;
            }

            z=event.values[2];
            detectShake(x,y,z);
            filterData(x,y,z);
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
