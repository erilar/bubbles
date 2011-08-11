package no.andsim.bubbles.model;

import java.util.Random;

import no.andsim.bubbles.activity.R;
import no.andsim.bubbles.view.Panel;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Element {

	private float mX;
	private float mY;
	private int mSpeedX;
	private int mSpeedY;

	private Bitmap mBitmap;
	private Bitmap dBitmap;
	
	private float dX;
	private float dY;
	
	private float yPosOffset;
	private float yNegOffset;
	
	private float xPosOffset;
	private float xNegOffset;
	
	private boolean destroyed = false;
	
	
	public boolean isDestroyed() {
		return destroyed;
	} 

	public Element(Resources res, int x, int y) {
		Random rand = new Random();
		mBitmap = BitmapFactory.decodeResource(res, R.drawable.bubble);
		dBitmap = BitmapFactory.decodeResource(res, R.drawable.boom);
		mX = x - mBitmap.getWidth() / 2;
		mY = y - mBitmap.getHeight() / 2;
		
		// offsets based on height (y axis) and width (x axis)
		xPosOffset = mBitmap.getWidth()/2;
		xNegOffset = mBitmap.getWidth()/2;
		yPosOffset = mBitmap.getHeight()/2;
		yNegOffset = mBitmap.getHeight()/2;

		mSpeedX = rand.nextInt(7) - 3;
		mSpeedY = rand.nextInt(7) - 3;
	}

	public void animate(Long elapsedTime) {
		mX += mSpeedX * (elapsedTime / 20f);
		mY += mSpeedY * (elapsedTime / 20f);
		checkBorders();
	}
	
	public void setDestroyed(){
			dX = mX;
			dY = mY;
			destroyed = true;
	}

	private void checkBorders() {
		if (mX <= 0) {
			mSpeedX = -mSpeedX;
			mX = 0;
		} else if (mX + mBitmap.getWidth() >= Panel.mWidth) {
			mSpeedX = -mSpeedX;
			mX = Panel.mWidth - mBitmap.getWidth();
		}
		if (mY <= 0) {
			mY = 0;
			mSpeedY = -mSpeedY;
		}
		if (mY + mBitmap.getHeight() >= Panel.mHeight) {
			mSpeedY = -mSpeedY;
			mY = Panel.mHeight - mBitmap.getHeight();
		}
	}

	public void doDraw(Canvas canvas) {
		if(!destroyed)canvas.drawBitmap(mBitmap, mX, mY, null);
		else canvas.drawBitmap(dBitmap, dX, dY,null);
	}

	public boolean isOccupyingSameSpace(Element other) {
		int oX = (int) other.mX;
		int oY = (int) other.mY;
		int tX = (int) mX;
		int tY = (int) mY;
		if (checkForOverlap(oX, oY, tX, tY)) {
			return true;
		} else return false;
	}

	private boolean checkForOverlap(int oX, int oY, int tX, int tY) {
		if(checkForXAxisOverlap(oX, tX)&&checkForYAxisOverlap(oY, tY)) return true;
		else return false;

	}
	
	private boolean checkForYAxisOverlap(int oY, int tY) {
		return oY +yPosOffset >=tY && oY - yNegOffset <=tY;
	}
	
	private boolean checkForXAxisOverlap(int oX, int tX) {
		return oX +xPosOffset >=tX && oX - xNegOffset <=tX;
	}

}
