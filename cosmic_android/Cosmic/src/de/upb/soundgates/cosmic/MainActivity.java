package de.upb.soundgates.cosmic;

import de.upb.soundgates.cosmic.controller.SocketSender;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity implements OnSeekBarChangeListener {
	/* Static Variables */
	private static final String TAG = "MainActivity";
	
	/* Member variables */
	private SeekBar		m_Seekbar = null;
	private EditText	m_Frequency = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* Initialize views */
        m_Frequency = (EditText) findViewById(R.id.vet_frequency);
        m_Frequency.setText(Integer.toString(440));
        m_Seekbar = (SeekBar) findViewById(R.id.vsb_frequency);
        m_Seekbar.setMax(440);
        m_Seekbar.setOnSeekBarChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		// Min value is 440 Hz and max value is 880 Hz
		progress += 440;
		String frequency = Integer.toString(progress);
		m_Frequency.setText(frequency);
		Log.i(TAG,"Sending frequency " + frequency + " to the FPGA");
		//SocketSender.sendMessage(frequency);
		new SocketSender().execute(frequency);
	}


	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
    
}
