package com.example.planefight3d;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class GameActivity extends Activity {

	private gameview myview;
	public static int width;
	public static int height;
	
	//游戏结束的标志位
	public static boolean is_game_over=false;
	
	
	//判断游戏是否成功的标志handler
	public static Handler handler;
	public static boolean is_game_win=false;
	
	private TextReader reader;
	
	
	public  void getScreenInfo()
	{
		WindowManager manager=getWindowManager();
		Display display=manager.getDefaultDisplay();
		DisplayMetrics metrics=new DisplayMetrics();
		display.getMetrics(metrics);
		width=metrics.widthPixels;
		height=metrics.heightPixels;
	}
	
	
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    requestWindowFeature(Window.FEATURE_NO_TITLE); 		//设置为全屏
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	    handler=new Handler()
		{
			public void handleMessage(Message mes)
			{
				super.handleMessage(mes);
				//resetAll();
				switch(mes.what)
				{
				case 0:
				{
					Intent intent=new Intent(GameActivity.this,AfterGameActivity.class);
					GameActivity.this.startActivity(intent);
					GameActivity.this.finish();
					is_game_win=true;
					//	Toast.makeText(GameActivity.this,"you win!!!!!!!",Toast.LENGTH_SHORT).show();
						
				}break;
				case 1:
				{
					Intent intent=new Intent(GameActivity.this,AfterGameActivity.class);
					GameActivity.this.startActivity(intent);
					GameActivity.this.finish();
					is_game_win=false;
				}break;
				}
						
					
				
			}
		};
		//GameActivity.is_game_over=false;
		//初始化constant里面的词汇;
		reader=new TextReader(this,true);
		reader.read_Shengci();
		int size=ShengCi.mosheng_word_list.size();
		if(size!=0)
		{
		  constant.words=new String[size];
		  
			  for(int i=0;i<size;i++)
			  {
				  constant.words[i]=ShengCi.mosheng_word_list.get(i);
			  }
			 
		  
		
		}
		//构造gameView
		myview=new gameview(this);
		setContentView(myview);
		this.getScreenInfo();
		Toast.makeText(GameActivity.this,"width:"+width+"height:"+height+":::"+(float)height/(float)width,Toast.LENGTH_SHORT).show();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}
	
	public void onPause()
	{
		super.onPause();
		myview.onPause();
	}
	
	public void onResume()
	{
		super.onResume();
		myview.onResume();
	}
	public void onDestroy()
	{
		super.onDestroy();
		
	}

}
