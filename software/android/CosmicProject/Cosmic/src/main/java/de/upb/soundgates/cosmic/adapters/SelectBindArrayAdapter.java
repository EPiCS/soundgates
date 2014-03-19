package de.upb.soundgates.cosmic.adapters;

/**
 * Created by posewsky on 13.11.13.
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.upb.soundgates.cosmic.rows.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;

public class SelectBindArrayAdapter extends ArrayAdapter<OSCMessage> {
    private final List<OSCMessage> list;
    private final Activity context;
    private String[] spinnerStrings;
    private static final String spinnerDescription = "- Choose Interaction Method -";

    public SelectBindArrayAdapter(Activity context, List<OSCMessage> list) {
        super(context, R.layout.select2_row, list);
        this.context = context;
        this.list = list;

        InteractionMethod[] interactionMethodDescriptions = InteractionMethod.values();
        this.spinnerStrings = new String[interactionMethodDescriptions.length+1];
        spinnerStrings[0] = spinnerDescription;
        for(int i = 1; i < spinnerStrings.length; ++i)
            spinnerStrings[i] = interactionMethodDescriptions[i-1].toString();
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
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }

        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getPath());
        holder.checkbox.setChecked(list.get(position).isSelected());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String im_str = parent.getItemAtPosition(position).toString();
                InteractionMethod im_id = null;

                if(im_str.equals(spinnerDescription)) {
                    OSCMessage msg = (OSCMessage) holder.checkbox.getTag();
                    holder.spinner.setSelection(1+msg.getInteractionMethod().ordinal());
                    return;
                }

                // Mapping from description of interaction technique to model values
                im_id = InteractionMethod.getInteractionMethodByDescription(im_str);
                if(im_id == null) {
                    String err = "Interaction method " + im_str + " unknown";
                    Log.e("SelectBindArrayAdapter", err);
                    System.exit(-1);
                }

                OSCMessage msg = (OSCMessage) holder.checkbox.getTag();
                msg.setInteractionMethod(im_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.spinner.setActivated(holder.checkbox.isActivated());
        InteractionMethod im = ((OSCMessage) holder.checkbox.getTag()).getInteractionMethod();//list.get(position).getInteractionMethod();
        if(im != null) {
            holder.spinner.setSelection(1+im.ordinal());
        }
        holder.spinner.setAdapter(adapter);

        return view;
    }
}