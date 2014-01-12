package de.upb.soundgates.cosmic.osc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by posewsky on 12.11.13.
 */
public class OSCMessageStore {
    List<OSCMessage> store;

    private OSCMessageStore() {
        store = new LinkedList<OSCMessage>();
    }


    public static OSCMessageStore holder;
    public static OSCMessageStore getInstance() {
        if(holder == null) {
            holder = new OSCMessageStore();
        }
        return holder;
    }
    public static OSCMessageStore hasInstance() {
        return holder;
    }
    public static void removeInstance() {
        holder = null;
    }


    public List<OSCMessage> getOSCMessageAsList() {
        return store;
    }

    public List<OSCMessage> getSelectedOSCMessageAsList() {
        List<OSCMessage> selected_store = new LinkedList<OSCMessage>();
        for(OSCMessage msg : store)
            if (msg.isSelected())
                selected_store.add(msg);
        return selected_store;
    }

    public boolean addOSCMessage(String msg) {
        OSCMessage osc_msg = OSCMessage.newInstance(msg);
        if(osc_msg == null)
            return false;
        store.add(osc_msg);
        return true;
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
            ret += msg + "\n";
        return ret;
    }

    public String toStringFull() {
        String ret = "";
        for(OSCMessage msg : store)
            ret += msg.toStringFull() + "\n";
        return ret;
    }
}
