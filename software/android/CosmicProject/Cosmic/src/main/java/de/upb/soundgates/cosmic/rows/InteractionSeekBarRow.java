package de.upb.soundgates.cosmic.rows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Observable;

import de.upb.soundgates.cosmic.misc.MinMaxSeekBar;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCArgument;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionSeekBarRow implements InteractionRow{
    private final OSCMessage msg;
    private final LayoutInflater inflater;

    @Override
    public void update(Observable observable, Object o) {

    }

    private static class ViewHolder {
        final TextView text;
        final MinMaxSeekBar seekbar;
        /*final Button button_c;
        final Button button_d;
        final Button button_e;
        final Button button_f;
        final Button button_g;
        final Button button_a;
        final Button button_h;*/

        private ViewHolder(TextView msgText,
                           MinMaxSeekBar seekbar/*,
                           Button button_c,
                           Button button_d,
                           Button button_e,
                           Button button_f,
                           Button button_g,
                           Button button_a,
                           Button button_h*/) {
            this.text = msgText;
            this.seekbar = seekbar;
            /*this.button_c= button_c;
            this.button_d= button_d;
            this.button_e= button_e;
            this.button_f= button_f;
            this.button_g= button_g;
            this.button_a= button_a;
            this.button_h= button_h;*/
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
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_seekbar, null);

            holder = new ViewHolder(
                    (TextView) viewGroup.findViewById(R.id.msg),
                    (MinMaxSeekBar) viewGroup.findViewById(R.id.seekBar)/*,
                    (Button) viewGroup.findViewById(R.id.button_c),
                    (Button) viewGroup.findViewById(R.id.button_d),
                    (Button) viewGroup.findViewById(R.id.button_e),
                    (Button) viewGroup.findViewById(R.id.button_f),
                    (Button) viewGroup.findViewById(R.id.button_g),
                    (Button) viewGroup.findViewById(R.id.button_a),
                    (Button) viewGroup.findViewById(R.id.button_h)*/
            );

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        //configureMinMaxSeekBar(holder);
        holder.text.setText(msg.getPath());

        OSCArgument t = msg.getTypes().get(0);
        holder.seekbar.setMaximumValue(t.MAX_VALUE);
        holder.seekbar.setMinimumValue(t.MIN_VALUE);
        holder.seekbar.setFloatValue(t.value);
        holder.seekbar.setOnSeekBarChangeListener(new OnMinMaxSeekBarChangeListener());

        return view;
    }

    class OnMinMaxSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private boolean touch;

        public OnMinMaxSeekBarChangeListener(){
            touch = false;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            if(!touch)
                return;

            msg.setValue(((MinMaxSeekBar)seekBar).getFloatValue());
            OSCSender.send(msg);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            touch = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            touch = false;
        }
    }

    private void configureMinMaxSeekBar(final ViewHolder viewHolder) {
        viewHolder.seekbar.setOnSeekBarChangeListener(new OnMinMaxSeekBarChangeListener());

        if(msg.getTypes().size() == 1)
        {
            OSCArgument t = msg.getTypes().get(0);
            viewHolder.seekbar.setMaximumValue(t.MAX_VALUE);
            viewHolder.seekbar.setMinimumValue(t.MIN_VALUE);
            viewHolder.seekbar.setFloatValue(t.MIN_VALUE);
        }
        /*
        View.OnTouchListener otl = (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int freq;
                switch(view.getId())
                {
                    case R.id.button_c:
                        freq = 131; break;
                    case R.id.button_d:
                        freq = 147; break;
                    case R.id.button_e:
                        freq = 165; break;
                    case R.id.button_f:
                        freq = 175; break;
                    case R.id.button_g:
                        freq = 196; break;
                    case R.id.button_a:
                        freq = 220; break;
                    case R.id.button_h:
                        freq = 247; break;
                    default:
                        freq = 220;
                }
                viewHolder.seekbar.setFloatValue(freq);

                if(event.getAction() == MotionEvent.ACTION_UP){
                    viewHolder.seekbar.setFloatValue(0);
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    viewHolder.seekbar.setFloatValue(freq);
                    return true;
                }
                return false;
            }
        });

        viewHolder.button_c.setOnTouchListener(otl);
        viewHolder.button_d.setOnTouchListener(otl);
        viewHolder.button_e.setOnTouchListener(otl);
        viewHolder.button_f.setOnTouchListener(otl);
        viewHolder.button_g.setOnTouchListener(otl);
        viewHolder.button_a.setOnTouchListener(otl);
        viewHolder.button_h.setOnTouchListener(otl);*/
    }

    @Override
    public int getViewType() {
        return InteractionMethod.SEEKBAR.ordinal();
    }
}
