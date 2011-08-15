package no.andsim.bubbles.view;

import java.util.ArrayList;
import java.util.List;

import no.andsim.bubbles.activity.R;
import no.andsim.bubbles.listener.BubblesOnTouchListener;
import no.andsim.bubbles.model.Difficulty;
import no.andsim.bubbles.model.Element;
import no.andsim.bubbles.model.Settings;
import no.andsim.bubbles.persistence.KeyValueRepository;
import no.andsim.bubbles.thread.ViewThread;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

	private List<Element> mElements = new ArrayList<Element>();

	private List<Element> dElements = new ArrayList<Element>();

	private ViewThread mThread;

	public static int mWidth;
	public static int mHeight;

	private int alive = 0;

	private int highScore = 0;

	private MediaPlayer highScoreSound;
	private MediaPlayer popSound;

	private Paint mPaint = new Paint();
	private Paint mPaintScore = new Paint();
	private Paint mAlive = new Paint();
	private Vibrator vibrator;
	
	View.OnTouchListener gestureListener;
	
	private KeyValueRepository keyValueRepository;

	public Panel(Context context, Vibrator vibrator,
			MediaPlayer highscoreSound, MediaPlayer popSound) {
		super(context);
		this.vibrator = vibrator;
		this.highScoreSound = highscoreSound;
		this.popSound = popSound;
		createResources(context);
		setOnTouchListener(gestureListener);
		initiValues();

	}

	private void createResources(Context context) {
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		gestureListener = new BubblesOnTouchListener(this);
		keyValueRepository = new KeyValueRepository(context);
	}

	private void initiValues() {
		mPaint.setColor(Color.WHITE);
		mPaintScore.setColor(Color.YELLOW);
		mAlive.setColor(Color.BLUE);
		mPaintScore.setTextSize(30);
		mAlive.setTextSize(30);
		highScore = keyValueRepository.getIntValue("highscore");
	}

	public void doDraw(long elapsed, Canvas canvas) {
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.damp);
		canvas.drawBitmap(mBitmap, 0, 0, null);
		drawElementsOnCanvas(canvas);
		drawTextOnCanvas(elapsed, canvas);

	}

	private void drawTextOnCanvas(long elapsed, Canvas canvas) {
		if (Settings.isDevmode())
			canvas.drawText("FPS: " + Math.round(1000f / elapsed), 10, 10,
					mPaint);
		canvas.drawText("Alive: " + alive, 300, 30, mAlive);
		canvas.drawText("Highscore: " + highScore, 10, 30, mPaintScore);
	}

	private void drawElementsOnCanvas(Canvas canvas) {
		synchronized (mElements) {
			for (Element element : mElements) {
				checkForCrash(element);
				element.doDraw(canvas);
			}
		}
	}

	public boolean checkForCrash(Element element) {
		if (!element.isDestroyed()) {
			for (Element other : mElements) {
				if (element != other && element.isOccupyingSameSpace(other)
						&& (!other.isDestroyed())) {
					destroy(element, other);
					checkForHighScore();

					return true;
				}
			}
		}
		return false;
	}

	private void destroy(Element element, Element other) {
		element.setDestroyed();
		other.setDestroyed();
		dElements.add(element);
		dElements.add(other);
		if (Settings.isVibration())
			vibrator.vibrate(500);
		if (Settings.isSound())
			popSound.start();
	}

	private void checkForHighScore() {
		if (alive > highScore) {
			highScore = alive;
			keyValueRepository.saveInt("highscore", highScore);
			  if(highScore >= Difficulty.getLevelOneNormal())((Activity)getContext()).startActivity(new Intent("no.andsim.bubbles.activity.BUBBLESTWO"));
			if (Settings.isSound())
				highScoreSound.start();
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
		return true;
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

	public void purgeDestroyed() {
		synchronized (mElements) {
			mElements.removeAll(dElements);
			dElements.clear();
			alive = mElements.size();
		}

	}

	public void addElement(MotionEvent e1, float velocityX, float velocityY) {
		synchronized (mElements) {
			mElements.add(new Element(getResources(), (int) e1.getX(), (int) e1
					.getY(), (int) velocityX, (int) velocityY));

		}

	}
}
