package no.andsim.bubbles.activity;

import no.andsim.bubbles.listener.OnOffRadioChangeListener;
import no.andsim.bubbles.model.Settings;
import no.andsim.bubbles.persistence.KeyValueRepository;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsMenu extends Activity {

	private OnOffRadioChangeListener radioSettingListener;
	private KeyValueRepository keyValueRepository;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		keyValueRepository = new KeyValueRepository(this.getApplicationContext());
		setContentView(R.layout.settings);
		setInitialState();
		radioSettingListener = new OnOffRadioChangeListener();
		RadioGroup soundRadioGrp = (RadioGroup) findViewById(R.id.groupSound);
		soundRadioGrp.setOnCheckedChangeListener(radioSettingListener);
		RadioGroup vibrateRadioGrp = (RadioGroup) findViewById(R.id.groupVibrate);
		vibrateRadioGrp.setOnCheckedChangeListener(radioSettingListener);
		RadioGroup devRadioGrp = (RadioGroup) findViewById(R.id.groupDev);
		devRadioGrp.setOnCheckedChangeListener(radioSettingListener);
		Button clearHigh = (Button) findViewById(R.id.clearHigh);
		clearHigh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				keyValueRepository.saveInt("bubbleone-highscore", 0);
				keyValueRepository.saveInt("bubbletwo-highscore", 0);
				
			}
		});
	}

	private void setInitialState() {

		// Sound
		RadioButton soundOn = (RadioButton) findViewById(R.id.soundOnRadio);
		if (Settings.isSound())
			soundOn.setChecked(true);
		RadioButton soundOff = (RadioButton) findViewById(R.id.soundOffRadio);
		if (!Settings.isSound())
			soundOff.setChecked(true);

		// Vibration
		RadioButton vibrateOn = (RadioButton) findViewById(R.id.vibrateOnRadio);
		if (Settings.isVibration())
			vibrateOn.setChecked(true);
		RadioButton vibrateOff = (RadioButton) findViewById(R.id.vibrateOffRadio);
		if (!Settings.isVibration())
			vibrateOff.setChecked(true);

		// Devmode
		RadioButton devOn = (RadioButton) findViewById(R.id.devOnRadio);
		if (Settings.isDevmode())
			devOn.setChecked(true);
		RadioButton devOff = (RadioButton) findViewById(R.id.devOffRadio);
		if (!Settings.isDevmode())
			devOff.setChecked(true);

	}

}
