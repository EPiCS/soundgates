package de.upb.soundgates.cosmic.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;
import java.util.List;

import de.upb.soundgates.cosmic.sensors.util.Vector3d;

/**
 * Created by Thorbjörn Posewsky on 05.02.14.
 */
public class ShakeSensorListener extends AbstractSensorListener {
    private Vector3d acc;
    private float threshold;

    private CharacteristicVectorStore charVectors;
    private int lastCharVectorIndex;

    public ShakeSensorListener(Context context) {
        super(context, Sensor.TYPE_LINEAR_ACCELERATION);

        acc = new Vector3d(0,0,0);
        threshold = 0.6f * 9.81f * 10;

        charVectors = new CharacteristicVectorStore();
        charVectors.addCharacteristicVector(new Vector3d(1,0,0),  "East");
        charVectors.addCharacteristicVector(new Vector3d(-1,0,0), "West");
        charVectors.addCharacteristicVector(new Vector3d(0,1,0),  "North");
        charVectors.addCharacteristicVector(new Vector3d(0,-1,0), "South");
        charVectors.addCharacteristicVector(new Vector3d(0,0,1),  "Up");
        charVectors.addCharacteristicVector(new Vector3d(0,0,-1), "Down");
        lastCharVectorIndex = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        acc.x = event.values[0];
        acc.y = event.values[1];
        acc.z = event.values[1];

        if(acc.length() < threshold) {
            return;
        }

        int charVectorIndex = acc.quantize(charVectors.getVector3dAsArray());
        if(charVectorIndex == lastCharVectorIndex)
            return;

        lastCharVectorIndex = charVectorIndex;

        setChanged();
        notifyObservers();
    }
}

class CharacteristicVector {
    public Vector3d vector;
    public String description;

    public CharacteristicVector(Vector3d vector, String description) {
        this.vector = vector;
        this.description = description;
    }

    public boolean equals(CharacteristicVector v) {
        return this.description.equals(v.description);
    }
}

class CharacteristicVectorStore {
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
