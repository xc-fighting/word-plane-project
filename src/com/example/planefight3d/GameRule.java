package com.example.planefight3d;

import java.util.Random;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;

public class GameRule extends Thread{
	
	
	 private Handler handler;
	 private Message mes;
	 private Resources res; 
	 
	 
	// private gamerender render;
     public static boolean is_generate_new_daoju=false;
  
   
     public static int word_index=0;
     
     public static int init_time;
     public static float dt;

 
     
     public GameRule(Resources r,gamerender rend)
     {
    	 this.res=r;
    	 this.handler=GameActivity.handler;
    	 this.mes=new Message();
    	 
    	init_time=Timer.init_time_value;
    	dt=init_time/constant.words.length;
    	 
    //	 this.render=rend;
    	 
     }
     
     

     
     
     public static void generate_random_daoju()
     {
    	 if(GameRule.is_generate_new_daoju==true)
    	 {
    		 Random seed=new Random();
    		 int type=seed.nextInt(3);
    		 Daoju tmp=new Daoju(constant.daoju_textures[type],0.1f,0.1f,type);
    		 DaojuManager.DaojuList.add(tmp);
    		 GameRule.is_generate_new_daoju=false;
    	 }
     }
     
     
     public void listen_game_over()
     {
        if(lifemanager.life_value==0||Timer.time_value==0)
        {
        	GameActivity.is_game_over=true;
        	
        }
       
     }
     
     
     public void Judge_Game_Win()
     {
    	 
    	 //游戏失败的判断条件
    	 if(lifemanager.life_value==0)
    	 {
    		 GameActivity.is_game_win=false;
    		 this.mes.what=0;
    		 this.handler.sendMessage(mes);
    	 }
    	 //游戏成功的判断条件
    	 if(Timer.time_value<=0&&lifemanager.life_value>0)
    	 {
    		 GameActivity.is_game_win=true;
    		 this.mes.what=1;
    		 this.handler.sendMessage(mes);
    	 }
    	 
    	 
     }
     
     public boolean if_gen_enemys()
     {
    	 int num_left=enemymanager.enemy_group.size()+EnemyPlaneManager.enemyplanes.size();
    	 if(num_left==0)return true;
    	 else
    		 return false;
     }
     
     

     public void gen_new_enemys()
     {
    	 
    	
    	     for(int i=0;i<5;i++)
    	     {
	    		 Random seed=new Random();
	    		 int type=seed.nextInt(2);
	    		 switch(type)
	    		 {
		    		 case 0:
		    		 {
		    			 //int id,int rows,int cols,float r,float x,float y,float z,float vz
		    			 Random seed1=new Random();
		    			 int num;
		    			 do
		    			 {
		    			   num=seed1.nextInt(10);
		    			 }while(num<=4);
		    			 Random seed2=new Random();
		    			 float x=0.1f*seed2.nextInt(30);
		    			 float y=0.1f*seed2.nextInt(10);
		    			 enemymanager.enemy_group.add(new enemy(constant.yunshi_tex,num,num,0.2f,x,y,-20,0.2f));
		    		 }break;
		    		 case 1:
		    		 {	    			
		    			 Random seed2=new Random();
		    			 float x=0.1f*seed2.nextInt(30);
		    			 float y=0.1f*seed2.nextInt(10);
		    			 EnemyPlaneManager.enemyplanes.add(new enemyplane(constant.feidie_filename,res,constant.feiji_tex,constant.scale_factor,x,y,-20f,0f,0f,0.1f));
		    		 }break;
	    		 }
    	 }
     }
     
     public static void gen_new_words_cube()
     {
    	// Random seed1=new Random();
    	// float x1=seed1.nextInt(10)*0.1f;
    	// float y1=seed1.nextInt(10)*0.1f;
    	 char ch=WordsBannerManager.get_blank_alpha();
    	 WordCubeManager.generate_words(ch, 0.2f,0 ,0 ,-20f);
    	 
    	 Random seed2=new Random();
    	 int index=seed2.nextInt(26);
    	 char ch1=constant.alpha[index];
    	 Random seed3=new Random();
    	 float x2=seed3.nextInt(10)*0.1f;
    	 float y2=seed3.nextInt(10)*0.1f;
    	 WordCubeManager.generate_words(ch1, 0.2f, x2, y2,-20f);
    	 
    	 
    	 Random seed4=new Random();
    	 int index1=seed4.nextInt(26);
    	 char ch2=constant.alpha[index1];
    	 Random seed5=new Random();
    	 float x3=seed5.nextInt(10)*0.1f;
    	 float y3=seed5.nextInt(10)*0.1f;
    	 WordCubeManager.generate_words(ch2, 0.2f, x3, y3,-20f);
    	 
    	 
     }
  
     
     
     
     
     
     
     public void run()
     {
    	while(GameActivity.is_game_over==false)
    	{
    		this.listen_game_over();
    		this.Judge_Game_Win();
    		if(this.if_gen_enemys()==true)
    			this.gen_new_enemys();
    		
          try
          {
        	  sleep(2600);
          }
          catch(Exception e)
          {
        	  e.printStackTrace();
          }
          
    		
    		
    	}
    	if(GameActivity.is_game_over==true)
    		GameActivity.is_game_over=false;
     }
     
    
     
     
     
     
     
     
     
     
     
     
}
