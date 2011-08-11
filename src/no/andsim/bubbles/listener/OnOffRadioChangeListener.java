package no.andsim.bubbles.listener;

import no.andsim.bubbles.activity.R;
import no.andsim.bubbles.model.Settings;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OnOffRadioChangeListener implements OnCheckedChangeListener {

	@Override
	public void onCheckedChanged(RadioGroup group, int checked) {
		switch(checked){
		case R.id.soundOffRadio:
			Settings.setSound(false);
			break;
		case R.id.soundOnRadio:
			Settings.setSound(true);
			break;
		case R.id.vibrateOnRadio:
			Settings.setVibration(true);
			break;
		case R.id.vibrateOffRadio:
			Settings.setVibration(false);
			break;
		}
		
	}

}
