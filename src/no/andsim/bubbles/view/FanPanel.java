package no.andsim.bubbles.view;

import no.andsim.bubbles.model.Element;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class FanPanel extends Panel {

	private double xSpeedMod = 0;
	private double ySpeedMod = 0;
	private double maxYSpeed = 10;
	private double maxXSpeed = 10;
	
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
				if(xSpeedMod != 0 && element.getmSpeedX()<=maxXSpeed)
					element.setmSpeedX(element.getmSpeedX()+xSpeedMod);
				if(ySpeedMod != 0 && element.getmSpeedX()<=maxYSpeed)
					element.setmSpeedX(element.getmSpeedX()+ySpeedMod);
				element.doDraw(canvas);
			}
		}
	}


	public void setxSpeedMod(double xSpeedMod) {
		this.xSpeedMod = xSpeedMod;
	}


	public void setySpeedMod(double ySpeedMod) {
		this.ySpeedMod = ySpeedMod;
	}


	public void setMaxYSpeed(double maxYSpeed) {
		this.maxYSpeed = maxYSpeed;
	}


	public void setMaxXSpeed(double maxXSpeed) {
		this.maxXSpeed = maxXSpeed;
	}
}
