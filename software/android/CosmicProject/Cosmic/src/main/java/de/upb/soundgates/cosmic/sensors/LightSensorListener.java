package de.upb.soundgates.cosmic.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by posewsky on 06.02.14.
 */
public class LightSensorListener extends AbstractSensorListener {
    private float lux;
    private float maxLux;

    public LightSensorListener(Context context) {
        super(context, Sensor.TYPE_LIGHT);

        lux = 0;
        maxLux = 30;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        lux = event.values[0];
        if(lux <= maxLux) {
            setChanged();
            notifyObservers();
        }
    }

    public float getLux() {
        return lux;
    }

    public float getLuxInvertedAsPercent() {
        return (maxLux - lux) / maxLux;
    }

    public void calibrateLightSensor() {
        maxLux = lux;
        Log.d(getClass().getName(), "Calibration of Light sensors: " + maxLux);
    }
}
