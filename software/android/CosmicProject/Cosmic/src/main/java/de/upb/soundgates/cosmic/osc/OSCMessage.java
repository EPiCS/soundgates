package de.upb.soundgates.cosmic.osc;

import java.util.LinkedList;

/**
 * Created by posewsky on 12.11.13.
 */

public class OSCMessage {
    protected String path;
    protected LinkedList<OSCType> types;
    protected boolean selected;

    public static OSCMessage newInstance(String oscMsg) {
        String[] parts = oscMsg.split(" ");

        if(parts.length == 1) {
            return new OSCMessage(parts[0]);
        } else if (parts.length == 2) {
            OSCMessage msg = new OSCMessage(parts[0]);
            String types = parts[1].replaceAll("\"", "");
            for(int i = 0; i < types.length(); ++i) {
                OSCType type = OSCType.newInstance(types.charAt(i));
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

                OSCType type = OSCType.newInstance(types.charAt(i), range[0], range[1]);
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
        this.types      = new LinkedList<OSCType>();
        this.selected   = false;
    }

    public String toString() {
        String ret = path;
        /*for(OSCType t: types)
            path += " " + t.toString();
        path += " selected: " + isSelected();*/
        return path;
    }

    public String toStringFull() {
        String ret = path;
        for(OSCType t: types)
            path += " " + t.toString();
        path += " selected: " + isSelected();
        return path;
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LinkedList<OSCType> getTypes() { return types; }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}