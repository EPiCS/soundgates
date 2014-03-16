package de.upb.soundgates.cosmic.osc;

import java.util.LinkedList;
import java.util.Observable;

import de.upb.soundgates.cosmic.InteractionMethod;

/**
 * Created by posewsky on 12.11.13.
 */

public class OSCMessage extends Observable {
    protected String path;
    protected LinkedList<OSCArgument> types;
    protected boolean selected;
    protected InteractionMethod interactionMethod;

    public static OSCMessage newInstance(String oscMsg) {
        String[] parts = oscMsg.split(" ");

        if(parts.length == 1) {
            return new OSCMessage(parts[0]);
        } else if (parts.length == 2) {
            OSCMessage msg = new OSCMessage(parts[0]);
            String types = parts[1].replaceAll("\"", "");
            for(int i = 0; i < types.length(); ++i) {
                OSCArgument type = OSCArgument.newInstance(msg, types.charAt(i));
                if(type == null)
                    return null;

                msg.types.add(type);
            }
            return msg;
        } else if (parts.length > 2) {
            OSCMessage msg = new OSCMessage(parts[0]);
            String types = parts[1].replaceAll("\"", "");
            for(int i = 0; i < types.length(); ++i) {
                String[] range = parts[2+i].replace('[', '\0').replace(']', '\0').split(":");
                if(range.length != 2)
                    return null;

                OSCArgument type = OSCArgument.newInstance(msg, types.charAt(i), range[0], range[1]);
                if(type == null)
                    return null;

                msg.types.add(type);
            }
            return msg;
        } else
            return null;
    }

    private OSCMessage(String path) {
        this.path       = path;
        this.types      = new LinkedList<OSCArgument>();
        this.selected   = false;
        this.interactionMethod = InteractionMethod.values()[0];
    }

    public String toString() {
        return path;
    }

    public String toStringFull() {
        String ret = path;
        for(OSCArgument t: types)
            ret += " " + t.toString();
        ret += " selected: " + isSelected();
        ret += " interaction method: " + getInteractionMethod();

        return ret;
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LinkedList<OSCArgument> getTypes() { return types; }

    public void setValue(float value) {
        if(getTypes().size() > 0) {
            OSCArgument arg = getTypes().get(0);
            arg.setValue(value);
            setChanged();
            notifyObservers(value);
        }
    }

    public float getValue() {
        if(getTypes().size() > 0) {
            OSCArgument arg = getTypes().get(0);
            return arg.getValue();
        }
        return Float.NaN;
    }

    public void setValueAsPercent(float value) {
        if(getTypes().size() > 0) {
            OSCArgument arg = getTypes().get(0);
            arg.setValueAsPercent(value);
            setChanged();
            notifyObservers(value);
        }
    }

    public float getValueAsPercent() {
        if(getTypes().size() > 0) {
            OSCArgument arg = getTypes().get(0);
            return arg.getValueAsPercent();
        }
        return Float.NaN;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public InteractionMethod getInteractionMethod() { return interactionMethod; }
    public void setInteractionMethod(InteractionMethod id) { this.interactionMethod = id; }
}