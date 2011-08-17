package no.andsim.bubbles.activity;

import no.andsim.bubbles.model.Element;
import no.andsim.bubbles.view.FanPanel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;

public class BubblesFour extends Activity {
	private final int levelGoal = 10;
	private final Intent nextLevel = new Intent("no.andsim.bubbles.activity.BUBBLESBEAT");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Element.setSpeedDivider(50);
		final MediaPlayer highscoreSound = MediaPlayer.create(this, R.raw.fanfare);
		final MediaPlayer popSound = MediaPlayer.create(this, R.raw.pop);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        FanPanel panel = new FanPanel("bubblefour",this,v,highscoreSound,popSound, BitmapFactory.decodeResource(getResources(),
				R.drawable.hurricane),levelGoal, nextLevel );
        panel.setxSpeedMod(+0.3);
        panel.setMaxXSpeed(5);
        setContentView(panel); 
	}
	@Override
	public void onBackPressed() {
		  startActivity( new Intent(
					"no.andsim.bubbles.activity.BUBBLEMENU"));
	}
}
