package no.andsim.bubbles.view;

import no.andsim.bubbles.model.Element;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class FanPanel extends Panel {

	public FanPanel(String levelName, Context context, Vibrator vibrator,
			MediaPlayer highscoreSound, MediaPlayer popSound,
			Bitmap background, int goal, Intent nextLevel) {
		super(levelName, context, vibrator, highscoreSound, popSound, background, goal,
				nextLevel);
	}
	
	
	@Override
	protected void drawElementsOnCanvas(Canvas canvas) {
		synchronized (super.mElements) {
			for (Element element : mElements) {
				checkForCrash(element);
				if(element.getmSpeedX()>-10)
					element.setmSpeedX(element.getmSpeedX()-0.1);
				element.doDraw(canvas);
			}
		}
	}
}
