package no.andsim.bubbles.activity;

import no.andsim.bubbles.thread.TransitionThread;
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
    	
    	TransitionThread logoSplash = new TransitionThread(this,new Intent("no.andsim.bubbles.activity.BUBBLEMENU"),2000);
    	logoSplash.start();
    	
    }
}