package de.upb.soundgates.cosmic;

/**
 * Created by posewsky on 13.11.13.
 */
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import de.upb.soundgates.cosmic.osc.OSCMessage;

public class BindArrayAdapter extends ArrayAdapter<OSCMessage> {

    private final List<OSCMessage> list;
    private final Activity context;

    public BindArrayAdapter(Activity context, List<OSCMessage> list) {
        super(context, R.layout.bind_row, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected SeekBar seekbar;
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
            onStopTrackingTouch(seekBar);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d(MainActivity.LOG_TAG, viewHolder.text.getText() + ": " + seekBar.getProgress());
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
            viewHolder.seekbar = (SeekBar) view.findViewById(R.id.bindrow_seekBar);
            viewHolder.seekbar.setOnSeekBarChangeListener(new BindOnSeekBarChangeListener(viewHolder));
            viewHolder.seekbar.setMax(880);

            View.OnClickListener ocl = new View.OnClickListener() {
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
                    viewHolder.seekbar.setProgress(freq);
                }
            };

            viewHolder.button_c =(Button) view.findViewById(R.id.bindrow_button_c);
            viewHolder.button_d =(Button) view.findViewById(R.id.bindrow_button_d);
            viewHolder.button_e =(Button) view.findViewById(R.id.bindrow_button_e);
            viewHolder.button_f =(Button) view.findViewById(R.id.bindrow_button_f);
            viewHolder.button_g =(Button) view.findViewById(R.id.bindrow_button_g);
            viewHolder.button_a =(Button) view.findViewById(R.id.bindrow_button_a);
            viewHolder.button_h =(Button) view.findViewById(R.id.bindrow_button_h);

            viewHolder.button_c.setOnClickListener(ocl);
            viewHolder.button_d.setOnClickListener(ocl);
            viewHolder.button_e.setOnClickListener(ocl);
            viewHolder.button_f.setOnClickListener(ocl);
            viewHolder.button_g.setOnClickListener(ocl);
            viewHolder.button_a.setOnClickListener(ocl);
            viewHolder.button_h.setOnClickListener(ocl);

            view.setTag(viewHolder);
            viewHolder.seekbar.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).seekbar.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).toString());
        //holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
    }
}
