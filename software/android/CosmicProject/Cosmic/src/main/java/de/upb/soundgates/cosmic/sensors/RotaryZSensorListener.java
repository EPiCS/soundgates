package de.upb.soundgates.cosmic.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import de.upb.soundgates.cosmic.sensors.util.Vector3d;

/**
 * Created by Thorbjörn Posewsky on 05.02.14.
 */
public class RotaryZSensorListener extends AbstractSensorListener {
    private Vector3d rot;
    private float rotationInPercent;

    private float deltaRotation;
    private float rotationOffsetInPercent;

    public RotaryZSensorListener(Context context) {
        super(context, Sensor.TYPE_ROTATION_VECTOR);

        rot = new Vector3d(0,0,0);
        rotationInPercent = 0;

        deltaRotation = 0.001f;
        rotationOffsetInPercent = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        Vector3d newRot = new Vector3d(event.values[0], event.values[1], event.values[2]);
        if(newRot.componentLessDeltaTest(rot, deltaRotation))
            return;

        rot = newRot;

        float[] R = new float[16];
        float[] values = new float[3];
        SensorManager.getRotationMatrixFromVector(R, rot.toFloatVector());
        SensorManager.getOrientation(R, values);

        float azimuth = values[0];

        rotationInPercent = (float)((azimuth + Math.PI) / (2*Math.PI));

        setChanged();
        notifyObservers();
    }

    public void setStartOfRotation() {
        rotationOffsetInPercent = getRotationInPercent();
    }

    public float getRotationInPercent() {
        return rotationInPercent;
    }

    public float getRotationWithOffsetInPercent() {
        if(rotationOffsetInPercent <= rotationInPercent)
            return rotationInPercent - rotationOffsetInPercent;
        else
            return 1 - rotationOffsetInPercent + rotationInPercent;
    }
}
