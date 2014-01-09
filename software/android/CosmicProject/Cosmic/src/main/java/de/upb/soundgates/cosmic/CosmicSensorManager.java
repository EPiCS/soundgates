package de.upb.soundgates.cosmic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by posewsky on 08.01.14.
 */
public class CosmicSensorManager implements SensorEventListener {
    public static final String LOG_TAG = "Cosmic - SensorManager";

    private Context context;
    private SensorManager sensorManager;

    public float[] rotationVector;
    public float[] quaternion;

    public CosmicSensorManager(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        initListeners();

        rotationVector = new float[3];
        quaternion = new float[4];

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    set(new Quat4d(quaternion[0], quaternion[1], quaternion[2], quaternion[3]));

                    double rad2angleFactor = 180 / Math.PI;

                    Log.i(LOG_TAG,  "\n\nheading (y-axis of device): " + heading * rad2angleFactor +
                                    "\nattitude (z-axis of device): " + attitude  * rad2angleFactor +
                                    "\nbank (x-axis of device): " + bank  * rad2angleFactor);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initListeners() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL); // SensorManager.SENSOR_DELAY_FASTEST

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL); // SensorManager.SENSOR_DELAY_FASTEST
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                rotationVector[0] = sensorEvent.values[0];
                rotationVector[1] = sensorEvent.values[1];
                rotationVector[2] = sensorEvent.values[2];

                if(sensorEvent.values.length > 3) // optional value[3] is present
                {
                    quaternion[0] = sensorEvent.values[3];  // w
                    quaternion[1] = rotationVector[0];      // x
                    quaternion[2] = rotationVector[1];      // y
                    quaternion[3] = rotationVector[2];      // z
                }
                else
                    SensorManager.getQuaternionFromVector(quaternion, rotationVector);

                break;
        }
    }

    private class Quat4d {
        public float w, x, y, z;

        public Quat4d(float w, float x, float y, float z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public double heading, attitude, bank;

    public void set(Quat4d q1) {
        double sqw = q1.w*q1.w;
        double sqx = q1.x*q1.x;
        double sqy = q1.y*q1.y;
        double sqz = q1.z*q1.z;
        double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise is correction factor
        double test = q1.x*q1.y + q1.z*q1.w;
        if (test > 0.499*unit) { // singularity at north pole
            heading = 2 * Math.atan2(q1.x, q1.w);
            attitude = Math.PI/2;
            bank = 0;
            return;
        }
        if (test < -0.499*unit) { // singularity at south pole
            heading = -2 * Math.atan2(q1.x, q1.w);
            attitude = -Math.PI/2;
            bank = 0;
            return;
        }
        heading = Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, sqx - sqy - sqz + sqw);
        attitude = Math.asin(2 * test / unit);
        bank = Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, -sqx + sqy - sqz + sqw);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
