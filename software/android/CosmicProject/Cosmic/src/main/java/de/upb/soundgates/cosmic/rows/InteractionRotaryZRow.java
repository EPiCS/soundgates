package de.upb.soundgates.cosmic.rows;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.sensor.RotaryZSensorListener;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionRotaryZRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;
    private final RotaryZSensorListener listener;

    private ViewHolder holder;

    @Override
    public void update(Observable observable, Object o) {
        final float rotation = listener.getRotationWithOffsetInPercent();
        msg.setValueAsPercent(rotation);
        Log.d("RotaryZ", rotation+"");
        OSCSender.send(msg);
        if(holder != null) {
            // Using AsyncTask.onPostExecute to update UI (UI-Updates must happen on UI thread)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {
                    holder.rotationValue.setText(String.format("%.2f", 100 * rotation) + "%");
                }
            }.execute();
        }
    }

    private static class ViewHolder {
        final TextView msgText;
        final TextView rotationValue;
        final Button calibrate;

        private ViewHolder(TextView msgText, TextView rotationValue, Button calibrate) {
            this.msgText = msgText;
            this.rotationValue = rotationValue;
            this.calibrate = calibrate;
        }
    }


    public InteractionRotaryZRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;
        this.holder = null;

        this.listener = new RotaryZSensorListener(inflater.getContext());
        this.listener.addObserver(this);
    }


    @Override
    public View getView(View convertView) {
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_rotaryz, null);

            holder = new ViewHolder(
                    (TextView) viewGroup.findViewById(R.id.msg),
                    (TextView) viewGroup.findViewById(R.id.value),
                    (Button)   viewGroup.findViewById(R.id.calibrate)
            );

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        holder.msgText.setText(msg.getPath());
        holder.calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setStartOfRotation();
            }
        });

        return view;
    }

    @Override
    public int getViewType() {
        return InteractionMethod.ROTARYZ.ordinal();
    }
}
