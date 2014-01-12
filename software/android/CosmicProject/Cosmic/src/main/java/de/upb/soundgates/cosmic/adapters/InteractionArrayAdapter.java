package de.upb.soundgates.cosmic.adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.List;

import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionArrayAdapter extends ArrayAdapter<OSCMessage> {
    private final List<OSCMessage> list;
    private final Activity context;

    public InteractionArrayAdapter(Activity context, List<OSCMessage> list,) {
        super(context, R.layout.b_row, list);
        this.context = context;
        this.list = list;
    }

}
