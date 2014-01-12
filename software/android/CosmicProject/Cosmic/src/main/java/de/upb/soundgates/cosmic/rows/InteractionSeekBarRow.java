package de.upb.soundgates.cosmic.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.MinMaxSeekBar;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionSeekBarRow implements InteractionRow{
    private final OSCMessage msg;
    private final LayoutInflater inflater;

    private static class ViewHolder {
        final TextView text;
        final MinMaxSeekBar seekbar;
        final Button button_c;
        final Button button_d;
        final Button button_e;
        final Button button_f;
        final Button button_g;
        final Button button_a;
        final Button button_h;

        private ViewHolder(TextView msgText,
                           MinMaxSeekBar seekbar,
                           Button button_c,
                           Button button_d,
                           Button button_e,
                           Button button_f,
                           Button button_g,
                           Button button_a,
                           Button button_h) {
            this.text = msgText;
            this.seekbar = seekbar;
            this.button_c= button_c;
            this.button_d= button_d;
            this.button_e= button_e;
            this.button_f= button_f;
            this.button_g= button_g;
            this.button_a= button_a;
            this.button_h= button_h;
        }
    }


    public InteractionSeekBarRow(LayoutInflater inflater, OSCMessage msg) {
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
                    (TextView) viewGroup.findViewById(R.id.interaction_row_textView),
                    (MinMaxSeekBar) viewGroup.findViewById(R.id.interaction_row_seekBar),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_c),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_d),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_e),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_f),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_g),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_a),
                    (Button) viewGroup.findViewById(R.id.interaction_row_button_h)
            );

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        holder.text.setText(msg.getPath());
        //holder.tiltValue.setText();

        return view;
    }

    @Override
    public int getViewType() {
        return InteractionMethod.SEEKBAR.ordinal();
    }
}
