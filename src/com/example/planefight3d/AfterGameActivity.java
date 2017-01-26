package com.example.planefight3d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class AfterGameActivity extends Activity implements OnClickListener{
	
	  private Button button1, button2;
      private TextView tv ;
      public int score=0;  //改成int score  分数来自网络
      private String username;
      private String time;
      
      public Handler handler=new Handler()
      {
    	  public void handleMessage(Message msg)
    	  {
    		  switch(msg.what)
    		  {
	    		  case 0:
	    		  {
	    			  Toast.makeText(AfterGameActivity.this,"网络内部出现错误",Toast.LENGTH_LONG).show();
	    		  }break;
	    		  case 1:
	    		  {
	    			  Toast.makeText(AfterGameActivity.this,"提交分数成功 ",Toast.LENGTH_LONG).show();
	    			  Intent intent=new Intent(AfterGameActivity.this,MenuActivity.class);
	    			  AfterGameActivity.this.startActivity(intent);
	    			  AfterGameActivity.this.finish();
	    		  }break;
    		  }
    	  }
      };
      
      
      public void onDestroy()
      {
    	  super.onDestroy();
    	  scoremanager.goals=0;
    	  DaojuManager.DaojuList.removeAll(DaojuManager.DaojuList);    	  
    	//  playermanager.single_player=null;
    	  enemymanager.enemy_group.removeAll(enemymanager.enemy_group);
    	  EnemyPlaneManager.enemyplanes.removeAll(EnemyPlaneManager.enemyplanes);
    	//  WordCubeManager.cube_list.removeAll(WordCubeManager.cube_list);
    	  ExplodeManager.explode_list.removeAll(ExplodeManager.explode_list);
    	  bulletmanager.enemy_bullets.removeAll(bulletmanager.enemy_bullets);
    	  bulletmanager.player_bullets.removeAll(bulletmanager.player_bullets);
    	  TimerThread.step=0;
    	  GameRule.word_index=0;
      }
    
      @Override
      public void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
             
              this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐去标题（应用的名字）
              //此设定必须要写在setContentView之前，否则会有异常）
              this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                              WindowManager.LayoutParams.FLAG_FULLSCREEN);
              setContentView(R.layout.aftergame); 
              score=scoremanager.goals;
             
              tv=(TextView)findViewById(R.id.textview); 
              tv.setText("您的得分是" + score); 
              button1 = (Button) findViewById(R.id.button1);
              button1.setOnClickListener(this);
              button2 = (Button) findViewById(R.id.button2);
              button2.setOnClickListener(this); 

      } 
      @Override
      public void onClick(View v) {
              if (v == button1) {
            //上传
            	 
            	  if(HTTPConnector.islogin==true)
            	  {
            		  username=HTTPConnector.username;
            		  time=GetTime.get_time();
            		  new update_grade_thread().start();
            	  }
            	  else
            	  {
            		  Toast.makeText(AfterGameActivity.this,"您尚未登录软件，无法提交成绩",Toast.LENGTH_LONG).show();
            	  }
            	  
                      
             } else if (v == button2) {
           //跳转到主菜单
            	 
            	 Intent intent=new Intent(AfterGameActivity.this,MenuActivity.class);
            	 AfterGameActivity.this.startActivity(intent);
            	 AfterGameActivity.this.finish();
                     
              }
      }
      
      
      private class update_grade_thread extends Thread
      {
    	  public void run()
    	  {
    		  HTTPConnector conn=new HTTPConnector(HTTPConnector.ip);
    		  boolean flag=conn.send_grade(username,score,time);
    		  if(flag==true)handler.sendEmptyMessage(1);
    		  else handler.sendEmptyMessage(0);
    		  
    	  }
      }

}
