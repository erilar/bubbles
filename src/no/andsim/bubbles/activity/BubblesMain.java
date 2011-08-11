package no.andsim.bubbles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class BubblesMain extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.splash);
    	
    	Thread logoTimer = new Thread(){
			public void run(){
				try{
					int logoTimer = 0;
					while(logoTimer<5000){
						sleep(100);
						logoTimer = logoTimer + 100;
					}
					startActivity(new Intent("no.andsim.bubbles.activity.BUBBLEMENU"));
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		logoTimer.start();
    	
    }
}