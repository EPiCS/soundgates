package de.upb.soundgates.cosmic.sensor;

/**
* Created by Thorbjörn Posewsky on 01.02.14.
*/
public class Vector3d {
    public float x,y,z;

    public Vector3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length() {
        return (float)Math.sqrt(x*x + y*y + z*z);
    }

    public float distance(Vector3d v) {
        float xDist = x - v.x;
        float yDist = y - v.y;
        float zDist = z - v.z;
        return (float)Math.sqrt(xDist*xDist + yDist*yDist + zDist*zDist);
    }

    public void normalize() {
        float length = length();
        if(length == 0)
            return;
        x /= length;
        y /= length;
        z /= length;
    }

    public Vector3d quantize(Vector3d[] charVectors) {
        float minDist = Float.MAX_VALUE;
        int minIndex = 0;

        normalize();
        for(Vector3d v : charVectors)
            v.normalize();

        for(int i = 0; i < charVectors.length; ++i) {
            float dist = distance(charVectors[i]);
            if(dist < minDist) {
                minIndex = i;
                minDist = dist;
            }
        }

        return charVectors[minIndex];
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}
