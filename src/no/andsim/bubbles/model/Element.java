package no.andsim.bubbles.model;

import java.util.Random;

import no.andsim.bubbles.activity.R;
import no.andsim.bubbles.view.Panel;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Element {

	private static int speedDivider = 100;
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
		dBitmap = BitmapFactory.decodeResource(res, R.drawable.burst);
		mX = x - mBitmap.getWidth() / 2;
		mY = y - mBitmap.getHeight() / 2;

		// offsets based on height (y axis) and width (x axis)
		xPosOffset = mBitmap.getWidth() / 2;
		xNegOffset = mBitmap.getWidth() / 2;
		yPosOffset = mBitmap.getHeight() / 2;
		yNegOffset = mBitmap.getHeight() / 2;

		mSpeedX = rand.nextInt(7) - 3;
		mSpeedY = rand.nextInt(7) - 3;
	}
	
	public Element(Resources res, int x, int y, int speedX, int speedY) {
		//Random rand = new Random();
		mBitmap = BitmapFactory.decodeResource(res, R.drawable.bubble);
		dBitmap = BitmapFactory.decodeResource(res, R.drawable.burst);
		mX = x - mBitmap.getWidth() / 2;
		mY = y - mBitmap.getHeight() / 2;

		// offsets based on height (y axis) and width (x axis)
		xPosOffset = mBitmap.getWidth() / 2;
		xNegOffset = mBitmap.getWidth() / 2;
		yPosOffset = mBitmap.getHeight() / 2;
		yNegOffset = mBitmap.getHeight() / 2;

		mSpeedX = speedX/speedDivider;
		mSpeedY = speedY/speedDivider;
	}

	public void animate(Long elapsedTime) {
		if (!isDestroyed()) {
			mX += mSpeedX * (elapsedTime / 20f);
			mY += mSpeedY * (elapsedTime / 20f);
			checkBorders();
		}
	}

	public void setDestroyed() {
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
		if (!destroyed)
			canvas.drawBitmap(mBitmap, mX, mY, null);
		else
			canvas.drawBitmap(dBitmap, dX, dY, null);
	}

	public boolean isOccupyingSameSpace(Element other) {
		int oX = (int) other.mX;
		int oY = (int) other.mY;
		int tX = (int) mX;
		int tY = (int) mY;
		if (checkForOverlap(oX, oY, tX, tY)) {
			return true;
		} else
			return false;
	}

	private boolean checkForOverlap(int oX, int oY, int tX, int tY) {
		if (checkForXAxisOverlap(oX, tX) && checkForYAxisOverlap(oY, tY))
			return true;
		else
			return false;

	}

	private boolean checkForYAxisOverlap(int oY, int tY) {
		return oY + yPosOffset >= tY && oY - yNegOffset <= tY;
	}

	private boolean checkForXAxisOverlap(int oX, int tX) {
		return oX + xPosOffset >= tX && oX - xNegOffset <= tX;
	}

	public static int getSpeedDivider() {
		return speedDivider;
	}

	public static void setSpeedDivider(int speedDivider) {
		Element.speedDivider = speedDivider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(dX);
		result = prime * result + Float.floatToIntBits(dY);
		result = prime * result + (destroyed ? 1231 : 1237);
		result = prime * result + mSpeedX;
		result = prime * result + mSpeedY;
		result = prime * result + Float.floatToIntBits(mX);
		result = prime * result + Float.floatToIntBits(mY);
		result = prime * result + Float.floatToIntBits(xNegOffset);
		result = prime * result + Float.floatToIntBits(xPosOffset);
		result = prime * result + Float.floatToIntBits(yNegOffset);
		result = prime * result + Float.floatToIntBits(yPosOffset);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (Float.floatToIntBits(dX) != Float.floatToIntBits(other.dX))
			return false;
		if (Float.floatToIntBits(dY) != Float.floatToIntBits(other.dY))
			return false;
		if (destroyed != other.destroyed)
			return false;
		if (mSpeedX != other.mSpeedX)
			return false;
		if (mSpeedY != other.mSpeedY)
			return false;
		if (Float.floatToIntBits(mX) != Float.floatToIntBits(other.mX))
			return false;
		if (Float.floatToIntBits(mY) != Float.floatToIntBits(other.mY))
			return false;
		if (Float.floatToIntBits(xNegOffset) != Float
				.floatToIntBits(other.xNegOffset))
			return false;
		if (Float.floatToIntBits(xPosOffset) != Float
				.floatToIntBits(other.xPosOffset))
			return false;
		if (Float.floatToIntBits(yNegOffset) != Float
				.floatToIntBits(other.yNegOffset))
			return false;
		if (Float.floatToIntBits(yPosOffset) != Float
				.floatToIntBits(other.yPosOffset))
			return false;
		return true;
	}

}
