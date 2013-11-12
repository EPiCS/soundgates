package de.upb.soundgates.cosmic.osc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by posewsky on 12.11.13.
 */
public class OSCMessageStore {
    List<OSCMessage> store;

    public OSCMessageStore() {
        store = new LinkedList<OSCMessage>();
    }

    public List<OSCMessage> getOSCMessageAsList() {
        return store;
    }

    public void addOSCMessage(String msg) {
        store.add(new OSCMessage(msg));
    }

    public boolean markOSCMessage(int position, boolean selected) {
        try {
            store.get(position).setSelected(selected);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public String toString() {
        String ret = "";
        for(OSCMessage msg : store)
            ret += msg + " (selected: " + msg.isSelected() + ")\n";
        return ret;
    }
}
