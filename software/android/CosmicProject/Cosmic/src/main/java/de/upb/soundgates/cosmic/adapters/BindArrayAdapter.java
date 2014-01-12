package de.upb.soundgates.cosmic.adapters;

/**
 * Created by posewsky on 13.11.13.
 */
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.upb.soundgates.cosmic.MainActivity;
import de.upb.soundgates.cosmic.MinMaxSeekBar;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.osc.OSCType;

public class BindArrayAdapter extends ArrayAdapter<OSCMessage> {
    private final List<OSCMessage> list;
    private final Activity context;
    private String host;
    private int port;

    public BindArrayAdapter(Activity context, List<OSCMessage> list, String host, int port) {
        super(context, R.layout.bind_row, list);
        this.context = context;
        this.list = list;
        this.host = host;
        this.port = port;
    }

    static class ViewHolder {
        protected TextView text;
        protected MinMaxSeekBar seekbar;
        protected Button button_c;
        protected Button button_d;
        protected Button button_e;
        protected Button button_f;
        protected Button button_g;
        protected Button button_a;
        protected Button button_h;
    }

    class BindOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        final ViewHolder viewHolder;

        public BindOnSeekBarChangeListener(final ViewHolder viewHolder){
            this.viewHolder = viewHolder;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            OSCMessage osc_msg = (OSCMessage)viewHolder.seekbar.getTag();
            LinkedList<OSCType> types = osc_msg.getTypes();

            if(types.size() == 0) {
                /* Just for demo: A seekBar change sends a normal OSC message */
                com.illposed.osc.OSCMessage packed_msg = new com.illposed.osc.OSCMessage(osc_msg.getPath());
                new OSCSender(host, port, packed_msg).execute();
                return;
            } else if(types.size() != 1) {
                Log.e(MainActivity.LOG_TAG, "#Types wrong: " + types.size());
                return;
            }

            Object[] args = new Object[1];

            switch(types.get(0).getTypeTag())
            {
                case 'i':
                    args[0] = new Integer((int)((MinMaxSeekBar)seekBar).getFloatValue());
                    break;
                case 'f':
                    args[0] = new Float(((MinMaxSeekBar)seekBar).getFloatValue());
                    break;
                default:
                    Log.e(MainActivity.LOG_TAG, "TypeTag not recognized");
                    return;
            }

            com.illposed.osc.OSCMessage packed_msg = new com.illposed.osc.OSCMessage(osc_msg.getPath(),args);

            new OSCSender(host, port, packed_msg).execute();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.bind_row, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.bindrow_textView);
            viewHolder.seekbar = (MinMaxSeekBar) view.findViewById(R.id.bindrow_seekBar);
            viewHolder.seekbar.setOnSeekBarChangeListener(new BindOnSeekBarChangeListener(viewHolder));

            if(list.get(position).getTypes().size() == 1)
            {
                switch(list.get(position).getTypes().get(0).getTypeTag())
                {
                    case 'i':
                    {
                        OSCType<Integer> t = list.get(position).getTypes().get(0);
                        viewHolder.seekbar.setMaximumValue(t.MAX_VALUE);
                        viewHolder.seekbar.setMinimumValue(t.MIN_VALUE);
                        break;
                    }
                    case 'f':
                    {
                        OSCType<Float> t = list.get(position).getTypes().get(0);
                        viewHolder.seekbar.setMaximumValue(t.MAX_VALUE);
                        viewHolder.seekbar.setMinimumValue(t.MIN_VALUE);
                        break;
                    }
                    default:
                        Log.e(MainActivity.LOG_TAG, "TypeTag not recognized");
                        return null;
                }
            }

            View.OnTouchListener otl = (new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    int freq;
                    switch(view.getId())
                    {
                        case R.id.bindrow_button_c:
                            freq = 131; break;
                        case R.id.bindrow_button_d:
                            freq = 147; break;
                        case R.id.bindrow_button_e:
                            freq = 165; break;
                        case R.id.bindrow_button_f:
                            freq = 175; break;
                        case R.id.bindrow_button_g:
                            freq = 196; break;
                        case R.id.bindrow_button_a:
                            freq = 220; break;
                        case R.id.bindrow_button_h:
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

            /*View.OnClickListener ocl = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int freq;
                    switch(view.getId())
                    {
                        case R.id.bindrow_button_c:
                            freq = 131; break;
                        case R.id.bindrow_button_d:
                            freq = 147; break;
                        case R.id.bindrow_button_e:
                            freq = 165; break;
                        case R.id.bindrow_button_f:
                            freq = 175; break;
                        case R.id.bindrow_button_g:
                            freq = 196; break;
                        case R.id.bindrow_button_a:
                            freq = 220; break;
                        case R.id.bindrow_button_h:
                            freq = 247; break;
                        default:
                            freq = 220;
                    }
                    viewHolder.seekbar.setFloatValue(freq);
                }
            };*/

            viewHolder.button_c =(Button) view.findViewById(R.id.bindrow_button_c);
            viewHolder.button_d =(Button) view.findViewById(R.id.bindrow_button_d);
            viewHolder.button_e =(Button) view.findViewById(R.id.bindrow_button_e);
            viewHolder.button_f =(Button) view.findViewById(R.id.bindrow_button_f);
            viewHolder.button_g =(Button) view.findViewById(R.id.bindrow_button_g);
            viewHolder.button_a =(Button) view.findViewById(R.id.bindrow_button_a);
            viewHolder.button_h =(Button) view.findViewById(R.id.bindrow_button_h);

            /*viewHolder.button_c.setOnClickListener(ocl);
            viewHolder.button_d.setOnClickListener(ocl);
            viewHolder.button_e.setOnClickListener(ocl);
            viewHolder.button_f.setOnClickListener(ocl);
            viewHolder.button_g.setOnClickListener(ocl);
            viewHolder.button_a.setOnClickListener(ocl);
            viewHolder.button_h.setOnClickListener(ocl);*/

            viewHolder.button_c.setOnTouchListener(otl);
            viewHolder.button_d.setOnTouchListener(otl);
            viewHolder.button_e.setOnTouchListener(otl);
            viewHolder.button_f.setOnTouchListener(otl);
            viewHolder.button_g.setOnTouchListener(otl);
            viewHolder.button_a.setOnTouchListener(otl);
            viewHolder.button_h.setOnTouchListener(otl);

            view.setTag(viewHolder);
            viewHolder.seekbar.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).seekbar.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getPath());
        //holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
    }
}
