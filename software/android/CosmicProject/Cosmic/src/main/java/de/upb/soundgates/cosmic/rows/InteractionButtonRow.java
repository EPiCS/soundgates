package de.upb.soundgates.cosmic.rows;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Observable;

import de.upb.soundgates.cosmic.InteractionMethod;
import de.upb.soundgates.cosmic.R;
import de.upb.soundgates.cosmic.osc.OSCMessage;
import de.upb.soundgates.cosmic.osc.OSCSender;

/**
 * Created by posewsky on 10.01.14.
 */
public class InteractionButtonRow implements InteractionRow {
    private final OSCMessage msg;
    private final LayoutInflater inflater;
    private HashMap<EditText, String> myList;

    @Override
    public void update(Observable observable, Object o) {

    }

    private static class ViewHolder {
        final TextView msgText;
        final Button button;
        final EditText onPressValue;
        final EditText onReleaseValue;

        private ViewHolder(TextView msgText, Button button, EditText onPressValue, EditText onReleaseValue) {
            this.msgText = msgText;
            this.button = button;
            this.onPressValue = onPressValue;
            this.onReleaseValue = onReleaseValue;
        }
    }


    public InteractionButtonRow(LayoutInflater inflater, OSCMessage msg) {
        this.inflater = inflater;
        this.msg = msg;
        this.myList = new HashMap<EditText, String>();
    }


    @Override
    public View getView(View convertView) {
        ViewHolder holder;
        View view;

        // no convertView so create a new one
        if (convertView == null) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.interaction_row_button, null);

            holder = new ViewHolder(
                    (TextView) viewGroup.findViewById(R.id.msg),
                    (Button) viewGroup.findViewById(R.id.button),
                    (EditText) viewGroup.findViewById(R.id.onPressValue),
                    (EditText) viewGroup.findViewById(R.id.onReleaseValue)
            );

            Log.d("getView", "convertView == null, " + msg.toString());

            viewGroup.setTag(holder);

            view = viewGroup;
        } else {
            holder = (ViewHolder) convertView.getTag();

            view = convertView;
        }

        // actually setup the view
        holder.msgText.setText(msg.getPath());
        holder.button.setOnTouchListener(new ButtonOnTouchListener(holder));
        //holder.onPressValue.addTextChangedListener(new ValueChangedListener(holder.onPressValue));
        //holder.onReleaseValue.addTextChangedListener(new ValueChangedListener(holder.onReleaseValue));

        //holder.onPressValue.setText(myList.get(holder.onPressValue));
        //holder.onReleaseValue.setText(myList.get(holder.onReleaseValue));

        return view;
    }

    class ValueChangedListener implements TextWatcher {
        final EditText editText;

        public ValueChangedListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            myList.put(editText, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    class ButtonOnTouchListener implements View.OnTouchListener {
        ViewHolder viewHolder;

        public ButtonOnTouchListener(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                String value;
                if(event.getAction() == MotionEvent.ACTION_UP)
                    value = viewHolder.onReleaseValue.getText().toString();
                else
                    value = viewHolder.onPressValue.getText().toString();

                if (value.trim().length() == 0)
                    return false; // send nothing if nothing is in the EditText

                try {
                    float parsedValue = Float.parseFloat(value);
                    msg.getTypes().get(0).value = parsedValue;
                    OSCSender.send(msg);
                } catch (NumberFormatException nfe) {
                    return false;
                }

                return true;
            }
            return false;
        }
    }

    @Override
    public int getViewType() {
        return InteractionMethod.BUTTON.ordinal();
    }
}
