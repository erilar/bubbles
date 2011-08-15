package no.andsim.bubbles.activity;

import no.andsim.bubbles.thread.SplashThread;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class BubblesStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.splash);
    	
    	SplashThread logoSplash = new SplashThread(this,new Intent("no.andsim.bubbles.activity.BUBBLEMENU"),5000);
    	logoSplash.start();
    	
    }
}