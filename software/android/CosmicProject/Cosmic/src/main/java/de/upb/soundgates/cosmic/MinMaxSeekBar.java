package de.upb.soundgates.cosmic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by posewsky on 18.11.13.
 */
public class MinMaxSeekBar extends SeekBar {

    private final int SEEK_BAR_MAX_VALUE = 10000;
    private final float INITIAL_MINIMUM_VALUE =  0.f;
    private final float INITIAL_MAXIMUM_VALUE = 10.f;
    private final float INITIAL_FLOAT_VALUE = 7.5f;

    private float minimumValue = INITIAL_MINIMUM_VALUE;
    private float maximumValue = INITIAL_MAXIMUM_VALUE;

    public MinMaxSeekBar(Context context) {
        super(context);
        initialize();
    }

    public MinMaxSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MinMaxSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        this.setMax(SEEK_BAR_MAX_VALUE);
        this.setFloatValue(INITIAL_FLOAT_VALUE);
    }

    public void setMinimumValue(float minimumValue) {
        if (minimumValue > this.maximumValue) minimumValue = this.maximumValue;
        this.minimumValue = minimumValue;
        this.setFloatValue(this.getFloatValue());
    }

    public float getMinimumValue() {
        return this.minimumValue;
    }

    public void setMaximumValue(float maximumValue) {
        if (maximumValue < this.minimumValue) maximumValue = this.minimumValue;
        this.maximumValue = maximumValue;
        this.setFloatValue(this.getFloatValue());
    }

    public float getMaximumValue() {
        return this.maximumValue;
    }

    public void setFloatValue(float value) {
        if (value < this.minimumValue) value = this.minimumValue;
        if (value > this.maximumValue) value = this.maximumValue;
        this.setProgress((int)((value - this.minimumValue) / this.currentRange() * (float)SEEK_BAR_MAX_VALUE));
    }

    public float getFloatValue() {
        return (this.currentRange() * this.currentPercentage()) + this.minimumValue;
    }

    private float currentRange() {
        return this.maximumValue - this.minimumValue;
    }

    private float currentPercentage() {
        return (float)this.getProgress() / (float)SEEK_BAR_MAX_VALUE;
    }
}