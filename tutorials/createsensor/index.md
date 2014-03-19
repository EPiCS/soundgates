---
title: Adding a sensor
layout: page
---
# Adding a sensor to the Android App

This Tutorial will explain how the Cosmic (*__CO__mputer __S__cientists __M__aking mus__IC__*) Android App can be extended.
We will implement a __proximity sensor__. In most Android devices the built-in proximity sensor returns just
two different values, which indicate if an object (usually the head of the user) is *near* or *far*.
The values from the sensor will be mapped to a certain range, which is provided by the *Synthesizer*.
The mapped value is then send via an [OSC](http://en.wikipedia.org/wiki/Open_Sound_Control) message to the Synthesizer.

> __Contents__  
> 1. [Sensor listener for an Android sensor](#listener)  
> 2. [Custom row layout](#layout)  
> 3. [Layout inflater and controller](#inflater)  
> 4. [Registration of the sensor](#register)  


## Sensor listener for an Android sensor
<a name="listener"></a>

First of all we will create a listener class that receives updates from the sensor via a callback method `onSensorChanged`.
The constructor of the class calls the constructor of the superclass `AbstractSensorListener` to register the listener.
The `onSensorChanged` method notifys observers (see [below](#inflater)) once the value of the sensor changes.
The getters grant the observers access to this data.

``` java
public class ProximitySensorListener extends AbstractSensorListener {
    private float value;

    public ProximitySensorListener(Context context) {
        super(context, Sensor.TYPE_PROXIMITY);

        value = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != getSensorType())
            return;

        float current_value = event.values[0];
        if(current_value != value) {
            value = current_value;

            setChanged();
            notifyObservers();
        }
    }

    public float getProximityValueInPercent() {
        return value / getSensorManager().getDefaultSensor(getSensorType()).getMaximumRange();
    }

    public float getProximityValue() {
        return value;
    }
}
```

## Custom row layout
<a name="layout"></a>

Each sensor has a custom UI layout. The layout is loaded as one row in the `ListView` of the interaction fragment.
In this case we make a simple layout that displays the OSC message (`@+id/msg`)
above the descriptive text "Proximity:" which is right next to the current proximity value (`@+id/value`).
The next [section](#inflater) explains how the layout is filled with data.

``` xml
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/msg" />

    <LinearLayout
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Proximity:"
            android:paddingRight="10dp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/value" />
    </LinearLayout>

</LinearLayout>
```

## Layout inflater and controller
<a name="inflater"></a>

The following class implements the `InteractionRow` interface which extends Javas `Observer` interface
and declares two new functions `getView` and `getViewType`.
`getView` uses the [View Holder-Pattern](http://developer.android.com/training/improving-layouts/smooth-scrolling.html) and
instanciates the layout. The `getViewType` function distinguishes between the different row types (sensors, see [below](#register)).
The constructor instanciates the listener and adds itself as a listener.
The `update` function is called once the values of the sensor change.
It updates the OSC message (the model) and sends the message to the Synthesizer.
It also triggers the UI update.

``` java
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
```

## Registration of the sensor
<a name="register"></a>

Finally all sensors are enumerated in the `InteractionMethod` enum.
The layout inflater class must be specified (please make sure its constructor takes a `LayoutInflater` and an `OSCMessage` as parameters)
to correctly load all classes. The second parameter is the description by which the users chooses the sensor.

``` java
public enum InteractionMethod {
    SEEKBAR(InteractionSeekBarRow.class, "Slider"),
    BUTTON(InteractionButtonRow.class, "Button"),
    TILT(InteractionTiltRow.class, "Tilt"),
    ROTARYZ(InteractionRotaryZRow.class, "Rotary Switch"),
    SHAKE(InteractionShakeRow.class, "Shake"),
    LIGHT(InteractionLightRow.class, "Light"),
    PROXIMITY(InteractionProximityRow.class, "Proximity");

    ...
```