package no.andsim.bubbles.activity;

import no.andsim.bubbles.thread.SplashThread;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class BubblesBeat extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.beat);
		SplashThread beatSplash = new SplashThread(this, new Intent(
				"no.andsim.bubbles.activity.BUBBLEMENU"), 1000);
		beatSplash.start();
	}

	@Override
	public void onBackPressed() {

		return;
	}
}
