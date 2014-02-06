package de.upb.soundgates.cosmic.osc;

import de.upb.soundgates.cosmic.InteractionMethod;

/**
 * Created by posewsky on 16.11.13.
 */
public class OSCType {
    protected OSCMessage msg;
    protected char typeTag;
    public float value;
    public float MIN_VALUE, MAX_VALUE;

    private OSCType(OSCMessage msg, char typeTag, float minValue, float maxValue) {
        this.msg        = msg;
        this.typeTag    = typeTag;
        this.MIN_VALUE  = minValue;
        this.MAX_VALUE  = maxValue;
    }

    public void setValueAsPercent(float p) {
        value = (MAX_VALUE - MIN_VALUE) * p + MIN_VALUE;
    }

    public float getValueAsPercent() {
        return (value - MIN_VALUE) * 100f / (MAX_VALUE - MIN_VALUE);
    }

    public static OSCType newInstance(OSCMessage msg, char typeTag) {
        switch(typeTag)
        {
            case 'i':
                return new OSCType(msg, 'i', 0, 1);
            case 'f':
                return new OSCType(msg, 'f', 0, 1);
            default:
                return null;
        }
    }

    public static OSCType newInstance(OSCMessage msg, char typeTag, String minValue, String maxValue) {
        try {
            switch(typeTag)
            {
                case 'i':
                    return new OSCType(msg, 'i', Integer.parseInt(minValue.trim()), Integer.parseInt(maxValue.trim()));
                case 'f':
                    return new OSCType(msg, 'f', Float.parseFloat(minValue.trim()), Float.parseFloat(maxValue.trim()));
                default:
                    return null;
            }
        } catch(NumberFormatException nfe) {
            return null;
        }
    }

    public char getTypeTag() {
        return typeTag;
    }
    public void setTypeTag(char typeTag) {
        this.typeTag = typeTag;
    }

    public String toString() {
        return typeTag + "[" + MIN_VALUE + ":" + MAX_VALUE + "]";
    }
}
