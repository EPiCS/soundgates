package de.upb.soundgates.cosmic.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Thorbjörn Posewsky on 06.02.14.
 */
public abstract class AbstractSensorListener extends Observable implements SensorEventListener {
    private SensorManager sensorManager;
    private int sensorType;

    static {
        listenerList = new ArrayList<AbstractSensorListener>();
    }

    private static ArrayList<AbstractSensorListener> listenerList;
    public static void unregisterListeners() {
        for(AbstractSensorListener listener : listenerList) {
            listener.unregisterListener();
        }
    }

    public AbstractSensorListener(Context context, int sensorType) {
        this.sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        this.sensorType = sensorType;

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(sensorType),
                SensorManager.SENSOR_DELAY_FASTEST);

        listenerList.add(this);
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    public int getSensorType() {
        return sensorType;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
