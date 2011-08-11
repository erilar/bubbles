package no.andsim.bubbles;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;

public class BubblesOne extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final MediaPlayer highscoreSound = MediaPlayer.create(this, R.raw.fanfare);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Panel panel = new Panel(this,v,highscoreSound);
        setContentView(panel); 
	}

}
