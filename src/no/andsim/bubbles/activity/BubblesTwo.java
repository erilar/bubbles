package no.andsim.bubbles.activity;

import no.andsim.bubbles.model.Element;
import no.andsim.bubbles.view.Panel;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;

public class BubblesTwo extends Activity{
	
	private final int levelGoal = 8;
	private final Intent nextLevel = new Intent("no.andsim.bubbles.activity.BUBBLESTHREE");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Element.setSpeedDivider(25);
		final MediaPlayer highscoreSound = MediaPlayer.create(this, R.raw.fanfare);
		final MediaPlayer popSound = MediaPlayer.create(this, R.raw.pop);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Panel panel = new Panel("bubbletwo",this,v,highscoreSound,popSound, BitmapFactory.decodeResource(getResources(),
				R.drawable.damp2),levelGoal, nextLevel );
        setContentView(panel); 
	}
	@Override
	public void onBackPressed() {
		 startActivity( new Intent(
					"no.andsim.bubbles.activity.BUBBLEMENU"));
	}
	

}
