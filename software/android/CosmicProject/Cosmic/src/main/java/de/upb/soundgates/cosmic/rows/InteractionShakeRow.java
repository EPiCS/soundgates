package de.upb.soundgates.cosmic.rows;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        private ViewHolder(TextView msgText, TextView shakeIndicator) {
            this.msgText = msgText;
            this.shakeIndicator = shakeIndicator;
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
        //holder.shakeIndicator.setText();

        return view;
    }

    @Override
    public int getViewType() {
        return InteractionMethod.SHAKE.ordinal();
    }
}
