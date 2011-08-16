package no.andsim.bubbles.view;

import java.util.ArrayList;
import java.util.List;

import no.andsim.bubbles.listener.BubblesOnTouchListener;
import no.andsim.bubbles.model.Element;
import no.andsim.bubbles.model.Settings;
import no.andsim.bubbles.persistence.KeyValueRepository;
import no.andsim.bubbles.thread.ViewThread;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

	protected List<Element> mElements = new ArrayList<Element>();

	protected List<Element> dElements = new ArrayList<Element>();

	private ViewThread mThread;

	public static int mWidth;
	public static int mHeight;

	private int alive = 0;

	private int highScore = 0;
	
	private final String levelName;
	
	private int goal;

	private final MediaPlayer highScoreSound;
	private final MediaPlayer popSound;

	private Paint mPaint = new Paint();
	private Paint mPaintScore = new Paint();
	private Paint mAlive = new Paint();
	private Paint mGoal = new Paint();
	private final Vibrator vibrator;
	private final Bitmap background;
	private final Intent nextLevel;
	
	View.OnTouchListener gestureListener;
	
	private KeyValueRepository keyValueRepository;

	public Panel(final String levelName,final Context context,final Vibrator vibrator,
			final MediaPlayer highscoreSound, final MediaPlayer popSound, final Bitmap background, final int goal, final Intent nextLevel) {
		super(context);
		this.levelName = levelName;
		this.vibrator = vibrator;
		this.background = background;
		this.highScoreSound = highscoreSound;
		this.popSound = popSound;
		this.goal = goal;
		this.nextLevel = nextLevel;
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
		mGoal.setColor(Color.BLUE);
		mGoal.setTextSize(30);
		mPaintScore.setColor(Color.YELLOW);
		mAlive.setColor(Color.BLUE);
		mPaintScore.setTextSize(30);
		mAlive.setTextSize(30);
		highScore = keyValueRepository.getIntValue(levelName+"-highscore");
		if(highScore > goal)goal = highScore;
	}

	public void doDraw(long elapsed, Canvas canvas) {
		canvas.drawBitmap(background, 0, 0, null);
		drawElementsOnCanvas(canvas);
		drawTextOnCanvas(elapsed, canvas);

	}

	private void drawTextOnCanvas(long elapsed, Canvas canvas) {
		if (Settings.isDevmode())
			canvas.drawText("FPS: " + Math.round(1000f / elapsed), 10, 10,
					mPaint);
		canvas.drawText("Goal:" +goal, 300, 30, mGoal);
		canvas.drawText("Alive: " + alive, 300, 60, mAlive);
		canvas.drawText("Highscore: " + highScore, 10, 30, mPaintScore);
	}

	protected void drawElementsOnCanvas(Canvas canvas) {
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
			keyValueRepository.saveInt(levelName+"-highscore", highScore);
			  if(highScore >= goal)((Activity)getContext()).startActivity(nextLevel);
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

	public void addElement(MotionEvent e1) {
		synchronized (mElements) {
			mElements.add(new Element(getResources(), (int) e1.getX(), (int) e1
					.getY()));

		}
		
	}
}
