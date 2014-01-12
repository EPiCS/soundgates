package de.upb.soundgates.cosmic.rows;

import android.view.View;

/**
 * Created by posewsky on 10.01.14.
 */
public interface InteractionRow {
    public View getView(View convertView);
    public int getViewType();
}
