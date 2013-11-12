package de.upb.soundgates.cosmic.osc;

/**
 * Created by posewsky on 12.11.13.
 */

public class OSCMessage {
    protected String oscMsg;
    protected boolean selected;

    public OSCMessage(String oscMsg) {
        this.oscMsg     = oscMsg;
        this.selected   = false;
    }

    public String toString() {
        return oscMsg;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
