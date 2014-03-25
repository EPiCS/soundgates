package de.upb.soundgates.cosmic.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;


public class ProximitySensorListener extends AbstractSensorListener {
    private float value;

    public ProximitySensorListener(Context context) {
        super(context, Sensor.TYPE_PROXIMITY);

        value = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        float current_value = event.values[0];
        if(current_value != value) {
            value = current_value;

            setChanged();
            notifyObservers();
        }
    }

    public float getProximityValueInPercent() {
        return value / getSensorManager().getDefaultSensor(getSensorType()).getMaximumRange();
    }

    public float getProximityValue() {
        return value;
    }
}
