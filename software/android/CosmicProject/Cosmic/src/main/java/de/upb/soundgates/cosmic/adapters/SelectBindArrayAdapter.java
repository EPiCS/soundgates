package de.upb.soundgates.cosmic.adapters;

/**
 * Created by posewsky on 13.11.13.
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.MinMaxSeekBar;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCMessageStore;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.osc.OSCType;

public class SelectBindArrayAdapter extends ArrayAdapter<OSCMessage> {
    private final List<OSCMessage> list;
    private final Activity context;

    public SelectBindArrayAdapter(Activity context, List<OSCMessage> list) {
        super(context, R.layout.select2_row, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected Spinner  spinner;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.select2_row, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text     = (TextView) view.findViewById(R.id.selectrow_textView);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.selectrow_checkBox);
            viewHolder.spinner  = (Spinner)  view.findViewById(R.id.selectrow_spinner);

            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            OSCMessage msg = (OSCMessage) viewHolder.checkbox.getTag();
                            msg.setSelected(buttonView.isChecked());
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.interaction_technique, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String im_str = parent.getItemAtPosition(position).toString();
                    InteractionMethod im_id = null;

                    // Mapping from description of interaction technique to model values
                    if(im_str.equals(context.getResources().getString(R.string.seekBar))) {
                        im_id = InteractionMethod.SEEKBAR;
                    } else if(im_str.equals(context.getResources().getString(R.string.button))) {
                        im_id = InteractionMethod.BUTTON;
                    } else if(im_str.equals(context.getResources().getString(R.string.tilt))) {
                        im_id = InteractionMethod.TILT;
                    } else if(im_str.equals(context.getResources().getString(R.string.rotaryZ))) {
                        im_id = InteractionMethod.ROTARYZ;
                    } else if(im_str.equals(context.getResources().getString(R.string.shake))) {
                        im_id = InteractionMethod.SHAKE;
                    } else if(im_str.equals(context.getResources().getString(R.string.light))) {
                        im_id = InteractionMethod.LIGHT;
                    } else {
                        String err = "Interaction method " + im_str + " unknown";
                        Log.e("SelectBindArrayAdapter", err);
                        System.exit(-1);
                    }

                    OSCMessage msg = (OSCMessage) viewHolder.checkbox.getTag();
                    msg.setInteractionMethod(im_id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            viewHolder.spinner.setActivated(viewHolder.checkbox.isActivated());
            viewHolder.spinner.setAdapter(adapter);
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getPath());
        holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
    }
}
