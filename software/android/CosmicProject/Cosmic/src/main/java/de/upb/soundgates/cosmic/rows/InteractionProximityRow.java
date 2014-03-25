package de.upb.soundgates.cosmic.rows;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;

import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;
import de.upb.soundgates.cosmic.sensors.ProximitySensorListener;


public class InteractionProximityRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;
    private final ProximitySensorListener listener;

    private ViewHolder holder;

    @Override
    public void update(Observable observable, Object o) {
        final float p = listener.getProximityValueInPercent();
        msg.setValueAsPercent(p);
        OSCSender.send(msg);

        updateUI();
    }

    private void updateUI() {
        if(holder != null) {
            // Using AsyncTask.onPostExecute to update UI (UI-Updates must happen on UI thread)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {
                    holder.value.setText(listener.getProximityValue() + " cm");
                }
            }.execute();
        }
    }

    private static class ViewHolder {
        final TextView msgText;
        final TextView value;

        private ViewHolder(TextView msgText, TextView value) {
            this.msgText = msgText;
            this.value = value;
        }
    }


    public InteractionProximityRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;
        this.holder = null;

        this.listener = new ProximitySensorListener(inflater.getContext());
        this.listener.addObserver(this);
    }


    @Override
    public View getView(View convertView) {
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_proximity, null);

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

        return view;
    }

    @Override
    public int getViewType() {
        return InteractionMethod.PROXIMITY.ordinal();
    }
}
