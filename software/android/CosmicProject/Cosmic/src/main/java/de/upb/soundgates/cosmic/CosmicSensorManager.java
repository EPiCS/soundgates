package de.upb.soundgates.cosmic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.osc.OSCSender;

/**
 * Created by posewsky on 08.01.14.
 */
public class CosmicSensorManager implements SensorEventListener {
    public static final String LOG_TAG = "Cosmic - SensorManager";
    private static CosmicSensorManager instance = null;

    private Context context;
    private SensorManager sensorManager;

    public float[] rotationVector;
    public float[] quaternion;

    public double heading, attitude, bank;

    public float lux;
    public float maxLux;


    public static CosmicSensorManager getInstance(Context context) {
        if(instance == null) {
            if(context == null)
                return null;
            instance = new CosmicSensorManager(context);
            return instance;
        }
        return instance;
    }

    private CosmicSensorManager(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        initListeners();

        rotationVector = new float[3];
        quaternion = new float[4];

        maxLux = 30;
    }

    private void initListeners() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL); // SensorManager.SENSOR_DELAY_FASTEST

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL); // SensorManager.SENSOR_DELAY_FASTEST

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                sensorManager.SENSOR_DELAY_FASTEST);
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
            case Sensor.TYPE_LIGHT:
                lux = sensorEvent.values[0];
                if(lux <= maxLux)
                {
                    float p = (maxLux - lux) / maxLux;
                    updateModel(InteractionMethod.LIGHT, p);
                }
                break;
        }
    }

    public void updateModel(final InteractionMethod im, final float percent) {
        new Thread(new Runnable() {
            public void run() {
                OSCMessageStore msg_store = OSCMessageStore.hasInstance();
                if(msg_store != null) {
                    for(OSCMessage msg : msg_store.getSelectedOSCMessageAsList()) {
                        if(msg.getInteractionMethod() == im) {
                            msg.setValueAsPercent(percent);
                            OSCSender.send(msg);
                        }
                    }
                }
            }
        }).start();
    }

    public void calibrateLightSensor() { maxLux = lux; Log.i(LOG_TAG, "Calibration of Light sensors: " + maxLux);}

    private class Quat4d {
        public float w, x, y, z;

        public Quat4d(float w, float x, float y, float z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double getHeading() {
            double sqw = w*w;
            double sqx = x*x;
            double sqy = y*y;
            double sqz = z*z;
            double unit = sqx + sqy + sqz + sqw;
            double test = x*y + z*w;
            if (test > 0.499*unit)
                return 2 * Math.atan2(x, w);
            if (test < -0.499*unit)
                return -2 * Math.atan2(x, w);
            return Math.atan2(2 * y * w - 2 * x * z, sqx - sqy - sqz + sqw);
        }

        double getAttitude() {
            double sqw = w*w;
            double sqx = x*x;
            double sqy = y*y;
            double sqz = z*z;
            double unit = sqx + sqy + sqz + sqw;
            double test = x*y + z*w;
            if (test > 0.499*unit)
                return Math.PI/2;
            if (test < -0.499*unit)
                return -Math.PI/2;
            return Math.asin(2 * test / unit);
        }

        double getBank() {
            double sqw = w*w;
            double sqx = x*x;
            double sqy = y*y;
            double sqz = z*z;
            double unit = sqx + sqy + sqz + sqw;
            double test = x*y + z*w;
            if (test > 0.499*unit)
                return 0;
            if (test < -0.499*unit)
                return 0;
            return Math.atan2(2 * x * w - 2 * y * z, -sqx + sqy - sqz + sqw);
        }

        /*public void set(Quat4d q1) {
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
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
