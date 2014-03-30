package de.upb.soundgates.cosmic.rows;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.sensors.ShakeSensorListener;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionShakeRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;
    private final ShakeSensorListener listener;

    private ViewHolder holder;

    @Override
    public void update(Observable observable, Object o) {
        msg.setValueAsPercent(1);
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
                    holder.shakeIndicator.setText("Yes");
                }
            }.execute();
        }

        msg.setValueAsPercent(0);
        OSCSender.send(msg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.shakeIndicator.setText("No");
            }
        }, 500);
    }

    private static class ViewHolder {
        final TextView msgText;
        final TextView shakeIndicator;
        final EditText sensitivity;
        final Button setButton;

        private ViewHolder(TextView msgText, TextView shakeIndicator, EditText sensitivity, Button setButton) {
            this.msgText = msgText;
            this.shakeIndicator = shakeIndicator;
            this.sensitivity = sensitivity;
            this.setButton = setButton;
        }
    }


    public InteractionShakeRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;

        this.listener = new ShakeSensorListener(inflater.getContext());
        this.listener.addObserver(this);
    }


    @Override
    public View getView(View convertView) {
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_shake, null);

            holder = new ViewHolder(
                    (TextView) viewGroup.findViewById(R.id.msg),
                    (TextView) viewGroup.findViewById(R.id.value),
                    (EditText) viewGroup.findViewById(R.id.sensitivity),
                    (Button)   viewGroup.findViewById(R.id.set)
            );

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        holder.msgText.setText(msg.getPath());
        holder.sensitivity.setText(listener.getThreshold() + "");
        holder.setButton.setOnTouchListener(new ButtonOnTouchListener(holder));
        //holder.shakeIndicator.setText();

        return view;
    }

    class ButtonOnTouchListener implements View.OnTouchListener {
        ViewHolder viewHolder;

        public ButtonOnTouchListener(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP) {
                String value;
                value = viewHolder.sensitivity.getText().toString();

                if (value.trim().length() == 0) {
                    setThresholdError("\"\"");
                    return false;
                }

                try {
                    float parsedValue = Float.parseFloat(value);
                    listener.setThreshold(parsedValue);
                } catch (NumberFormatException nfe) {
                    setThresholdError(value);
                    return false;
                }

                return true;
            }
            return false;
        }
    }

    void setThresholdError(final String value) {
        if(holder != null) {
            // Using AsyncTask.onPostExecute to update UI (UI-Updates must happen on UI thread)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {
                    CharSequence text = "Can not set value: " + value;
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(inflater.getContext(), text, duration);
                    toast.show();
                }
            }.execute();
        }
    }

    @Override
    public int getViewType() {
        return InteractionMethod.SHAKE.ordinal();
    }
}
