package de.upb.soundgates.cosmic.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import de.upb.soundgates.cosmic.InteractionMethod;
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

    private final float deltaRotation = 0.001f;


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
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
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
            case Sensor.TYPE_LINEAR_ACCELERATION: // also see http://android-developers.blogspot.de/2010/09/one-screen-turn-deserves-another.html
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                Vector3d v = new Vector3d(x, y, z);

                if(v.length() < 0.6 * 9.81)
                    return;

                Vector3d[] charVectors = {
                        new Vector3d(1,0,0),
                        new Vector3d(-1,0,0),
                        new Vector3d(0,1,0),
                        new Vector3d(0,-1,0),
                        new Vector3d(0,0,1),
                        new Vector3d(0,0,-1)
                };
                Log.d(LOG_TAG, "Linear Acceleration Vector v="+v+" quantized to " + v.quantize(charVectors));

                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                if(Math.abs(rotationVector[0] - sensorEvent.values[0]) < deltaRotation &&
                   Math.abs(rotationVector[1] - sensorEvent.values[1]) < deltaRotation &&
                   Math.abs(rotationVector[2] - sensorEvent.values[2]) < deltaRotation)
                    return;
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

                Quaternion q = new Quaternion();
                q.setQuaternion(quaternion);
                heading = q.getHeading();
                attitude = q.getAttitude();

                //Log.i(LOG_TAG, "Rotary Switch: " + ((attitude+Math.PI/2) * 100 / Math.PI));
                float pHeading = (float)((heading+Math.PI) / (2 * Math.PI));
                float offset = 0.5f;
                if(pHeading+offset > 1)
                    pHeading = pHeading+offset - 1;
                else
                    pHeading += offset;
                updateModel(InteractionMethod.TILT, pHeading);

                break;
            case Sensor.TYPE_LIGHT:
                lux = sensorEvent.values[0];
                if(lux <= maxLux) {
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
