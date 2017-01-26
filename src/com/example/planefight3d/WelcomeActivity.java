package com.example.planefight3d;

import android.app.Activity;

import android.content.Intent;


import android.os.Bundle;


import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;


public class WelcomeActivity extends Activity{
	
	private ImageView welcome1;
	
	
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
	//	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	//    requestWindowFeature(Window.FEATURE_NO_TITLE); 		//ÉèÖÃÎªÈ«ÆÁ
	//    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		welcome1=(ImageView)this.findViewById(R.id.welcome);
    	AlphaAnimation anima=new AlphaAnimation(0.05f,1f);
    	anima.setDuration(6000);
    	welcome1.startAnimation(anima);
    	anima.setAnimationListener(new AniListener());
	    
	    
	    
	}
	
	
	
	 private class AniListener implements AnimationListener
	    {

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
			    skip();
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				welcome1.setBackgroundResource(R.drawable.welcome);
				
				
			}
	    	
	    }
	    
	    public void skip()
	    {
	    
	    	Intent intent=new Intent(WelcomeActivity.this,MenuActivity.class);
	    	WelcomeActivity.this.startActivity(intent);
	    	WelcomeActivity.this.finish();
	    }
	
	
	
	
	

}
