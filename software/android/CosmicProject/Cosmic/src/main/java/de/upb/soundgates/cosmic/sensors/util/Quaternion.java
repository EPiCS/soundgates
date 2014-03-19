package de.upb.soundgates.cosmic.sensors.util;

/**
* Created by Thorbjörn Posewsky on 01.02.14.
*/
public class Quaternion {
    public float w, x, y, z;

    public boolean setQuaternion(float[] quaternion) {
        if(quaternion.length != 4)
            return false;
        w = quaternion[0];
        x = quaternion[1];
        y = quaternion[2];
        z = quaternion[3];
        return true;
    }

    public Quaternion() {
        this(0, 0, 0, 0);
    }

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getHeading() {
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

    public double getAttitude() {
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

    public double getBank() {
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

    /*public void set(Quaternion q1) {
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
