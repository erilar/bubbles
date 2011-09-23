package no.andsim.bubbles.activity;

import no.andsim.bubbles.model.Settings;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class BubbleMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bubblemenu);

		// set up button sound
		final MediaPlayer mpButtonClick = MediaPlayer.create(this, R.raw.buttonclick);

		Button startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent("no.andsim.bubbles.activity.BUBBLESONE"));
				if (Settings.isSound())
					mpButtonClick.start();
			}
		});

		Button settingBtn = (Button) findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent("no.andsim.bubbles.activity.SETTINGSMENU"));
				if (Settings.isSound())
					mpButtonClick.start();
			}
		});

	}

}
