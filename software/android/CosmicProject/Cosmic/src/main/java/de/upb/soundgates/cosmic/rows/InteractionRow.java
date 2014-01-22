package de.upb.soundgates.cosmic.rows;

import android.view.View;

import java.util.Observer;

/**
 * Created by posewsky on 10.01.14.
 */
public interface InteractionRow extends Observer{
    public View getView(View convertView);
    public int getViewType();
}
