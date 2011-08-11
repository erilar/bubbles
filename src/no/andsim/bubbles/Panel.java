package no.andsim.bubbles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

	private List<Element> mElements = Collections.synchronizedList(new ArrayList<Element>());

	private ViewThread mThread;

	public static int mWidth;
	public static int mHeight;

	private int mElementNumber = 0;
	private int destroyedNumber = 0;

	private int highScore;
	private int alive;
	
	private MediaPlayer highScoreSound;
	
	
	private Paint mPaint = new Paint();
	private Paint mPaintScore = new Paint();
	private Vibrator vibrator;

	public Panel(Context context, Vibrator vibrator, MediaPlayer highscoreSound) {
		super(context);
		this.vibrator = vibrator;
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		mPaint.setColor(Color.WHITE);
		mPaintScore.setColor(Color.YELLOW);
		this.highScoreSound = highscoreSound;
		highScoreSound.setVolume(0.1f, 0.1f);

	}

	protected void doDraw(long elapsed, Canvas canvas) {
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.damp);
		 canvas.drawBitmap(mBitmap, 0, 0, null);
		 synchronized (mElements) {
			for (Element element : mElements) {
				checkForCrash(element);
				element.doDraw(canvas);
			}
		}
		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Bubbles: "
				+ mElementNumber + " Bursts: " + destroyedNumber, 10, 10,
				mPaint);
		mPaintScore.setTextSize(25);
		canvas.drawText("HIGHSCORE: "+highScore,10,35,mPaintScore);
	}

	public boolean checkForCrash(Element element) {
		if(!element.isDestroyed()){		
			for (Element other : mElements) {
				if (element != other && element.isOccupyingSameSpace(other)
						&& (!other.isDestroyed())) {
					element.setDestroyed();
					other.setDestroyed();
					if(Settings.isVibration())vibrator.vibrate(500);
					destroyedNumber = destroyedNumber +2;
					checkForHighScore();
					
					return true;
				}
			}
		}
		return false;
	}

	private void checkForHighScore() {
		alive = mElementNumber - destroyedNumber;
		if(alive>highScore){
			highScore = alive;
			if(Settings.isSound())highScoreSound.start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mWidth = width;
		mHeight = height;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (mElements) {
			mElements.add(new Element(getResources(), (int) event.getX(),
					(int) event.getY()));
			mElementNumber = mElements.size();
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (!mThread.isAlive()) {
			mThread = new ViewThread(this);
			mThread.setRunning(true);
			mThread.start();
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void animate(long elapsedTime) {
		synchronized (mElements) {
			for (Element element : mElements) {
				element.animate(elapsedTime);
			}
		}
	}

}
