package de.upb.soundgates.cosmic.rows;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionLightRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;
    private ViewHolder holder;

    @Override
    public void update(Observable observable, Object o) {
        if(holder != null) {
            // Using AsyncTask.onPostExecute to update UI (UI-Updates must happen on UI thread)
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {
                    holder.luxValue.setText(msg.getTypes().get(0).value + " Lux");
                }
            }.execute();
        }
    }

    private static class ViewHolder {
        final TextView msgText;
        final TextView luxValue;

        private ViewHolder(TextView msgText, TextView luxValue) {
            this.msgText = msgText;
            this.luxValue = luxValue;
        }
    }


    public InteractionLightRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;
        this.holder = null;

        msg.addObserver(this);
    }


    @Override
    public View getView(View convertView) {
        //ViewHolder holder;
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_light, null);

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
        return InteractionMethod.LIGHT.ordinal();
    }
}
