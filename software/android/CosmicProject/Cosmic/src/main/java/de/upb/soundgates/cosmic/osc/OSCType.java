package de.upb.soundgates.cosmic.osc;

import de.upb.soundgates.cosmic.InteractionMethod;

/**
 * Created by posewsky on 16.11.13.
 */
public class OSCType<E> {
    protected OSCMessage msg;
    protected char typeTag;
    public E MIN_VALUE, MAX_VALUE;

    private OSCType(OSCMessage msg, char typeTag, E minValue, E maxValue) {
        this.msg        = msg;
        this.typeTag    = typeTag;
        this.MIN_VALUE  = minValue;
        this.MAX_VALUE  = maxValue;
    }

    public static OSCType newInstance(OSCMessage msg, char typeTag) {
        switch(typeTag)
        {
            case 'i':
                return new OSCType<Integer>(msg, 'i', Integer.MIN_VALUE, Integer.MAX_VALUE);
            case 'f':
                return new OSCType<Float>(msg, 'f', Float.MIN_VALUE, Float.MAX_VALUE);
            default:
                return null;
        }
    }

    public static OSCType newInstance(OSCMessage msg, char typeTag, String minValue, String maxValue) {
        try {
            switch(typeTag)
            {
                case 'i':
                    return new OSCType<Integer>(msg, 'i', Integer.parseInt(minValue.trim()), Integer.parseInt(maxValue.trim()));
                case 'f':
                    return new OSCType<Float>(msg, 'f', Float.parseFloat(minValue.trim()), Float.parseFloat(maxValue.trim()));
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
