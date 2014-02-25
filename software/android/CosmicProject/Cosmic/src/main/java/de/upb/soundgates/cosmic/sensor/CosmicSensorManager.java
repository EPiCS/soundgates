package de.upb.soundgates.cosmic.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.sensor.util.Quaternion;
import de.upb.soundgates.cosmic.sensor.util.Vector3d;

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

    CharacteristicVectorStore charVectors;


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

        charVectors = new CharacteristicVectorStore();
        charVectors.addCharacteristicVector(new Vector3d(1,0,0),  "East");
        charVectors.addCharacteristicVector(new Vector3d(-1,0,0), "West");
        charVectors.addCharacteristicVector(new Vector3d(0,1,0),  "North");
        charVectors.addCharacteristicVector(new Vector3d(0,-1,0), "South");
        charVectors.addCharacteristicVector(new Vector3d(0,0,1),  "Up");
        charVectors.addCharacteristicVector(new Vector3d(0,0,-1), "Down");
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

    private class CharacteristicVector {
        public Vector3d vector;
        public String description;

        public CharacteristicVector(Vector3d vector, String description) {
            this.vector = vector;
            this.description = description;
        }
    }

    private class CharacteristicVectorStore {
        public List<CharacteristicVector> list;

        public CharacteristicVectorStore() {
            list = new ArrayList<CharacteristicVector>();
        }

        public void addCharacteristicVector(Vector3d v, String desc) {
            list.add(new CharacteristicVector(v, desc));
        }

        public Vector3d[] getVector3dAsArray() {
            Vector3d[] arr = new Vector3d[list.size()];
            for(int i = 0; i < list.size(); ++i)
                arr[i] = list.get(i).vector;
            return arr;
        }
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
                /*else
                {
                    updateModel(InteractionMethod.SHAKE, 1.0f);
                }*/

                float[] resultVec = new float[4];
                float[] R = new float[16];
                float[] RInv = new float[16];

                android.hardware.SensorManager.getRotationMatrixFromVector(R, rotationVector);
                //android.opengl.Matrix.invertM(RInv, 0, R, 0);
                android.opengl.Matrix.multiplyMV(resultVec, 0, R, 0, v.to4dFloatVector(), 0);

                Vector3d vWorld = new Vector3d(resultVec[0], resultVec[1], resultVec[2]);
                Vector3d[] vectors = this.charVectors.getVector3dAsArray();
                String desc = this.charVectors.list.get(vWorld.quantize(vectors)).description;
                Log.d(LOG_TAG, "Linear Acceleration Vector v="+vWorld+" quantized to " + desc);

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
                float offset = 0.75f;
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
