package de.upb.soundgates.cosmic.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import de.upb.soundgates.cosmic.sensor.util.Vector3d;

/**
 * Created by Thorbjörn Posewsky on 05.02.14.
 */
public class ShakeSensorListener extends AbstractSensorListener {
    private Vector3d acc;
    private float threshold;

    public ShakeSensorListener(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION);

        acc = new Vector3d(0,0,0);
        threshold = 0.6f * 9.81f;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        acc.x = event.values[0];
        acc.y = event.values[1];
        acc.z = event.values[1];

        if(acc.length() < threshold)
            return;

        setChanged();
        notifyObservers();
    }
}
