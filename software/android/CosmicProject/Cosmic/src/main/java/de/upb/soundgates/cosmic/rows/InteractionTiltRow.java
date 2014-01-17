package de.upb.soundgates.cosmic.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionTiltRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;

    private static class ViewHolder {
        final TextView msgText;
        final TextView tiltValue;

        private ViewHolder(TextView msgText, TextView tiltValue) {
            this.msgText = msgText;
            this.tiltValue = tiltValue;
        }
    }


    public InteractionTiltRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;
    }


    @Override
    public View getView(View convertView) {
        ViewHolder holder;
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_tilt, null);

            holder = new ViewHolder(
                    (TextView) viewGroup.findViewById(R.id.msg),
                    (TextView) viewGroup.findViewById(R.id.value)
            );

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        holder.msgText.setText(msg.getPath());
        //holder.tiltValue.setText();

        return view;
    }

    @Override
    public int getViewType() {
        return InteractionMethod.TILT.ordinal();
    }
}
