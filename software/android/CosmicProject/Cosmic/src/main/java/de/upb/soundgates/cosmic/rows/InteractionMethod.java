package de.upb.soundgates.cosmic.rows;

import android.util.Log;
import android.view.LayoutInflater;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.upb.soundgates.cosmic.osc.OSCMessage;

/**
 * Created by posewsky on 10.01.14.
 */
public enum InteractionMethod {
    SEEKBAR(InteractionSeekBarRow.class, "Slider"),
    BUTTON(InteractionButtonRow.class, "Button"),
    TILT(InteractionTiltRow.class, "Tilt"),
    ROTARYZ(InteractionRotaryZRow.class, "Rotary Switch"),
    SHAKE(InteractionShakeRow.class, "Shake"),
    LIGHT(InteractionLightRow.class, "Light");

    //------------------------------------------------------------------------------------------

    private static final Map<String, InteractionMethod> descriptionLookupMap =
            new HashMap<String, InteractionMethod>();

    static {
        for(InteractionMethod im : EnumSet.allOf(InteractionMethod.class)) {
            if(descriptionLookupMap.containsKey(im.toString())) {
                Log.e("InteractionMethod", "Same description for different InteractionMethods");
                System.exit(-1);
            }
            descriptionLookupMap.put(im.toString(), im);
        }
    }

    public static InteractionMethod getInteractionMethodByDescription(String description) {
        return descriptionLookupMap.get(description);
    }

    //------------------------------------------------------------------------------------------

    private Class classRepresentation;
    private String description;

    private InteractionMethod(Class row, String desc) {
        classRepresentation = row;
        description = desc;
    }

    public InteractionRow createRow(LayoutInflater inflater, OSCMessage msg) {
        try {
            Constructor rowConstructor = this.classRepresentation.getConstructor(LayoutInflater.class, OSCMessage.class);
            return (InteractionRow)rowConstructor.newInstance(inflater, msg);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return description;
    }
}
